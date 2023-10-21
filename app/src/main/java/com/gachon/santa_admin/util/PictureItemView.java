package com.gachon.santa_admin.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.gachon.santa_admin.R;
import com.gachon.santa_admin.activity.CommentActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PictureItemView extends LinearLayout {
    ImageView imageView;
    ImageView imageRead;

    // Generate > Constructor

    public PictureItemView(Context context) {
        super(context);
        init(context);
    }
    public PictureItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_picture, this, true);

        imageView = findViewById(R.id.image_picture);
        imageRead = findViewById(R.id.image_read);
    }

    public void setImage(String url, boolean b) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(this + "error", "image load Error");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        setImageRead(b);
                        return false;
                    }
                })
                .into(imageView);
    }
    public void setImageRead(boolean b){
        if(b){
            imageRead.setVisibility(GONE);
        }
        else{
            imageRead.setVisibility(VISIBLE);
        }
    }
}
