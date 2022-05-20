package com.example.pdfreader.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import com.example.pdfreader.models.Model1;

import java.util.ArrayList;

public class GetPDFS extends Activity {
    ContentResolver contentResolver;


    public GetPDFS (ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public ArrayList<Model1> loadData() {
        ArrayList<Model1> pdfList = new ArrayList<>();
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE
        };
        String mimeType = "application/pdf";

        String whereClause = MediaStore.Files.FileColumns.MIME_TYPE + " IN ('" + mimeType + "')";
        String orderBy = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

        Cursor cursor = /*super.getContentResolver()*/contentResolver.query(MediaStore.Files.getContentUri("external"),
                projection,//null,
                whereClause,
                null,
                orderBy);//orderBy);
        while (cursor.moveToNext()) {
            Long pdfId = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
            String pdfMime = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
            Long pdfDateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED));
            Long pdfDateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));
            String pdfDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
            String pdfTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
            Long pdfSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));

            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri("external"), pdfId);

            Log.d("PROJECTION",contentUri.getPath());
            pdfList.add(new Model1(contentUri,pdfDisplayName,pdfSize,pdfDateAdded,pdfDateModified));


        }
        cursor.close();
        return pdfList;
    }
    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }
    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void init(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }

}