package com.gachon.santa_admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gachon.santa_admin.R;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] items;
    private Boolean[] check;

    public SpinnerAdapter(Context context, int resource, String[] items, Boolean[] check) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.check = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        }

        TextView spinnerText = convertView.findViewById(R.id.text_type);
        ImageView spinnerRead = convertView.findViewById(R.id.image_read);

        spinnerText.setText(items[position]); // 텍스트 설정
        if(check[position]){
            spinnerRead.setVisibility(View.VISIBLE);
        }
        else
            spinnerRead.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}