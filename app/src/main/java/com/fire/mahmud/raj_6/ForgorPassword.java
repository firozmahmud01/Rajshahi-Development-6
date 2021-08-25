package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgorPassword extends AppCompatActivity {
    //assining interface
    EditText email;







    //declearing firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgor_password);
        //assining interface
        email=findViewById(R.id.signup_email_edittext);








        //setting onclick listner
        findViewById(R.id.forgot_password_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ForgorPassword.this, MainActivity.class));
                        } else {
                            Toast.makeText(ForgorPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                    });







                    }
                });
            }
        }