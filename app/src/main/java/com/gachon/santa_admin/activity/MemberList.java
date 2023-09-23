package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gachon.santa_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {
    UserAdapter adapter;
    ListView listView;
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this);

        // Firebase Realtime Database 초기화
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("users"); // "users"는 Firebase Database의 데이터 경로입니다.

        // "user"인 정보만 가져와서 리스트에 추가
        collectionReference
                .whereEqualTo("type", "user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String age = document.getData().get("age").toString();
                                String name = document.getData().get("name").toString();
                                String sex = document.getData().get("sex").toString();
                                String uid = document.getData().get("uid").toString();
                                UserItem userItem = new UserItem(name, age, sex, uid);
                                adapter.addItem(userItem);
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    UserItem item = (UserItem) adapter.getItem(position);
                                    Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MemberList.this, Pictures.class);
                                    intent.putExtra("uid", item.getUid());
                                    //Log.e("test", intent.getStringExtra("uid"));
                                    startActivity(intent);

                                }
                            });
                        }else{
                            Log.e("text",task.getException().toString());
                        }
                    }
                });
        // 이벤트 처리 리스너 설정

    }

    // UserAdapter 클래스와 UserItem 클래스는 그대로 사용합니다.
    // ...
}
    class UserAdapter extends BaseAdapter {
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

            UserItem item = items.get(position);

            view.setName(item.getName());
            view.setAge(item.getAge());
            view.setSex(item.getSex());
            Log.e("text", items.size()+"");
            return view;
        }
    }