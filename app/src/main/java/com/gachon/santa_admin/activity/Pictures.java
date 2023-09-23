package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gachon.santa_admin.R;

public class Pictures extends AppCompatActivity {

    Button FIGURE_Button;
    Button KHTP_Button;
    Button LMT_Button;
    Button PITR_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        Intent intent1 = getIntent();
        String uid = intent1.getStringExtra("uid");//전에 페이지에서 받은 uid를 가져옴.

        FIGURE_Button = findViewById(R.id.FIGUREbutton);
        KHTP_Button = findViewById(R.id.KHTPbutton);
        LMT_Button = findViewById(R.id.LMTbutton);
        PITR_Button = findViewById(R.id.PITRbutton);

        // FIGURE 버튼 클릭 시 FIGUREActivity로 이동 : 사용 유저 FIGURE로 이동을 해야함
        FIGURE_Button.setOnClickListener(new View.OnClickListener() {
            String type = "figure";
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, Select_Pictures.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        // KHTP 버튼 클릭 시 KHTPActivity로 이동 : 사용 유저 KHTP로 이동을 해야함
        KHTP_Button.setOnClickListener(new View.OnClickListener() {
            String type = "htp";
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, Select_Pictures.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        // LMT 버튼 클릭 시 LMTActivity로 이동 : 사용 유저 LMT로 이동을 해야함
        LMT_Button.setOnClickListener(new View.OnClickListener() {
            String type = "lmt";
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, Select_Pictures.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        // PITR 버튼 클릭 시 PITRActivity로 이동 : 사용 유저 PITR로 이동을 해야함
        PITR_Button.setOnClickListener(new View.OnClickListener() {
            String type = "pitr";
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, Select_Pictures.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }
}