package com.rahulcompany.updaterlibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Updater {

    private Context ctx;
    private String appname;
    private Activity activity;


    private String new_app_url;
    public Updater(Context ctx,String appname,Activity activity){
        this.ctx = ctx;
        this.appname = appname;
        this.activity=activity;
    }

    public void fetch() {


        if (check_app_update()) {
            AlertDialog dialog=new AlertDialog.Builder(ctx)
                    .setCancelable(false)
                    .setTitle("App Update Available...")
                    .setPositiveButton("Install", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            install_app();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    }).create();
            dialog.show();

        } else {
            AlertDialog dialog=new AlertDialog.Builder(ctx)
                    .setCancelable(true)
                    .setTitle("Your App is Up-to-date...")
                    .setNeutralButton("Lets Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();

        }
    }

    private void install_app() {
        String perm[]={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ctx.checkSelfPermission(perm[0]) == PackageManager.PERMISSION_DENIED ||
                ctx.checkSelfPermission(perm[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, perm, 100);
        } else {
            new downloadtask(new_app_url, activity).execute();
        }

    }


    private boolean check_app_update(){
        ProgressDialog pd=new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        final int[] flag = {0};
        String url="https://appupdater1234.herokuapp.com/app/";
        url = url + appname;

        OkHttpClient client=new OkHttpClient();
        Request r=new Request.Builder()
                .url(url)
                .build();

        client.newCall(r).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data=response.body().string();
                try {
                    JSONObject obj = new JSONObject(data);
                    JSONArray arr1 = obj.getJSONArray("data");
                    JSONObject obj1 = arr1.getJSONObject(0);
                    int latest_version = Integer.parseInt(obj1.getString("version"));
                    int currentversion = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;

                    if (latest_version > currentversion) {
                        new_app_url=obj1.getString("appurl");
                        flag[0] = 1;
                    } else {
                        flag[0] = 2;
                    }


                } catch (JSONException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        while (flag[0] == 0) {

        }

        pd.dismiss();
        if (flag[0] == 1) {
            return true;
        } else {
            return false;
        }


    }


}
