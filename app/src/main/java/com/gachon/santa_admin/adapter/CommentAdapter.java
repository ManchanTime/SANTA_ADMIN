package com.gachon.santa_admin.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gachon.santa_admin.R;
import com.gachon.santa_admin.entity.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final ArrayList<Comment> mDataset;
    private Activity activity;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public CommentViewHolder(RelativeLayout v){
            super(v);
            relativeLayout = v;
        }
    }

    public CommentAdapter(Activity activity, ArrayList<Comment> myDataset){
        this.activity = activity;
        mDataset = myDataset;
        firestore = FirebaseFirestore.getInstance();
        Log.e("test", mDataset.size()+"");
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RelativeLayout relativeLayout =
                (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, @SuppressLint("RecyclerView") int position){
        RelativeLayout relativeLayout = holder.relativeLayout;
        TextView textName = relativeLayout.findViewById(R.id.text_name);
        TextView textContent = relativeLayout.findViewById(R.id.text_content);
        TextView textTime = relativeLayout.findViewById(R.id.text_time);

        documentReference = firestore.collection("comments").document(mDataset.get(position).getCid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String publisherId = task.getResult().getData().get("publisher").toString();
                    String content = task.getResult().getData().get("content").toString();
                    Date date = mDataset.get(position).getCreatedAt();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
                    String strDate = simpleDateFormat.format(date);
                    documentReference = firestore.collection("users").document(publisherId);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                String publisherName = task.getResult().getData().get("name").toString();
                                textName.setText(publisherName);
                                textContent.setText(content);
                                textTime.setText(strDate);
                            }
                            else{
                                Log.e("secondError", task.getException().toString());
                            }
                        }
                    });
                }
                else{
                    Log.e("error", task.getException().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void removeItem(int position){
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addItem(Comment comment){
        mDataset.add(0, comment);
        notifyDataSetChanged();
    }
}
