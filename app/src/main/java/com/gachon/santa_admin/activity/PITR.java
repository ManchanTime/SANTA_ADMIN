package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gachon.santa_admin.R;

public class PITR extends AppCompatActivity {
    Button send;
    ImageView photo;
    String date;
    EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitr);
        Intent getIntent = getIntent();
        date = getIntent.getStringExtra("date");


        photo = findViewById(R.id.imageView_P);
        send = findViewById(R.id.Resultbutton_P);
        editTextMessage = findViewById(R.id.Result_P);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                if (!message.isEmpty()) {
                    Toast.makeText(PITR.this, "메시지를 보냈습니다: " + message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PITR.this, "메시지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}