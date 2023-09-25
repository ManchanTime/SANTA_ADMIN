package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.adapter.GridViewAdapter;
import com.gachon.santa_admin.dialog.ProgressDialog;
import com.gachon.santa_admin.entity.Galley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class SelectPicturesActivity extends AppCompatActivity {
    private GridView gridview = null;
    private GridViewAdapter adapter = null;
    String uid;
    String type;
    //골뱅이 돌리기
    private ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pictures);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        gridview = (GridView) findViewById(R.id.grid_paint);
        Intent intent1 = getIntent();
        uid = intent1.getStringExtra("uid");
        type = intent1.getStringExtra("type");

        adapter = new GridViewAdapter(this, type);

        load();
    }

    public void load(){
        //로딩창
        customProgressDialog.show();
        //화면터치 방지
        customProgressDialog.setCanceledOnTouchOutside(false);
        //뒤로가기 방지
        customProgressDialog.setCancelable(false);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("paints");
        collectionReference
                .whereEqualTo("uid", uid)
                .whereEqualTo("type", type)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        customProgressDialog.cancel();
                        customProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            adapter.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String pid = document.getData().get("pid").toString();
                                String url = document.getData().get("url").toString();
                                adapter.addItem(new Galley(uid, pid, url, new Date(document.getDate("date").getTime())));
                            }
                            gridview.setAdapter(adapter);
                        }
                        else{
                            Log.e("error", task.getException().toString());
                        }
                    }
                });
    }
}

