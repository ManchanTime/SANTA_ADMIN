package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.adapter.GridViewAdapter;
import com.gachon.santa_admin.adapter.SpinnerAdapter;
import com.gachon.santa_admin.dialog.ProgressDialog;
import com.gachon.santa_admin.entity.Galley;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SelectPicturesActivity extends AppCompatActivity {
    private GridView gridview = null;
    private GridViewAdapter adapter = null;
    private String uid;
    //골뱅이 돌리기
    private ProgressDialog customProgressDialog;
    //스피너
    private String choose = "전체";
    private final String[] items = {"전체", "Figure", "Lmt", "K-Htp", "Pitr"};
    private Boolean[] check;
    private Boolean init;
    private Boolean initialSelection;

    @Override
    protected void onResume(){
        super.onResume();
        check = new Boolean[]{false, false, false, false, false};
        initialSelection = true;
        init = false;
        load(choose);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pictures);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //새로 고침
        ImageView imageReload = findViewById(R.id.image_reload);
        imageReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent = getIntent(); //인텐트
                startActivity(intent); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            }
        });

        gridview = (GridView) findViewById(R.id.grid_paint);
        Intent intent1 = getIntent();
        uid = intent1.getStringExtra("uid");

        adapter = new GridViewAdapter(this);
    }

    public void load(String type){
        //로딩창
        customProgressDialog.show();
        //화면터치 방지
        customProgressDialog.setCanceledOnTouchOutside(false);
        //뒤로가기 방지
        customProgressDialog.setCancelable(false);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("paints");
        if(type.equals("전체")) {
            collectionReference
                    .whereEqualTo("uid", uid)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            customProgressDialog.cancel();
                            customProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                adapter.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String pid = document.getData().get("pid").toString();
                                    String url = document.getData().get("url").toString();
                                    Boolean read = (Boolean) document.getData().get("read");
                                    String type = document.getData().get("type").toString();
                                    if (Boolean.FALSE.equals(read)) {
                                        check[0] = true;
                                        switch (type) {
                                            case "figure":
                                                check[1] = true;
                                                break;
                                            case "lmt":
                                                check[2] = true;
                                                break;
                                            case "htp":
                                                check[3] = true;
                                                break;
                                            case "pitr":
                                                check[4] = true;
                                                break;
                                        }
                                    }
                                    adapter.addItem(new Galley(uid, pid, url, read, new Date(document.getDate("date").getTime())));
                                }
                                if (!init) {
                                    setSpinner();
                                    init = true;
                                }
                                gridview.setAdapter(adapter);
                            } else {
                                Log.e("error", task.getException().toString());
                            }
                        }
                    });
        }
        else{
            String pType = "";
            switch (type) {
                case "Figure":
                    pType = "figure";
                    break;
                case "Lmt":
                    pType = "lmt";
                    break;
                case "K-Htp":
                    pType = "htp";
                    break;
                case "Pitr":
                    pType = "pitr";
                    break;
            }
            collectionReference
                    .whereEqualTo("uid", uid)
                    .whereEqualTo("type", pType)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            customProgressDialog.cancel();
                            customProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                adapter.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String pid = document.getData().get("pid").toString();
                                    String url = document.getData().get("url").toString();
                                    Boolean read = (Boolean) document.getData().get("read");
                                    adapter.addItem(new Galley(uid, pid, url, read, new Date(document.getDate("date").getTime())));
                                }
                                gridview.setAdapter(adapter);
                            } else {
                                Log.e("error", task.getException().toString());
                            }
                        }
                    });
        }
    }

    public void setSpinner(){
        //스피너 초기화
        Spinner filter = findViewById(R.id.sp_filter);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.item_spinner, items, check);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialSelection) {
                    initialSelection = false;
                    return; // 초기 선택을 처리하고 리스너 종료
                }
                choose = items[i];
                load(choose);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}

