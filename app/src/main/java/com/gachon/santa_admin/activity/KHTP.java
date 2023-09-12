package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gachon.santa_admin.R;

public class KHTP extends AppCompatActivity {

    Button result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khtp);

//        result = findViewById(R.id.Resultbutton);
//        result.setOnClickListener(new View.OnClickListener() {
//            Intent intent = new Intent(KHTP.this, ResultChat.class);
//            startActivity(intent);
//        });
    }
}