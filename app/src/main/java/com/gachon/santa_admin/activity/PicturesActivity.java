package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.util.UserItemView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class PicturesActivity extends AppCompatActivity {

    private Button FIGURE_Button;
    private Button KHTP_Button;
    private Button LMT_Button;
    private Button PITR_Button;
    private ImageView imgFigure, imgKhpt, imgLmt, imgPitr;
    private String uid;

    @Override
    protected void onResume(){
        super.onResume();
        setNotification(uid, "figure", imgFigure);
        setNotification(uid, "htp", imgKhpt);
        setNotification(uid, "lmt", imgLmt);
        setNotification(uid, "pitr", imgPitr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        Intent intent1 = getIntent();
        uid = intent1.getStringExtra("uid");//전에 페이지에서 받은 uid를 가져옴.

        FIGURE_Button = findViewById(R.id.btn_figure);
        KHTP_Button = findViewById(R.id.btn_htp);
        LMT_Button = findViewById(R.id.btn_lmt);
        PITR_Button = findViewById(R.id.btn_pitr);

        FIGURE_Button.setOnClickListener(onClickListener);
        KHTP_Button.setOnClickListener(onClickListener);
        LMT_Button.setOnClickListener(onClickListener);
        PITR_Button.setOnClickListener(onClickListener);

        imgFigure = findViewById(R.id.image_figure);
        imgKhpt = findViewById(R.id.image_hpt);
        imgLmt = findViewById(R.id.image_lmt);
        imgPitr = findViewById(R.id.image_pitr);

//        setNotification(uid, "figure", imgFigure);
//        setNotification(uid, "htp", imgKhpt);
//        setNotification(uid, "lmt", imgLmt);
//        setNotification(uid, "pitr", imgPitr);
    }

    View.OnClickListener onClickListener = (v) -> {
        Intent intent;
        String type = "";
        switch (v.getId()){
            case R.id.btn_figure:
                type = "figure";
                break;
            case R.id.btn_htp:
                type = "htp";
                break;
            case R.id.btn_lmt:
                type = "lmt";
                break;
            case R.id.btn_pitr:
                type = "pitr";
                break;
        }
        intent = new Intent(PicturesActivity.this, SelectPicturesActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("type", type);
        startActivity(intent);
    };

    public void setNotification(String uid, String type, ImageView imageView){
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("paints");
        collectionReference
                .whereEqualTo("uid", uid)
                .whereEqualTo("type", type)
                .whereEqualTo("read", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                count++;
                            }
                            if(count != 0)
                                imageView.setVisibility(View.VISIBLE);
                            else
                                imageView.setVisibility(View.GONE);
                        }
                        else{
                            Log.e(this + " error", "task Error");
                        }
                    }
                });
    }
}