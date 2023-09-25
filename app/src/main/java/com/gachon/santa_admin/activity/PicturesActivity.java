package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gachon.santa_admin.R;

public class PicturesActivity extends AppCompatActivity {

    private Button FIGURE_Button;
    private Button KHTP_Button;
    private Button LMT_Button;
    private Button PITR_Button;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        Intent intent1 = getIntent();
        uid = intent1.getStringExtra("uid");//전에 페이지에서 받은 uid를 가져옴.

        FIGURE_Button = findViewById(R.id.FIGUREbutton);
        KHTP_Button = findViewById(R.id.KHTPbutton);
        LMT_Button = findViewById(R.id.LMTbutton);
        PITR_Button = findViewById(R.id.PITRbutton);

        FIGURE_Button.setOnClickListener(onClickListener);
        KHTP_Button.setOnClickListener(onClickListener);
        LMT_Button.setOnClickListener(onClickListener);
        PITR_Button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = (v) -> {
        Intent intent;
        String type = "";
        switch (v.getId()){
            case R.id.FIGUREbutton:
                type = "figure";
                break;
            case R.id.KHTPbutton:
                type = "htp";
                break;
            case R.id.LMTbutton:
                type = "lmt";
                break;
            case R.id.PITRbutton:
                type = "pitr";
                break;
        }
        intent = new Intent(PicturesActivity.this, SelectPicturesActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("type", type);
        startActivity(intent);
    };
}