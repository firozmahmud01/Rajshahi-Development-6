package com.fire.mahmud.raj_6;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AllStep extends AppCompatActivity {
    //statice value
    public static boolean notification=false;






    private ListView lv;
    private BaseAdapter ba;
    Context context;
    private LayoutInflater li;
    private DatabaseReference listd;
    private ArrayList<OnlineData> listdata;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_step);
        listdata=new ArrayList<OnlineData>();
        context=this;
        if (notification){
            notification=false;
            listd=FirebaseDatabase.getInstance().getReference(KeyF.notice);


        }else {
            listd = FirebaseDatabase.getInstance().getReference(KeyF.allUpload).child(getIntent().getStringExtra(KeyF.AllstepIntent.folder)).child(getIntent().getStringExtra(KeyF.AllstepIntent.subfolder));
        }
        li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lv=findViewById(R.id.show_casing_listview);
        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return listdata.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view==null){
                    view=li.inflate(R.layout.all_upload_custom,null);
                }
                ImageView iv=view.findViewById(R.id.all_upload_custom_details_pic_imageView);
                TextView title=view.findViewById(R.id.all_upload_custom_title_textView),
                details=view.findViewById(R.id.all_upload_custom_details);
                title.setText(KeyF.getCompressedText(listdata.get(position).getTitle(),70));
                Picasso.with(context).load(listdata.get(position).getPicuri()).into(iv);
                details.setText(KeyF.getCompressedText(listdata.get(position).getDetails(),100));
                return view;
            }
        };
        lv.setAdapter(ba);
        listd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listdata.removeAll(listdata);
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    OnlineData od=new OnlineData(d.child("title").getValue(String.class)
                            ,d.child("details").getValue(String.class)
                            ,Uri.parse(d.child("picurl").getValue(String.class)));
                    listdata.add(od);
                }
                ba.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,DetailsViewer.class);
                intent.putExtra(KeyF.Intentdata.picUri,listdata.get(position).getPicuri().toString());
                intent.putExtra(KeyF.Intentdata.title,listdata.get(position).getTitle());
                intent.putExtra(KeyF.Intentdata.details,listdata.get(position).getDetails());
                startActivity(intent);
            }
        });
    }
}
