package com.gachon.santa_admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gachon.santa_admin.R;

public class UserItemView extends LinearLayout {

    TextView text_info1;
    TextView text_info2;
    TextView text_info3;
    ImageView imageView;

    // Generate > Constructor

    public UserItemView(Context context) {
        super(context);

        init(context);
    }

    public UserItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_user_item, this, true);

        text_info1 = (TextView) findViewById(R.id.text_info1);
        text_info2 = (TextView) findViewById(R.id.text_info2);
        text_info3 = (TextView) findViewById(R.id.text_info3);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setName(String name) {
        text_info1.setText(name);
    }

    public void setAge(String age) {
        text_info2.setText(age);
    }

    public void setSex(String sex){
        text_info3.setText(sex);
    }
    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }

}