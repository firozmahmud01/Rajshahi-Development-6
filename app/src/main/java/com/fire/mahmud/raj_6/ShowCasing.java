package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ShowCasing extends AppCompatActivity {
    ViewPager vp;
    FragmentPagerAdapter fpa;
    String data[]={KeyF.groupchild.shasthoBabostha,
            KeyF.groupchild.sikkha,KeyF.groupchild.biddhuth,
            KeyF.groupchild.samajikunnaon,KeyF.groupchild.nirapotta,
            KeyF.groupchild.colomanKarrjokrom,KeyF.groupchild.sarakbabosstha};
    int icon[]=new int[]{R.drawable.sastho,
            R.drawable.sikkha,
            R.drawable.biddhuth,
            R.drawable.samajik,
            R.drawable.nirapotta,
            R.drawable.soloman,
            R.drawable.sorokbabosstha
    };
    TabLayout tl;
    Context context;
    int pos=0;
    ViewFlipper vf;
    CustomTab ct;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_casing);
        setSupportActionBar((Toolbar)findViewById(R.id.mytoolbar));
        vf=findViewById(R.id.show_casing_viewflipper);
        user=FirebaseAuth.getInstance().getCurrentUser();
        vp=findViewById(R.id.show_casing_view_pager);
        tl=findViewById(R.id.show_casing_tab);
        setTitle("");
        context=this;
        fpa=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return new Galary(context,data,icon,vp);
                }
                return /*new AllStep(context,data[position-1])*/new ButtonView(data[position-1],context);
            }

            @Override
            public int getCount() {
                return data.length+1;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position==0){
                    return KeyF.groupchild.galary;
                }
                return KeyF.groupchild.banglaLikha(data[position-1]);
            }
        };
        vp.setAdapter(fpa);
        ct=new CustomTab(this,vf,vp,data.length+1);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!ct.canpagechange){
                    ct.canpagechange=true;
                    pos=position;
                    return;
                }
                    ct.x = 0;
                    ct.canchange=false;
                    ct.action(pos - position);
                    pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        tl.setupWithViewPager(vp);
        for(int x=0;x<data.length+1;x++) {
            if (x==0) {
                tl.getTabAt(x).setIcon(getPicId(x));
                ct.addView(getPicId(0), KeyF.groupchild.galary);
                continue;
            }
            tl.getTabAt(x).setIcon(getPicId(x));
            ct.addView(getPicId(x),KeyF.groupchild.banglaLikha(data[x-1]));
        }











        Toolbar tb=findViewById(R.id.mytoolbar);
        //declearing and assining drawer
        final DrawerLayout dl=findViewById(R.id.mydrawer);
        final ActionBarDrawerToggle abdt=new ActionBarDrawerToggle(ShowCasing.this,dl,tb,R.string.app_name,R.string.package_name);
        abdt.getDrawerArrowDrawable().setColor(Color.BLACK);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        NavigationView nv=findViewById(R.id.main_activity_nav_view);

        View v=nv.getHeaderView(0);





        TextView username=v.findViewById(R.id.logout_button);
        if(user!=null) {
            Picasso.with(this).load(user.getPhotoUrl()).into((ImageView) v.findViewById(R.id.profile_image));
            username.setText(user.getDisplayName());
        }




        //adding onitemselectlistener
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_about:
                        startActivity(new Intent(ShowCasing.this,About.class));
                        break;
                    case R.id.nav_dashboard:
//                        startActivity(new Intent(ShowCasing.this,ShowCasing.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(ShowCasing.this,ProfileOfShariar.class));
                        break;
                    case R.id.nav_progressactivity:
                        Toast.makeText(context, "Under development", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_report:
                        startActivity(new Intent(ShowCasing.this,Report.class));
                        break;
                }
                dl.closeDrawers();
                return true;
            }
        });










        //starting service
        if (!NoticeSender.isRunning){
            startService(new Intent(ShowCasing.this,NoticeSender.class));
        }

    }
    private int getPicId(int index){
        if (index==0){
            return R.drawable.ic_home;
        }
        return icon[index-1];
    }



}
