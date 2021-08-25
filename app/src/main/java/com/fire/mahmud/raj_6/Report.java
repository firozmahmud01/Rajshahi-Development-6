package com.fire.mahmud.raj_6;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;

public class Report extends AppCompatActivity {
    //static declearation
    public static boolean twomust=false;
    public static boolean attachment=false;










    //declearing interface
    EditText address1,address2,address3,compname,zip,phone,report;
    ImageView picture,attach;










    //declearing variables
    Uri image=null,file=null;
    String key;








    //declearing firebase
    FirebaseUser user;
    StorageReference sr;
    DatabaseReference dr;






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_report);
        //assining interface
        address1=findViewById(R.id.report_address1_edittext);
        address2=findViewById(R.id.report_address2_edittext);
        address3=findViewById(R.id.report_address3_edittext);
        compname=findViewById(R.id.report_name_editText);
        zip=findViewById(R.id.report_zip_edittext);
        phone=findViewById(R.id.report_number_edittext);
        report=findViewById(R.id.report_details_editText);
        picture=findViewById(R.id.report_camera_imageView);
        attach=findViewById(R.id.report_attachment_imageview);








        //traking activity
        String title="অভিযোগ পত্র";
        if (twomust) {
            title = "পরামর্শ বক্স";
        }else if (attachment){
            title = "আবেদন বক্স";
        }








        //cheaging titlebar name
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);











        //hidding picture
        if (attachment){
            picture.setVisibility(View.GONE);
        }








        //assining firebase
        sr=FirebaseStorage.getInstance().getReference(KeyF.firebase.Report.toString());
        dr=FirebaseDatabase.getInstance().getReference(KeyF.firebase.Report.toString());
        user=FirebaseAuth.getInstance().getCurrentUser();









        //setting username
        if (user!=null) {
            ((TextView) findViewById(R.id.report_username_textview)).setText(user.getDisplayName());
        }








        findViewById(R.id.report_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking validity
                if (address1.getText().toString().isEmpty()){
                    address1.setError("Fill it");
                    return;
                }
                if (address2.getText().toString().isEmpty()){
                    address2.setError("Fill it");
                    return;
                }
                if (address3.getText().toString().isEmpty()){
                    address3.setError("Fill it");
                    return;
                }
                if (compname.getText().toString().isEmpty()){
                    compname.setError("Fill it");
                    return;
                }
                if (zip.getText().toString().isEmpty()){
                    zip.setError("Fill it");
                    return;
                }
                if (phone.getText().toString().isEmpty()){
                    phone.setError("Fill it");
                    return;
                }
                if (report.getText().toString().isEmpty()){
                    report.setError("Fill it");
                    return;
                }
                if ((image==null||file==null)&&attachment){
                    Toast.makeText(Report.this, "Check picture or attachment", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (image==null&&file==null){
                    Toast.makeText(Report.this, "Check picture or attachment", Toast.LENGTH_SHORT).show();
                    return;
                }




                //showing progressber
                ProgressBuilder.showUploading(Report.this);







                //uploading everything
                uploadFile();

            }
        });



//01715603213







        //making static false
        attachment=false;
        twomust=false;

    }











    //upload file
    public void uploadFile(){
        key=dr.push().getKey();
        if (file==null){
            uploadImage("");
            return;
        }




        sr.child(key).child(file.getLastPathSegment()).putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uploadImage(uri.toString());
                        }
                    });
                }else{



                    //failed
                    Toast.makeText(Report.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }






            }
        });



    }







    //upload image
    public void uploadImage(final String attachment){
        if (image==null){
            uploadData("",attachment);
            return;
        }


        sr.child(key+".jpg").putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uploadData(uri.toString(),attachment);
                        }
                    });
                }else{



                    //failed
                    Toast.makeText(Report.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }






            }
        });



    }








    public void uploadData(String picurl,String attachurl){



        ReportData rd=new ReportData(user.getDisplayName()
                ,user.getUid(),user.getEmail()
                ,address1.getText().toString()
                ,address2.getText().toString()
                ,address3.getText().toString()
                ,compname.getText().toString()
                ,zip.getText().toString()
                ,phone.getText().toString()
                ,report.getText().toString()
                ,picurl,attachurl,key);


        dr.child(key).setValue(rd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //stoping progressber
                ProgressBuilder.dissmiss();





                //report work finished

                if (task.isSuccessful()) {
                    finish();
                }else{
                    Toast.makeText(Report.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }








            }
        });


    }

}
