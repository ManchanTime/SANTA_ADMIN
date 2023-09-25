package com.gachon.santa_admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.adapter.UserAdapter;
import com.gachon.santa_admin.dialog.ProgressDialog;
import com.gachon.santa_admin.entity.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MemberListActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private ListView listView;
    private EditText editSearch;
    private String search_data = "";
    //골뱅이 돌리기
    private ProgressDialog customProgressDialog;
    private CollectionReference collectionReference;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this);
        editSearch = findViewById(R.id.edit_search);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // Firebase Database 초기화
        firestore = FirebaseFirestore.getInstance();
        setEdit();
        load();
    }

    private void load(){
        //로딩창
        customProgressDialog.show();
        //화면터치 방지
        customProgressDialog.setCanceledOnTouchOutside(false);
        //뒤로가기 방지
        customProgressDialog.setCancelable(false);
        collectionReference = firestore.collection("users"); // "users"는 Firebase Database의 데이터 경로입니다.
        // "user"인 정보만 가져와서 리스트에 추가
        collectionReference
                .whereEqualTo("type", "user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        customProgressDialog.cancel();
                        customProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            adapter.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String age = document.getData().get("age").toString();
                                String name = document.getData().get("name").toString();
                                String sex = document.getData().get("sex").toString();
                                String uid = document.getData().get("uid").toString();
                                UserItem userItem = new UserItem(name, age, sex, uid);
                                adapter.addItem(userItem);
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    UserItem item = (UserItem) adapter.getItem(position);
                                    Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MemberListActivity.this, PicturesActivity.class);
                                    intent.putExtra("uid", item.getUid());
                                    startActivity(intent);

                                }
                            });
                        }else{
                            Log.e("error",task.getException().toString());
                        }
                    }
                });
        // 이벤트 처리 리스너 설정
    }

    private void search(){
        collectionReference = firestore.collection("users");
        collectionReference
                .whereEqualTo("name", search_data)
                .whereEqualTo("type", "user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        customProgressDialog.cancel();
                        customProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            adapter.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String age = document.getData().get("age").toString();
                                String name = document.getData().get("name").toString();
                                String sex = document.getData().get("sex").toString();
                                String uid = document.getData().get("uid").toString();
                                UserItem userItem = new UserItem(name, age, sex, uid);
                                adapter.addItem(userItem);
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    UserItem item = (UserItem) adapter.getItem(position);
                                    Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MemberListActivity.this, PicturesActivity.class);
                                    intent.putExtra("uid", item.getUid());
                                    startActivity(intent);

                                }
                            });
                        }else{
                            Log.e("error",task.getException().toString());
                        }
                    }
                });
    }

    private void setEdit(){
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString();
                if(!text.equals("") || editSearch.getVisibility() == View.VISIBLE){
                    editSearch.setBackground(getDrawable(R.drawable.square_edit_text));
                }else{
                    editSearch.setBackground(getDrawable(R.drawable.square_edit_text_focus));
                }
            }
        });
        editSearch.setImeOptions(EditorInfo.IME_ACTION_DONE); //키보드 다음 버튼을 완료 버튼으로 바꿔줌
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                search_data = v.getText().toString();
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    customProgressDialog.show();
                    //화면터치 방지
                    customProgressDialog.setCanceledOnTouchOutside(false);
                    //뒤로가기 방지
                    customProgressDialog.setCancelable(false);
                    if(search_data.equals(""))
                        load();
                    else
                        search();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.show();

        Button btnNo = dialog.findViewById(R.id.btn_no);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnYes = dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });

    }

    public void exit(){
        super.onBackPressed();
    }

    // UserAdapter 클래스와 UserItem 클래스는 그대로 사용합니다.
    // ...
}
