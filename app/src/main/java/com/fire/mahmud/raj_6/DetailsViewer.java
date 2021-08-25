package com.fire.mahmud.raj_6;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsViewer extends AppCompatActivity {
    ImageView iv;
    TextView titlet,detailst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_viewer);
        titlet=findViewById(R.id.details_viewer_title_textView);
        detailst=findViewById(R.id.details_view_details_textView);
        iv=findViewById(R.id.details_viewer_imageView);
        titlet.setText(getIntent().getStringExtra(KeyF.Intentdata.title));
        detailst.setText(getIntent().getStringExtra(KeyF.Intentdata.details));
        Picasso.with(this).load(Uri.parse(getIntent().getStringExtra(KeyF.Intentdata.picUri))).into(iv);
    }
}
