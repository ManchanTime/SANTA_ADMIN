package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gachon.santa_admin.R;
import com.gachon.santa_admin.adapter.CommentAdapter;
import com.gachon.santa_admin.dialog.ProgressDialog;
import com.gachon.santa_admin.entity.Comment;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private PhotoView imagePicture;
    private EditText editComment;
    private Button btnSend;
    private String target, type, url, postId;
    private TextView textType;

    private RelativeLayout layoutNoSearch;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList = new ArrayList<>();
    //골뱅이 돌리기
    private ProgressDialog customProgressDialog;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        firestore = FirebaseFirestore.getInstance();

        imagePicture = findViewById(R.id.image_picture);
        imagePicture.setOnClickListener(onClickListener);
        editComment = findViewById(R.id.edit_comment);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(onClickListener);
        textType = findViewById(R.id.text_type);
        layoutNoSearch = findViewById(R.id.layout_no_search);

        Intent intent = getIntent();
        target = intent.getStringExtra("target");
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        postId = intent.getStringExtra("pid");
        textType.setText(type);
        Glide.with(this).load(url).into(imagePicture);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(100);
        commentAdapter = new CommentAdapter(this, commentList);
        commentAdapter.setHasStableIds(true);
        loadComment();
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
            case R.id.image_picture:
                Intent intent = new Intent(this, ImageActivity.class);
                intent.putExtra("image", url);
                startActivity(intent);
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
        DocumentReference documentReference = firestore.collection("comments").document();
        Comment comment =
                new Comment(documentReference.getId(), user.getUid(), target, postId, editComment.getText().toString(), url, false, new Date());
        documentReference.set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                customProgressDialog.cancel();
                customProgressDialog.dismiss();
                Toast.makeText(CommentActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                commentAdapter.addItem(comment);
                editComment.setText("");
            }
        });
    }

    public void loadComment(){
        commentList.clear();
        //로딩창
        customProgressDialog.show();
        //화면터치 방지
        customProgressDialog.setCanceledOnTouchOutside(false);
        //뒤로가기 방지
        customProgressDialog.setCancelable(false);

        CollectionReference collectionReference = firestore.collection("comments");
        collectionReference
                .whereEqualTo("postId", postId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                commentList.add(
                                        new Comment(
                                                document.getData().get("cid").toString(),
                                                document.getData().get("publisher").toString(),
                                                document.getData().get("target").toString(),
                                                document.getData().get("postId").toString(),
                                                document.getData().get("content").toString(),
                                                document.getData().get("url").toString(),
                                                (Boolean) document.getData().get("read"),
                                                new Date(document.getDate("createdAt").getTime())
                                        )
                                );
                            }
                            if(commentList.isEmpty()){
                                layoutNoSearch.setVisibility(View.VISIBLE);
                            }else
                                layoutNoSearch.setVisibility(View.GONE);
                            recyclerView.setAdapter(commentAdapter);
                        }
                        else{
                            Log.e("CommentError", task.getException().toString());
                        }
                        customProgressDialog.cancel();
                        customProgressDialog.dismiss();
                    }
                });
    }
}