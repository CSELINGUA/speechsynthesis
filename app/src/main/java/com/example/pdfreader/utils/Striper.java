package com.example.pdfreader.utils;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.example.pdfreader.models.TTSModel;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.*;
import java.util.ArrayList;

public class Striper {
    Uri uri;
    String extractedText;
    ArrayList<TTSModel> separatedText;
    File file;
    InputStream inputStream;
    ContentResolver contentResolver;
    Context context;


    public Striper(Uri uri,ContentResolver contentResolver,Context context) {
        this.uri = uri;
        this.contentResolver = contentResolver;
        this.context = context;

    }

    public String stripText(int page) {
        PDFBoxResourceLoader.init(context);
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //assetManager = getAssets();
        String parsedText = null;
        PDDocument document = null;


        try {
            document = PDDocument.load(inputStream);/* .load(assetManager.open("Certification.pdf"));*/
        } catch (IOException e) {
            Log.e("PdfBox-Android-Sample", "Exception thrown while loading document to strip", e);
        }
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(page);
            pdfStripper.setEndPage(page);
            parsedText = "Parsed text: " + pdfStripper.getText(document);
        } catch (IOException e) {
            Log.e("PdfBox-Android-Sample", "Exception thrown while stripping text", e);
        } finally {
            try {

                if (document != null) document.close();

            } catch (IOException e) {
                Log.e("PdfBox-Android-Sample", "Exception thrown while closing document", e);
            }
        }
        extractedText = parsedText;
        return extractedText;
    }

    public ArrayList<TTSModel> separateText(){
        separatedText = new ArrayList<>();

        String [] texts = extractedText.split("., ");
        for(int i =0; i<texts.length;i++){
            //texts[i] = texts[i] + texts[i + 1];
            separatedText.add(new TTSModel(texts[i]));
        }

        return separatedText;
    }

}
