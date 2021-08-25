package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressBar pb;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        tv=findViewById(R.id.loading_persent);
        pb=findViewById(R.id.progressBar1);
        Drawable draw=getResources().getDrawable(R.drawable.customprogressbar);
        pb.setProgressDrawable(draw);
        pb.setMax(50);
        Thread th=new Thread(){
        @Override
        public void run(){





            for (int loop=1;loop<=50;loop++) {
                try {
                    sleep(100);
                } catch (Exception e) {
                }
                pb.setProgress(loop);
                final int changer=loop;
                tv.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText((changer*2)+"%");
                    }
                });
            }
            startActivity(new Intent(MainActivity.this,ShowCasing.class));
            return;
//            if (user==null){
//                startActivity(new Intent(MainActivity.this,LogIn.class));
//            }else{
//                boolean check=false;
//                try{
//                    check=user.getDisplayName().isEmpty()||user.getDisplayName()==null;
//                }catch (Exception e){
//                    check=true;
//                }
//                if(check){
//                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            startActivity(new Intent(MainActivity.this, LogIn.class));
//                        }
//                    });
//                }else {
//                    startActivity(new Intent(MainActivity.this, ShowCasing.class));
//                }
            }
//        startActivity(new Intent(MainActivity.this,ShowCasing.class));
//        }
        };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
