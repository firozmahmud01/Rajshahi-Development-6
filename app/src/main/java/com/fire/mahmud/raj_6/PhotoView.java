package com.fire.mahmud.raj_6;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PhotoView extends AppCompatActivity {
    public static Uri url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);






        //loading image in high quality
        Picasso.with(this).load(url).placeholder(R.drawable.tmp).into((ImageView)findViewById(R.id.photo_viewer_imageView));



    }
}
