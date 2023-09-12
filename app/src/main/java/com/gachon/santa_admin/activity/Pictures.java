package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gachon.santa_admin.R;

public class Pictures extends AppCompatActivity {

    Button KHTP_Button;
    Button LMT_Button;
    Button PITR_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        KHTP_Button = findViewById(R.id.KHTPbutton);
        LMT_Button = findViewById(R.id.LMTbutton);
        PITR_Button = findViewById(R.id.PITRbutton);

        // KHTP 버튼 클릭 시 KHTPActivity로 이동
        KHTP_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, KHTP.class);
                startActivity(intent);
            }
        });

        // LMT 버튼 클릭 시 LMTActivity로 이동
        LMT_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, LMT.class);
                startActivity(intent);
            }
        });

        // PITR 버튼 클릭 시 PITRActivity로 이동
        PITR_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pictures.this, PITR.class);
                startActivity(intent);
            }
        });
    }
}