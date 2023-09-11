package com.gachon.santa_admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.gachon.santa_admin.R;

import java.util.Locale;

public class IntroActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private Button btnNext;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        text = "어르신의 행복하고 활력있는 삶을 그림 그리기를 통해 찾아드리겠습니다!!";
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(onClickListener);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.btn_next:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    };

    @Override public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }
}