package com.fire.mahmud.raj_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;

public class CustomTab {
    ArrayList<Integer>icons;
    ArrayList<String>names;
    ViewFlipper vf;
    Context context;
    LayoutInflater li;
    boolean canpagechange=true;
    boolean canchange=true;
    ViewPager tab;
    int total=0;
    float x;
    public CustomTab(Context context,ViewFlipper vf,ViewPager tab,int total){
        this.icons=new ArrayList<>();
        this.total=total;
        this.names=new ArrayList<>();
        this.vf=vf;
        this.tab=tab;
        this.li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        addlistener();
    }

    public void addView(int icon,String name){
        View view=li.inflate(R.layout.viewflippercustom,null);
        ImageView im1=view.findViewById(R.id.viewflipper_frist_imageView)
                ,im2=view.findViewById(R.id.viewflipper_second_imageView)
                ,im3=view.findViewById(R.id.viewflipper_third_imageView);
        icons.add(icon);
        names.add(name);
        TextView tv=view.findViewById(R.id.customTab_name_textview);
        tv.setText(name);
        int position=icons.size()==1?0:icons.size()-2;
        im1.setImageResource(icons.get(position));
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canpagechange=false;
                x=0;
                action(1);
            }
        });
        im2.setImageResource(icon);
        im3.setImageResource(icons.get(0));
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        canpagechange=false;
                        x=0;
                        action(-1);

            }
        });
        vf.addView(view);
        View fristview=vf.getChildAt(0);
        ImageView iv=fristview.findViewById(R.id.viewflipper_frist_imageView);
        iv.setImageResource(icon);
        if (icons.size()<2){
            return;
        }
        View aview=vf.getChildAt(icons.size()-2);
        ImageView lastic=aview.findViewById(R.id.viewflipper_third_imageView);
        lastic.setImageResource(icon);
    }
    private void addlistener() {
        vf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        canpagechange=false;
                        action(event.getX());
                        break;
                }
                return true;
            }
        });
    }
        public void action(float y){
            if (x > y) {
//                vf.setInAnimation(context, R.anim.lpictrans);
//                vf.setOutAnimation(context, R.anim.fpictrans);
//                View view;
//                if (present < total - 1) {
//                    if(canchange)tab.setCurrentItem(++present);else canchange=true;
////                    view=vf.getChildAt(present);
//                } else {
//                    present = 0;
//                    if (canchange)tab.setCurrentItem(present);else canchange=true;
////                    view=vf.getChildAt(present);
//                }

                vf.showNext();

            } else if (x < y) {
//                vf.setInAnimation(context, R.anim.previnanim);
//                vf.setOutAnimation(context, R.anim.prevoutanim);
                vf.showPrevious();
//                if (present > 0) {
//                    if (canchange)tab.setCurrentItem(--present);else canchange=true;
//                } else {
//                    present = total - 1;
//                    if(canchange)tab.setCurrentItem(present);else canchange=true;
//                }
            }
            View view=vf.getCurrentView();
            int count=vf.indexOfChild(view);
            if(canchange)tab.setCurrentItem(count);else canchange=true;
        }
}
