package com.gachon.santa_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gachon.santa_admin.activity.CommentActivity;
import com.gachon.santa_admin.entity.Galley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/* 그리드뷰 어댑터 */
public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Galley> items = new ArrayList<Galley>();
    private String type;
    private Context context;

    public GridViewAdapter(Context context, String type){
        this.context = context;
        this.type = type;
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
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5, 5, 5, 5);
        Glide.with(context).load(items.get(position).getUrl()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = FirebaseFirestore.getInstance()
                        .collection("paints")
                        .document(items.get(position).getPid());
                documentReference.update("read", true);
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("target", items.get(position).getUid());
                intent.putExtra("type", type);
                intent.putExtra("pid", items.get(position).getPid());
                intent.putExtra("url", items.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        return imageView;
    }

}
