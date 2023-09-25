package com.gachon.santa_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gachon.santa_admin.R;
import com.gachon.santa_admin.dialog.ProgressDialog;
import com.gachon.santa_admin.entity.Comment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    private ImageView imagePicture;
    private EditText editComment;
    private Button btnSend;
    private String target, type, url, postId;
    private TextView textType;
    //골뱅이 돌리기
    private ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        imagePicture = findViewById(R.id.image_picture);
        editComment = findViewById(R.id.edit_comment);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(onClickListener);
        textType = findViewById(R.id.text_type);

        Intent intent = getIntent();
        target = intent.getStringExtra("target");
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        postId = intent.getStringExtra("pid");
        textType.setText(type);
        Glide.with(this).load(url).into(imagePicture);
    }

    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.btn_send:
                if(editComment.getText().toString().equals("")){
                    Toast.makeText(this, "코멘트를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else
                    sendComment();
                break;
        }
    };

    public void sendComment(){
        //로딩창
        customProgressDialog.show();
        //화면터치 방지
        customProgressDialog.setCanceledOnTouchOutside(false);
        //뒤로가기 방지
        customProgressDialog.setCancelable(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("comments").document();
        Comment comment =
                new Comment(documentReference.getId(), user.getUid(), target, postId, editComment.getText().toString(), url, false, new Date());
        documentReference.set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                customProgressDialog.cancel();
                customProgressDialog.dismiss();
                Toast.makeText(CommentActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}