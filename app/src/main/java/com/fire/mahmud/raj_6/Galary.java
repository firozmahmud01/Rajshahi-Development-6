package com.fire.mahmud.raj_6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Galary extends Fragment {
    //declearing variables
    Context context;
    BaseAdapter lvadapter;
    ArrayList<DataFile>data;
    BaseAdapter gridba,serviceba;
    int height=0;
    ArrayList<Integer>hlist;
    String servicenames[];
    int serviceicon[];









    //declearing interface
    RecyclerView rv;
    ListView lv;
    GridView gv,services;
    ViewPager vp;








    //Declearing firebase
    DatabaseReference dr;



public Galary(Context context,String servicenames[],int serviceicon[],ViewPager vp){
this.context=context;
this.vp=vp;
this.servicenames=servicenames;
this.serviceicon=serviceicon;
}

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_galary, container, false);
        //assining interface
        rv=view.findViewById(R.id.home_page_recyclerView);
        lv=view.findViewById(R.id.home_page_listview);
        gv=view.findViewById(R.id.homepage_gridview);
        services=view.findViewById(R.id.home_page_services_gridview);
        









        //assining variables
        data=new ArrayList<>();
        hlist=new ArrayList<>();
        





        //assining firebase
        dr= FirebaseDatabase.getInstance().getReference(KeyF.notice);






        //making recyclerview horizontal
        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);













        //assining adapter
        lvadapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size()>4?4:data.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                    ViewGroup vg=lv;
                    int limit=lvadapter.getCount();
                    height=0;
                    for(int x=0;x<limit;x++) {
                        View v= lvadapter.getView(x,null,vg);
                        v.measure(0,0);
                        height+=v.getMeasuredHeight();
                    }
                        ViewGroup.LayoutParams lp = lv.getLayoutParams();
                        lp.height = height;
                        lv.setLayoutParams(lp);
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
            public View getView(int position, View cv, ViewGroup parent) {
                if (cv==null){
                    cv=inflater.inflate(R.layout.all_upload_custom,null);
                }

                ImageView iv=cv.findViewById(R.id.all_upload_custom_details_pic_imageView);
                TextView title=cv.findViewById(R.id.all_upload_custom_title_textView),
                        details=cv.findViewById(R.id.all_upload_custom_details);
                title.setText(KeyF.getCompressedText(data.get(position).getTitle(),70));
                Picasso.with(context).load(data.get(position).getPicurl()).into(iv);
                details.setText(KeyF.getCompressedText(data.get(position).getDetails(),100));



                return cv;
            }
        };







        gridba=new BaseAdapter() {
            @Override
            public int getCount() {
                return 9;
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
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=inflater.inflate(R.layout.phone_custom_layout,null);
                }





                String arr[]={
                        "সরকারি তথ্য ও সেবা",
                        "জরুরি সেবা",
                        "নারী ও শিশু নির্যাতন",
                        "দুদক",
                        "দুর্যোগের আগাম বার্তা",
                        "বাঘা পুলিশ স্টেশন",
                        "বাঘা ফায়ার সার্ভিস",
                        "চারঘাট পুলিশ স্টেশন",
                        "চারঘাট ফায়ার সার্ভিস"
                };

                ((LinearLayout)convertView.findViewById(R.id.phone_back)).setBackground(getResources().getDrawable(R.drawable.phonegradient));
                ((TextView)convertView.findViewById(R.id.phone_custom_tv)).setText(arr[position]);
                return convertView;
            }
        };


        serviceba=new BaseAdapter() {
            @Override
            public int getCount() {
                return servicenames.length;
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
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=inflater.inflate(R.layout.phone_custom_layout,null);
                }
                ((ImageView)convertView.findViewById(R.id.home_page_custom_dialer)).setImageResource(serviceicon[position]);
                ((LinearLayout)convertView.findViewById(R.id.phone_back)).setBackground(getResources().getDrawable(R.drawable.phonegradient));
                ((TextView)convertView.findViewById(R.id.phone_custom_tv))
                        .setText(KeyF.groupchild.banglaLikha(servicenames[position]));
                return convertView;
            }
        };


        //setting grid to service list
        services.setAdapter(serviceba);






        //setting onitem
        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //changeing page by item position
                vp.setCurrentItem(position+1);
            }
        });





        //setting grid to phone list
        gv.setAdapter(gridba);




        ViewGroup vg=gv,sergroup=services;

        int limit=gridba.getCount();
        height=0;
        for(int x=0;x<limit;x+=3) {
            View v= gridba.getView(x,null,vg);
            v.measure(0,0);
            height+=v.getMeasuredHeight();
        }
        ViewGroup.LayoutParams lp = gv.getLayoutParams();
        lp.height = height+(limit*gv.getVerticalSpacing());
        gv.setLayoutParams(lp);

        int serlimit=serviceba.getCount();
        int serheight=0;

        for(int x=0;x<serlimit;x+=3) {
            View v= serviceba.getView(x,null,sergroup);
            v.measure(0,0);
            serheight+=v.getMeasuredHeight();
        }
        ViewGroup.LayoutParams vp = services.getLayoutParams();
        vp.height = height+(limit*services.getVerticalSpacing());
        services.setLayoutParams(vp);




        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Galary.this.onItemClick(position);
            }
        });









        //setting adapter
        RecyclerAdapter rd=new RecyclerAdapter(context);
        rv.setAdapter(rd);
        //listview adapter
        lv.setAdapter(lvadapter);










        //setting on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,DetailsViewer.class);
                intent.putExtra(KeyF.Intentdata.picUri,data.get(position).getPicurl());
                intent.putExtra(KeyF.Intentdata.title,data.get(position).getTitle());
                intent.putExtra(KeyF.Intentdata.details,data.get(position).getDetails());
                context.startActivity(intent);
            }
        });








        //setting onclicklistner
        view.findViewById(R.id.home_page_more_textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStep.notification=true;
                context.startActivity(new Intent(context,AllStep.class));
            }
        });












        //loading data
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.removeAll(data);
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    data.add(ds.getValue(DataFile.class));
                }
                lvadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return view;
    }











    public void onItemClick(int pos) {
    String arr[]=new String[]{"333", "999", "109", "106", "1090", "01713373808", "01308443334", "01713373807", "01733201881"};
        String number = arr[pos];
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }






}