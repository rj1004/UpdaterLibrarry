package com.rahulcompany.updaterlibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class downloadtask extends AsyncTask<Void,String,Void> {


    String urltodownload;
    Activity ctx;

    ProgressDialog d;
    TextView data,perc;



    public downloadtask(String urltodownload, Activity ctx) {

        this.urltodownload = urltodownload;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        d = new ProgressDialog(ctx);
        d.setCancelable(false);
        d.setIndeterminate(true);
        d.setTitle("Downloading");
        d.setMessage("It May take A While..");
        d.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        d.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        d.dismiss();
        File file=new File(Environment.getExternalStorageDirectory().getPath()+"/UpdaterApps/app.apk");

        Toast.makeText(ctx,"App Downloaded to UpdaterApps Folder...Kindly Install it ...",Toast.LENGTH_SHORT).show();

        // TODO: 22/6/21 install app
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".provider", file);
//        try {
//            if (ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),0).versionCode < Build.VERSION_CODES.N) {
//                uri = Uri.fromFile(file);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        intent.setDataAndType(uri,"application/vnd.android.package-archive");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        ctx.startActivity(Intent.createChooser(intent,"Install With"));
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/UpdaterApps";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        File apk = new File(path + "/app.apk");
        if (!apk.exists()) {
            try {
                apk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            apk.delete();
            try {
                apk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        InputStream is;
        OutputStream os;
        try {
            URL url = new URL(urltodownload);
            is=url.openStream();
            os = new FileOutputStream(apk);

            int c;
            int count=0;

            byte[] b = new byte[1024];
            while ((c = is.read(b)) != -1) {
                os.write(b,0,c);
                count++;
            }

            is.close();
            os.close();
            Log.d("count", String.valueOf(count));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
