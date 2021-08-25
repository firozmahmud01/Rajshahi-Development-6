package com.fire.mahmud.raj_6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ButtonView extends Fragment {
    //Declearing interface element
    GridView gv;





    //Adapter variable
    BaseAdapter ba;





    //Firebase declearation
    DatabaseReference dr;




    //Normal variable declearation
    ArrayList<String>path;
    String mainfolder;
    Context context;

    public ButtonView(String mainfolder,Context context) {
        this.mainfolder=mainfolder;
        this.context=context;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_button_view, container, false);




        //assining firebase
        dr= FirebaseDatabase.getInstance().getReference(KeyF.allUpload).child(mainfolder);





        //assing interface elements
        gv=view.findViewById(R.id.buttonList);






        //assining normal variable
        path=new ArrayList<>();








        //assining baseadapter
        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return path.size();
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
                    convertView=inflater.inflate(R.layout.custom_button_view,null);
                }
                int picid=R.drawable.attachment;
                if(mainfolder.equals(KeyF.groupchild.shasthoBabostha)){
                    picid=R.drawable.hospital;
                }


                //assining and setting data
                ((TextView)convertView.findViewById(R.id.custom_button_view_title)).setText(KeyF.getCompressedText(path.get(position),20));
                ((ImageView)convertView.findViewById(R.id.custom_button_view_image)).setImageResource(picid);



                return convertView;
            }
        };







        gv.setAdapter(ba);





        //setting item click listner
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,AllStep.class);
                intent.putExtra(KeyF.AllstepIntent.folder,mainfolder);
                intent.putExtra(KeyF.AllstepIntent.subfolder,path.get(position));
                startActivity(intent);
            }
        });








        //getting all online folder
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                path.removeAll(path);
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    path.add(ds.getKey());
                }
                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
