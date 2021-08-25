package com.fire.mahmud.raj_6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    Context context;
    FirebaseUser user;
    public RecyclerAdapter(Context context){
        this.context=context;
        this.user= FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview, parent, false);
        MyViewHolder mvh=new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String text="";
        int id=0;
        int color=0;
        switch (position){
            case 0:
                text="আবেদন করুন";
                id=R.drawable.applicationbox;
                color=context.getResources().getColor( R.color.recyclebluecolor);
                break;
            case 1:
                text="অভিযোগ করুন";
                id=R.drawable.ic_assignment;
                color=context.getResources().getColor(R.color.recyclegreen);
                break;
            case 2:
                text="Covid-19";
                id=R.drawable.coronavirus;
                color=context.getResources().getColor(R.color.recyclered);
                break;
            case 3:
                text="Galary";
                id=R.drawable.galary;
                color=context.getResources().getColor(R.color.recycleorrenge);
                break;
        }






        holder.tv.setText(text);
        holder.iv.setImageResource(id);
        holder.cv.setBackgroundColor(color);
        final int fid=id;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fid!=R.drawable.galary&&position!=2) {
                    if (position==1){
                        Report.twomust=true;
                    }else if (position==2){
                        Report.attachment=true;
                    }else if(position==0){
                        Report.attachment=false;
                        Report.twomust=false;
                    }
                    if (user==null){
                        context.startActivity(new Intent(context,LogIn.class));
                        return;
                    }
                    context.startActivity(new Intent(context, Report.class));
                }else if (position==2) {
                    context.startActivity(new Intent(context,Covid.class));
                }else{
                    context.startActivity(new Intent(context, GaleryPhoto.class));
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //declearing interface
        ImageView iv;
        TextView tv;
        ConstraintLayout cv;



        public MyViewHolder(View view) {
            super(view);
            cv=view.findViewById(R.id.custom_recyclerview_cardview);
            iv=view.findViewById(R.id.custom_recyclerview_imageView);
            tv=view.findViewById(R.id.custom_recyclerview_textView);
        }

    }
}
