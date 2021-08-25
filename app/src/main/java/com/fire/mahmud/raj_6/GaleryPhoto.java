package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GaleryPhoto extends AppCompatActivity {
    //declearing firebase
    private StorageReference sr;







    //declearing interface
    GridView gridView;









    //declearing variables
    BaseAdapter ba;
    ArrayList<Uri> allurl;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_photo);
        //assining interface
        gridView=findViewById(R.id.galery_photo_grid);






        //assining variables
        allurl=new ArrayList<>();
        context=this;









        //assining firebase
        sr= FirebaseStorage.getInstance().getReference(KeyF.gallery);








        //assining baseadapter
        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return allurl.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            LayoutInflater li=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=li.inflate(R.layout.galary_custom,null);
                }
                Picasso.with(context).load(allurl.get(position)).into((ImageView)convertView.findViewById(R.id.galary_custom_imageView));
                return convertView;
            }
        };








        //setting adapter
        gridView.setAdapter(ba);







        //setting onclicklistener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog d=new Dialog(context);
                d.setContentView(R.layout.photo_viewer_dialog_layout);
                ImageView iv=d.findViewById(R.id.photo_viewer_imageView_new);
                Picasso.with(getApplicationContext()).load(allurl.get(position)).into(iv);
                d.show();
            }
        });









        //listing all data
        sr.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                allurl.removeAll(allurl);
                for (StorageReference sr:listResult.getItems()){
                    sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            allurl.add(uri);
                            ba.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
