package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    //dclearing interface
    EditText name,password,email;
    Spinner age,gender;
    ImageView face;









    //declearing variable
    Bitmap bitmap=null;
    Uri uri=null;
    Context context;







    //declearing firebase
    StorageReference sr;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //assining interface
        name=findViewById(R.id.signup_fullname_edittext);
        password=findViewById(R.id.signup_password_edittext);
        email=findViewById(R.id.signup_email_edittext);
        age=findViewById(R.id.signup_age_spinner);
        gender=findViewById(R.id.signup_gender_spinner);
        face=findViewById(R.id.signup_pic_imageview);





        //assining variable
        context=this;





        //assining firebase
        sr=FirebaseStorage.getInstance().getReference(KeyF.firebase.Users.toString());
        dr=FirebaseDatabase.getInstance().getReference(KeyF.firebase.Users.toString());











        //adding adapter
        gender.setAdapter(ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_dropdown_item));
        age.setAdapter(ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_dropdown_item));









        //setting onclick listener
        findViewById(R.id.sign_up_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LogIn.class));
            }
        });








        //setting up onclicklistner
        findViewById(R.id.signup_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chaecking validity
                if (name.getText().toString().isEmpty()){
                    name.setError("Name required to sign up");
                    return;
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Name required to sign up");
                    return;
                }
                if (email.getText().toString().isEmpty()){
                    email.setError("Name required to sign up");
                    return;
                }
                if (gender.getSelectedItem().toString().equals("Gender")){
                    Toast.makeText(SignUp.this, "Select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (age.getSelectedItem().toString().equals("Age")){
                    Toast.makeText(SignUp.this, "Select your age", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap==null){
                    Toast.makeText(SignUp.this, "Select your picture", Toast.LENGTH_SHORT).show();
                    return;
                }

                //showing progress
                ProgressBuilder.showWaiting(SignUp.this);







                //detecting number or email
                if (email.getText().toString().contains("@")){
                    emailVerificaiton();
                }else{
                    try {
                        int number = Integer.valueOf(email.getText().toString());
                        numberVerification(number);
                    }catch (Exception e){
                        email.setError("Use email or number");
                        ProgressBuilder.dissmiss();
                    }
                }
            }
        });





        //choosing picture
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,4009);
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==4009&&resultCode==Activity.RESULT_OK){
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                face.setImageBitmap(bitmap);
                uri=data.getData();
                VerificationCode.file=data.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    //number signup
    private void numberVerification(int number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+880"+number,120, TimeUnit.SECONDS,this,VerificationCode.callbacks);
        Intent intent=new Intent(SignUp.this,VerificationCode.class);
        VerificationCode.userData=new UserData(name.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                age.getSelectedItem().toString(),
                gender.getSelectedItem().toString());
        ProgressBuilder.dissmiss();
        startActivity(intent);
        finish();
    }











    //email signup
    private void emailVerificaiton(){
        FirebaseAuth auth=FirebaseAuth.getInstance();



        auth.createUserWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //user created



                    compleateLogin(task.getResult().getUser(),sr,dr,uri,
                            name.getText().toString(),
                            password.getText().toString(),
                            age.getSelectedItem().toString(),
                            gender.getSelectedItem().toString(),
                            email.getText().toString(),context);
                }else{
                    ProgressBuilder.dissmiss();
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }







    //compleating signup
    public static void compleateLogin(final FirebaseUser user, StorageReference store, final DatabaseReference datab, Uri file,
                               final String userfullname, final String userpassword, final String userage, final String usergender, final String email, final Context context){

        store.child(user.getUid()+".jpg").putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //profile pic uploaded







                        //gettng file donwload lisk
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //downlaod link
                                
                                
                                
                                
                                UserProfileChangeRequest upcr=new UserProfileChangeRequest.Builder()
                                        .setDisplayName(userfullname)
                                        .setPhotoUri(uri)
                                        .build();




                                //updating user profile
                                user.updateProfile(upcr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            UserData ud=new UserData(userfullname,
                                                    email,
                                                    userpassword,
                                                    userage,
                                                    usergender);





                                            //uploading userdata
                                            datab.child(user.getUid())
                                                    .setValue(ud).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    ProgressBuilder.dissmiss();
                                                    if (task.isSuccessful()){
                                                        context.startActivity(new Intent(context,MainActivity.class));
                                                    }else{
                                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            ProgressBuilder.dissmiss();
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ProgressBuilder.dissmiss();
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ProgressBuilder.dissmiss();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
