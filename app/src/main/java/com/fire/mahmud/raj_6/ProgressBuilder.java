package com.fire.mahmud.raj_6;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBuilder {
    private static ProgressDialog pd=null;
    public static void showDownloading(Context context){
        pd=new ProgressDialog(context);
        pd.setMessage("Downloading...");
        pd.setCancelable(false);
        pd.show();
    }
    public static void showUploading(Context context){
        pd=new ProgressDialog(context);
        pd.setMessage("Uploading...");
        pd.setCancelable(false);
        pd.show();
    }
    public static void showWaiting(Context context){
        pd=new ProgressDialog(context);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
    }
    public static void dissmiss(){
        pd.dismiss();
    }
}
