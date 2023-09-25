package com.gachon.santa_admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gachon.santa_admin.entity.UserItem;
import com.gachon.santa_admin.util.UserItemView;

import java.util.ArrayList;

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

        UserItem item = items.get(position);

        view.setName(item.getName());
        view.setAge(item.getAge());
        view.setSex(item.getSex());
        return view;
    }
}
