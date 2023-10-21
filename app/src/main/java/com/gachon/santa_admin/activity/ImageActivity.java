package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gachon.santa_admin.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity {

    private PhotoView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String image = getIntent().getStringExtra("image");
        img = findViewById(R.id.image_full);

        ImageView over = findViewById(R.id.btn_cancel);
        Glide.with(this).load(image).into(img);
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}