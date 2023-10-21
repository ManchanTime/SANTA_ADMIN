package com.gachon.santa_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.gachon.santa_admin.activity.CommentActivity;
import com.gachon.santa_admin.entity.Galley;
import com.gachon.santa_admin.util.PictureItemView;
import com.gachon.santa_admin.util.UserItemView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.PersistentCacheIndexManager;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 그리드뷰 어댑터 */
public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Galley> items = new ArrayList<>();
    private final Context context;

    public GridViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Galley item) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        PictureItemView pictureItemView = null;
        if(convertView == null){
            pictureItemView = new PictureItemView(context);
        }else{
            pictureItemView = (PictureItemView) convertView;
        }
        //setNotification(pictureItemView, items.get(position).getPid(), position);
        pictureItemView.setImage(items.get(position).getUrl(), items.get(position).getRead());
        pictureItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = FirebaseFirestore.getInstance()
                        .collection("paints")
                        .document(items.get(position).getPid());
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        documentReference.update("read", true);
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("target", items.get(position).getUid());
                        intent.putExtra("type", task.getResult().getData().get("type").toString());
                        intent.putExtra("pid", items.get(position).getPid());
                        intent.putExtra("url", items.get(position).getUrl());
                        context.startActivity(intent);
                    }
                });
            }
        });
        return pictureItemView;
    }
}
