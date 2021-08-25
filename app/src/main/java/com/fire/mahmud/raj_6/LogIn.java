package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.Key;
import java.util.concurrent.TimeUnit;

public class LogIn extends AppCompatActivity {
    //declearing interface
    EditText username,password;





    //declearing variables
    Context context;





    //declearing firebase
    FirebaseAuth mAuth;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);






        //assining variables
        context=this;









        //interface assining
        username=findViewById(R.id.log_in_username);
        password=findViewById(R.id.log_in_password);




        //firebase assining
        mAuth=FirebaseAuth.getInstance();
        dr= FirebaseDatabase.getInstance().getReference(KeyF.firebase.Passwords.toString());








        findViewById(R.id.log_in_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking validity
                if (username.getText().toString().isEmpty()) {
                    username.setError("Email needed to login");
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    username.setError("Password need to login");
                    return;
                }






                ProgressBuilder.showWaiting(context);




                //detecting
                detect();
            }
        });









        //signup starting
        findViewById(R.id.log_in_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });











        //forgot password starting
        findViewById(R.id.log_in_forgetpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,ForgorPassword.class));
            }
        });
    }







    private void loginwithEmail(){
        mAuth.signInWithEmailAndPassword(username.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ProgressBuilder.dissmiss();
                if (task.isSuccessful()){
                    startActivity(new Intent(LogIn.this,MainActivity.class));
                }else {
                    Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.log_in_forgetpassword).setVisibility(View.VISIBLE);
                }
            }
        });
    }




    //checking passwords
    public void chackpas(final int number){
        dr.child(KeyF.firebase.Passwords.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(String.class).contains(password.getText().toString())){
                    numberLogin(number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ProgressBuilder.dissmiss();
                Toast.makeText(context, databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        }) ;
    }





    //detecting number or email
    private void detect(){
            if (username.getText().toString().contains("@")) {
                loginwithEmail();
            } else {
                try {
                    int number = Integer.valueOf(username.getText().toString());
                    chackpas(number);
                } catch (Exception e) {
                    username.setError("Use email or number");
                    ProgressBuilder.dissmiss();
                }
            }

    }







    //login with number
    private void numberLogin(int number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+880"+number,2000,TimeUnit.SECONDS,this,VerificationCode.callbacks);
        ProgressBuilder.dissmiss();
        startActivity(new Intent(LogIn.this,VerificationCode.class));
    }


}
