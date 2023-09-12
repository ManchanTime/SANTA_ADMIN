package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gachon.santa_admin.R;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {
    UserAdapter adapter;

    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
//아래에 있는 주석을 풀면 list에서 회원을 추가할 수 있는 코드가 나옴
//        editText = (EditText) findViewById(R.id.editText);
//        editText2 = (EditText) findViewById(R.id.editText2);

        ListView listView = (ListView) findViewById(R.id.listView);

        // 어댑터 안에 데이터 담기
        adapter = new UserAdapter();

        adapter.addItem(new UserItem("user1", "010-3433-2323", R.drawable.android1));
        adapter.addItem(new UserItem("user2", "010-3433-2323", R.drawable.android2));
        adapter.addItem(new UserItem("user3", "010-3433-2323", R.drawable.android3));
        adapter.addItem(new UserItem("user4", "010-3433-2323", R.drawable.android4));
        adapter.addItem(new UserItem("user5", "010-3433-2323", R.drawable.android5));
        adapter.addItem(new UserItem("user6", "010-3433-2323", R.drawable.android1));

        // 리스트 뷰에 어댑터 설정
        listView.setAdapter(adapter);
        // 이벤트 처리 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserItem item = (UserItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :"+item.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MemberList.this, Pictures.class);

                startActivity(intent);
            }
        });

        // 버튼 눌렀을 때 우측 이름, 전화번호가 리스트뷰에 포함되도록 처리
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = editText.getText().toString();
//                String mobile = editText2.getText().toString();
//
//                adapter.addItem(new UserItem(name, mobile, R.drawable.android1));
//                adapter.notifyDataSetChanged();
//            }
//        }
//        );
    }

    class UserAdapter extends BaseAdapter {
        ArrayList<UserItem> items = new ArrayList<UserItem>();


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
                view = new UserItemView(getApplicationContext());
            } else {
                view = (UserItemView) convertView;
            }

            UserItem item = items.get(position);

            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setImage(item.getResId());

            return view;
        }
    }
}