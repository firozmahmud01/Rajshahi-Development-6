package com.fire.mahmud.raj_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VerificationCode extends AppCompatActivity {
    //static content
    public static PhoneAuthCredential pac=null;
    public static String verificationId=null;
    public static boolean check=false;
    public static Context context=null;
    public static UserData userData=null;
    public static Uri file=null;
    public static Activity ac;







public static PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        pac=phoneAuthCredential;
        if (check){
            compleateLogin();
        }
    }

    @Override
    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
        verificationId=s;
        if (check){
            Toast.makeText(context, "Code sended", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
        if (context!=null) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    
    
};







    //declearing variableslk
    TextWatcher tw;
    int focus=0;
    boolean isEmpty=false;






    //declearing interface
    EditText num1,num2,num3,num4,num5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        //assining static
        context=this;
        ac=VerificationCode.this;








        //code sended or not
        if (verificationId!=null){
            Toast.makeText(context, "Code sended", Toast.LENGTH_SHORT).show();
        }








        //verification complete or not
        if (pac==null){
            check=true;
        }else{
            compleateLogin();
        }








        //assining interface
        num1=findViewById(R.id.verification_code_numb1_editText);
        num2=findViewById(R.id.verification_code_numb2_editText);
        num3=findViewById(R.id.verification_code_numb3_editText);
        num4=findViewById(R.id.verification_code_numb4_editText);
        num5=findViewById(R.id.verification_code_numb5_editText);







        //setting onclicklistener
        findViewById(R.id.verification_code_verifytextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking validity
                if (num1.getText().toString().isEmpty()){
                    num1.setError("This code missing");
                    return;
                }
                if (num2.getText().toString().isEmpty()){
                    num2.setError("This code missing");
                    return;
                }
                if (num3.getText().toString().isEmpty()){
                    num3.setError("This code missing");
                    return;
                }
                if (num4.getText().toString().isEmpty()){
                    num4.setError("This code missing");
                    return;
                }
                if (num5.getText().toString().isEmpty()){
                    num5.setError("This code missing");
                    return;
                }
                if (verificationId==null){
                    Toast.makeText(VerificationCode.this, "You didn't received any code yet.", Toast.LENGTH_SHORT).show();
                    return;
                }









                //verifing with code
                pac=PhoneAuthProvider.getCredential(verificationId,
                        num1.getText().toString()+
                                num2.getText().toString()+
                                num3.getText().toString()+
                                num4.getText().toString()+
                                num5.getText().toString()
                        );
                compleateLogin();

            }
        });








        //assining textwatcher
        tw=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                focus=curentlyFocuses(getCurrentFocus());
                if (s.toString().isEmpty()){
                    isEmpty=false;
                }else{
                    focusnext();
                }
            }
        };









        //setting onkeylistener
        num1.addTextChangedListener(tw);
        num2.addTextChangedListener(tw);
        num3.addTextChangedListener(tw);
        num4.addTextChangedListener(tw);
        num5.addTextChangedListener(tw);
    }





    private int curentlyFocuses(View view){
        switch (view.getId()){
            case R.id.verification_code_numb1_editText:
                return 0;
            case R.id.verification_code_numb2_editText:
                return 1;
            case R.id.verification_code_numb3_editText:
                return 2;
            case R.id.verification_code_numb4_editText:
                return 3;
            case R.id.verification_code_numb5_editText:
                return 4;
        }
        return 10;
    }








    private void focusnext(){
        if (focus!=4){
            focus++;
            focusedit(focus);
        }
    }









    private void focusprev(){
        if (focus!=0){
            focus--;
            focusedit(focus);
        }
    }






    private void focusedit(int num){
        switch (num){
            case 0:
                num1.requestFocus();
                break;
            case 1:
                num2.requestFocus();
                break;
            case 2:
                num3.requestFocus();
                break;
            case 3:
                num4.requestFocus();
                break;
            case 4:
                num5.requestFocus();
                break;

        }
    }








    //auto code login
    private static void compleateLogin(){
        final FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithCredential(pac).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){




                    //checking
                    if (userData==null){
                        context.startActivity(new Intent(context,MainActivity.class));
                        ac.finish();
                    }else{


                        //declearing finrebase and assining them
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference dr= FirebaseDatabase.getInstance().getReference(KeyF.firebase.Users.toString());
                        StorageReference sr= FirebaseStorage.getInstance().getReference(KeyF.firebase.Users.toString());






                        //completing signup proccess
                        SignUp.compleateLogin(user,sr,dr,file,userData.getUsername()
                                ,userData.getPassword(),
                                userData.getAge(),
                                userData.getGender(),userData.getEmail(),context);
                        ac.finish();

                    }






                }else{
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
