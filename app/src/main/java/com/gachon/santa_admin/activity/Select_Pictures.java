package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gachon.santa_admin.R;

import java.util.ArrayList;

public class Select_Pictures extends AppCompatActivity {
    private GridView gridview = null;
    private GridViewAdapter adapter = null;
    String uid;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pictures);

        gridview = (GridView) findViewById(R.id.gridview);
        Intent intent1 = getIntent();
        uid = intent1.getStringExtra("uid");
        type = intent1.getStringExtra("type");

        adapter = new GridViewAdapter();

        //Adapter 안에 아이템의 정보 담기
        adapter.addItem(new gallery("23-09-18, 08:37", "FIGURE", R.drawable.android1));
        adapter.addItem(new gallery("23-09-19, 20:33", "LMT", R.drawable.android2));
        adapter.addItem(new gallery("23-09-20", "KHTP", R.drawable.android3));
        adapter.addItem(new gallery("23-09-21", "PITR", R.drawable.android4));
        adapter.addItem(new gallery("23-09-22", "LMT", R.drawable.android5));

        //리스트뷰에 Adapter 설정
        gridview.setAdapter(adapter);

    }


    /* 그리드뷰 어댑터 */
    class GridViewAdapter extends BaseAdapter {
        ArrayList<gallery> items = new ArrayList<gallery>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(gallery item) {
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final gallery bearItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);

                TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

                tv_num.setText(bearItem.getDate());
                tv_name.setText(bearItem.getName());
                iv_icon.setImageResource(bearItem.getResId());


            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (type) {
                        case "figure":
                            // type이 1인 경우, 다른 페이지로 이동합니다.
                            Intent intent1 = new Intent(context, FIGURE.class);
                            // 필요한 데이터를 인텐트에 추가할 수 있습니다.
                            intent1.putExtra("date", bearItem.getDate());
                            context.startActivity(intent1);
                            break;
                        case "htp":
                            // type이 1인 경우, 다른 페이지로 이동합니다.
                            Intent intent2 = new Intent(context, KHTP.class);
                            // 필요한 데이터를 인텐트에 추가할 수 있습니다.
                            intent2.putExtra("date", bearItem.getDate());
                            context.startActivity(intent2);
                            break;
                        case "lmt":
                            // type이 1인 경우, 다른 페이지로 이동합니다.
                            Intent intent3 = new Intent(context, LMT.class);
                            // 필요한 데이터를 인텐트에 추가할 수 있습니다.
                            intent3.putExtra("date", bearItem.getDate());
                            context.startActivity(intent3);
                            break;
                        case "pitr":
                            // type이 1인 경우, 다른 페이지로 이동합니다.
                            Intent intent4 = new Intent(context, PITR.class);
                            // 필요한 데이터를 인텐트에 추가할 수 있습니다.
                            intent4.putExtra("date", bearItem.getDate());
                            context.startActivity(intent4);
                            break;
                    }
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

}