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

        //각 아이템 선택 event
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (type) {
//                    case "figure":
//                        // type이 1인 경우, 다른 페이지로 이동합니다.
//                        Intent intent1 = new Intent(context, FigureActivity.class);
//                        // 필요한 데이터를 인텐트에 추가할 수 있습니다.
//                        intent1.putExtra("date", bearItem.getDate());
//                        context.startActivity(intent1);
//                        break;
//                    case "htp":
//                        // type이 1인 경우, 다른 페이지로 이동합니다.
//                        Intent intent2 = new Intent(context, KhtpActivity.class);
//                        // 필요한 데이터를 인텐트에 추가할 수 있습니다.
//                        intent2.putExtra("date", bearItem.getDate());
//                        context.startActivity(intent2);
//                        break;
//                    case "lmt":
//                        // type이 1인 경우, 다른 페이지로 이동합니다.
//                        Intent intent3 = new Intent(context, LmtActivity.class);
//                        // 필요한 데이터를 인텐트에 추가할 수 있습니다.
//                        intent3.putExtra("date", bearItem.getDate());
//                        context.startActivity(intent3);
//                        break;
//                    case "pitr":
//                        // type이 1인 경우, 다른 페이지로 이동합니다.
//                        Intent intent4 = new Intent(context, PitrActivity.class);
//                        // 필요한 데이터를 인텐트에 추가할 수 있습니다.
//                        intent4.putExtra("date", bearItem.getDate());
//                        context.startActivity(intent4);
//                        break;
//                }
//            }
//        });
}
