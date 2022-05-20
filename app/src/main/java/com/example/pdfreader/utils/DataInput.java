

package com.example.pdfreader.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pdfreader.activities.MainActivity;

public class DataInput extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private TextView outputTextView;
    private Intent intent;

    private static final int READ_REQUEST_CODE = 42;
    private static final String PRIMARY = "primary";
    private static final String LOCAL_STORAGE = "/storage/self/primary/";
    private static final String EXT_STORAGE = "/storage/self/primary/";


    void openDocument() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
//                Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
                Log.d("yyyy", uri.getPath());
                readPdfFiles(uri);
            }
        }

    }*/

    public void readPdfFiles(Uri uri) {
        String fullPath;
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
        if (uri.getPath().contains(PRIMARY)) {
            fullPath = LOCAL_STORAGE + uri.getPath().split(":")[1];
        } else {
            fullPath = EXT_STORAGE + uri.getPath().split(":")[1];
        }
        Toast.makeText(this, fullPath, Toast.LENGTH_LONG).show();
        Log.i("FULLPATH1", fullPath);
        /*String stringParser;
        PdfDocument pdfdoc = new PdfDocument(fullPath);
        stringParser = PdfText*/


    }



    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSIONS_COUNT = 2;
    private static final int REQUEST_PERMISSIONS = 1234;

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "hey",Toast.LENGTH_SHORT).show();
        Log.d("RESUMEFUNC", "CHECKING THINGS HERE");
        if (notPermissions()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Boolean notPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionPtr = 0;
            while (permissionPtr < PERMISSIONS_COUNT) {
                if (checkSelfPermission(PERMISSIONS[permissionPtr]) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                permissionPtr++;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (notPermissions()) {
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                recreate();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}



/*

package com.example.tmsreader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

public class DataInput extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private  static final String LOCAL_STORAGE = "/storage/self/primary/";
    //private  static final String LOCAL_STORAGE = "/storage/self/primary/";



    private static final String PRIMARY = "primary";
    public void main(){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(resultData != null){
                Uri uri = resultData.getData();
                Toast.makeText(this,uri.getPath(),Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void readPdfFile(Uri uri){
        String fullpath;
        if(uri.getPath().contains(PRIMARY){
            fullpath = LOCAL_STORAGE + uri.getPath().split(":")[1];
        }
        else{
            fullpath = EXT_STORAGE + uri.getPath().split(":")[1];
        }
        String stringParser;
        try{
            PdfReader pdfReader = new PdfReader(fullpath);
            stringParser = PdfTextExtractor.getTextFromPage(pdfReader, 1).trim();
            pdfReader.close();
            textToSpeech.speak(stringParser, TextToSpeech.QUEUE_FLUSH,null,null);

        }
        catch(Exception e){
            e.printStackTrace();
        }


    }


}
    *//*
*//*static ArrayList pdfs=new ArrayList<File>();// = new File[0];

    public static void Search_Dir(File dir){
        String pdfPattern = ".pdf";
        File FileList[] =dir.listFiles();
        if(FileList != null){
            for(int i = 0; i < FileList.length; i++){
                if(FileList[i].isDirectory()) {
                    Search_Dir(FileList[i]);
                }
                else {
                    if(FileList[i].getName().endsWith(pdfPattern)){
                        pdfs.add(FileList[i]);
                    }
                }
            }
        }
        else{
            Log.d("NULLHERE","OOPS NULL HERE");
        }

    }


    public static ArrayList<Model1> setData(){


        ArrayList list = new ArrayList<Model1>();
        for(int i = 0;i<pdfs.size();i++){
            list.add(new Model1(((File)pdfs.get(i)).getName(),((File)pdfs.get(i)).getPath()));
        }

        return list;

    }
}

*//*
*/