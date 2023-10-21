package com.gachon.santa_admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.entity.UserItem;
import com.gachon.santa_admin.util.UserItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserAdapter extends BaseAdapter {
    ArrayList<UserItem> items = new ArrayList<UserItem>();
    Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    // Generate > implement methods
    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(UserItem item) {
        items.add(item);
    }

    public void clear(){
        items.clear();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 뷰 객체 재사용
        UserItemView view = null;
        if (convertView == null) {
            view = new UserItemView(context);
        } else {
            view = (UserItemView) convertView;
        }
        setNotification(items.get(position).getUid(), view);
        UserItem item = items.get(position);

        view.setName(item.getName());
        view.setAge(item.getAge());
        view.setSex(item.getSex());
        return view;
    }

    public void setNotification(String user, UserItemView view){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference chatRef = firebaseFirestore.collection("paints");
        //chatRef의 데이터가 변경될때마다 반응하는 리스너 달기 : get()은 일회용
        chatRef.addSnapshotListener(new EventListener<QuerySnapshot>() { //데이터가 바뀔떄마다 찍음
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //데이터가 바뀔때마다 그냥 add하면 그 순간의 모든것을 찍어 가져오기 때문에 중복되어버림
                //따라서 변경된 Document만 찾아달라고 해야함
                //1. 바뀐 애들 찾온다 - 왜 리스트인가? 처음 시작할 때 문제가 됨 그래서 여러개라고 생각함
                List<DocumentChange> documentChanges = value.getDocumentChanges();
                int count = 0;
                for (DocumentChange documentChange : documentChanges) {
                    //2.변경된 문서내역의 데이터를 촬영한 DocumentSnapshot얻어오기
                    DocumentSnapshot snapshot = documentChange.getDocument();
                    //3.Document에 있는 필드값 가져오기
                    Map<String, Object> comments = snapshot.getData();
                    if (comments != null) {
                        boolean read = (boolean) comments.get("read");
                        if (!read) {
                            String targetId = comments.get("uid").toString();
                            if (targetId.equals(user)) {
                                count++;
                            }
                        }
                    }
                }
                view.setImageRead(count != 0);
            }
        });
    }
}
