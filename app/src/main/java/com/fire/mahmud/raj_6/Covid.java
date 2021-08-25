package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Covid extends AppCompatActivity {
    //interface
    ListView lv;







    //variables
    String questions[];
    boolean checked[];







    //firebase
    DatabaseReference dr;






    //baseadapter
    BaseAdapter ba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);





        //database assining value
        dr= FirebaseDatabase.getInstance().getReference("Covid");







        //variables
        questions=new String[]{
                "করোনা ভাইরাস আক্রান্ত রোগী আছে এরকম কোন বাড়ি বা এলাকায় কি আপনি সাম্প্রতিক যাত্রা করেছেন?",
                "১৪ দিন কোয়ারেন্টাইনে থাকা কোন করনা আক্রান্ত রোগির সংস্পর্শে এসেছেন বা কমপক্ষে ৩ ফুট দুরত্ত্বে অবস্থান করেছেন?",
                "কমপক্ষে এক সপ্তাহের মধ্যে কি আপনি জ্বর বা কাশি অনুভব করেছেন বা এখন কি আপনার জ্বর বা কাশি আছে?",
                "আপনার কি কাশি, শ্বাসকষ্ট বা দীর্ঘশ্বাস নিতে অসুবিধা হচ্ছে?",
                "আপনি কি গলা ব্যাথা অনুভব করছেন?",
                "আপনার মাথা বা পেশীতে ব্যাথা আছে?",
                "আপনি কি নিয়মিত মাস্ক ব্যাবহার করছেন?"
        };
        checked=new boolean[]{
                false,
                false,
                false,
                false,
                false,
                false,
                false
        };








        //interface
        lv=findViewById(R.id.covid_listview);






        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return questions.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
            LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @Override
            public View getView(int position, View cv, ViewGroup parent) {
                if (cv==null){
                    cv=li.inflate(R.layout.covid_custom_layout,null);
                }


                TextView question=cv.findViewById(R.id.covid_question_textView);



                question.setText(questions[position]);


                final int pos=position;

                final CheckBox yes=cv.findViewById(R.id.covid_yes_textview),
                no=cv.findViewById(R.id.covid_no_textview);


                if (checked[position]){
                    yes.setChecked(true);
                    no.setChecked(false);
                }else{
                    yes.setChecked(false);
                    no.setChecked(true);
                }






                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            no.setChecked(false);
                            checked[pos]=true;
                    }
                });



                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            yes.setChecked(false);
                            checked[pos]=false;
                    }
                });


                return cv;
            }
        };





        findViewById(R.id.covid_submit_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=findViewById(R.id.covid_name)
                        ,zilla=findViewById(R.id.covid_zilla)
                        ,phone=findViewById(R.id.covid_phone)
                        ,post=findViewById(R.id.covid_post)
                        ,father=findViewById(R.id.fathername),
                upozilla=findViewById(R.id.covid_upozilla),
                wordno=findViewById(R.id.covid_wordno);


                //checking validity
                if (name.getText().toString().isEmpty()){
                    name.setError("Fill this before submit");
                    return;
                }

                if (zilla.getText().toString().isEmpty()){
                    zilla.setError("Fill this before submit");
                    return;
                }

                if (phone.getText().toString().isEmpty()){
                    phone.setError("Fill this before submit");
                    return;
                }


                if (post.getText().toString().isEmpty()){
                    post.setError("Fill this before submit");
                    return;
                }
                if (wordno.getText().toString().isEmpty()){
                    wordno.setError("Fill this before submit");
                    return;
                }
                if (father.getText().toString().isEmpty()){
                    father.setError("Fill this before submit");
                    return;
                }
                if (upozilla.getText().toString().isEmpty()){
                    upozilla.setError("Fill this before submit");
                    return;
                }







                dr.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(new CovidData(name.getText().toString(),
                                father.getText().toString(),
                                phone.getText().toString(),
                                zilla.getText().toString(),
                                upozilla.getText().toString(),
                                post.getText().toString(),
                                wordno.getText().toString(),
                                checked
                                ))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Covid.this, "Thank you for helping us", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(Covid.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });





        findViewById(R.id.covid_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run(checked);
            }
        });





        lv.setAdapter(ba);

    }




    public void run(boolean ch[]){
        int count=0;
        for (boolean c:ch){
            if (c)count++;
        }
        if (count<=ch.length/2){
            finish();
            return;
        }
        findViewById(R.id.covid_floatingActionButton).setVisibility(View.GONE);
        findViewById(R.id.covid_scrollview).setVisibility(View.VISIBLE);
        findViewById(R.id.covid_listview).setVisibility(View.GONE);


    }
}