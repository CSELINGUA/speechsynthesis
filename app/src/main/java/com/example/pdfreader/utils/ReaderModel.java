package com.example.pdfreader.utils;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pdfreader.R;

import java.util.HashMap;
import java.util.Locale;


public class ReaderModel extends AppCompatActivity implements TextToSpeech.OnUtteranceCompletedListener {
    private final String UTTER_ID = "utterance";


    Button btnSpeak;
    EditText etText;
    TextToSpeech speaker;


    HashMap<String, String> ttsOptions;
    //MainViewHandler g_hnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text1);

        //g_hnd = new MainViewHandler(ReaderModel.this);
        init();
    }

    private void init() {
        speaker = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttsOptions = new HashMap<String, String>();
                ttsOptions.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTER_ID);

                speaker.setLanguage(Locale.ENGLISH);
            }
        });
        speaker.setOnUtteranceCompletedListener(this);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etText.getText().length() > 0)
//                    speaker.speak(etText.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                    speaker.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, ttsOptions);
            }
        });
        etText = (EditText) findViewById(R.id.etText);
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        if (utteranceId.equals(UTTER_ID)) {
            Log.d("textToSpeech", "completed");
            speaker.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, ttsOptions);
            /*
            // for more common usage, use `Handler` instead of `runOnUiThread()`.
            // block
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SpeakingNotepad.this, "completed", Toast.LENGTH_LONG).show();
                    btnSpeak.setBackgroundColor(Color.BLACK);
                }
            });
            */

            // then, you just call `sendMessage()`.
            //g_hnd.sendMessage(TTS_DONE);
        }
    }
}














/*package com.example.tmsreader;


import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Locale;

public class ReaderModel extends AppCompatActivity implements TextToSpeech.OnUtteranceCompletedListener, TextToSpeech.OnInitListener   {



    File root;
    AssetManager assetManager;
    String extractedText;
    private TextToSpeech speakText = null;


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text1);



        //

        //speak(this,"this is some random text you have to listen to ");


    }


    void speak(final Context context, final String text) {

        try{

        speakText = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @SuppressLint("NewApi")
            @Override
            public void onInit(int i) {
                speakText.setLanguage(Locale.US);

                if (i == TextToSpeech.SUCCESS) {
                    speakText.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                            speakText.speak("this is some random text you have to listen to ", TextToSpeech.QUEUE_FLUSH, null, null);
                        }

                        @Override
                        public void onDone(String s) {
                            Toast.makeText(context, "hello I'm done", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(String s) {
                            Log.d("ERRORS","ERRORS HERE");

                        }
                    });
                    //speakText.speak("this is some random text you have to listen to ", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

    }
        catch (Exception e){
            Toast.makeText(context, "some error occured with the TTS Engine",Toast.LENGTH_SHORT).show();
        }
    }


    private void setup() {
        // Enable Android-style asset loading (highly recommended)
        //PDFBoxResourceLoader.init(getApplicationContext());
        // Find the root of the external storage.
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);



         //Need to ask for write permissions on SDK 23 and up, this is ignored on older versions
        if (ContextCompat.checkSelfPermission(ReaderModel.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(ReaderModel.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    public void stripText() {
        assetManager = getAssets();
        String parsedText = null;
        PDDocument document = null;
        try {
            document = PDDocument.load(assetManager.open("Certification.pdf"));
        } catch (IOException e) {
            Log.e("PdfBox-Android-Sample", "Exception thrown while loading document to strip", e);
        }
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(0);
            pdfStripper.setEndPage(1);
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
        //TextView tv = new TextView(this);
        tv.setText(extractedText);
        //findViewById(R.id.test_).gettext = tv;
        Log.d("yessss", extractedText);

    }


    public void readPdfFile() {


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            speakText = new TextToSpeech(this, this);
            speakText.setLanguage(Locale.getDefault());
            speakText.setOnUtteranceCompletedListener(this);
            Toast.makeText(this,"this is some randome text",Toast.LENGTH_SHORT).show();
            Log.d("imdone","imdone");
            speakText.speak("this is some random text you have to listen to ", TextToSpeech.QUEUE_FLUSH, null, null);

        }
        else{
            Toast.makeText(this,"xxthis is some randome text",Toast.LENGTH_SHORT).show();
            Log.d("errorhere","errorhere");

        }
    }

    @Override
    public void onUtteranceCompleted(String s) {
        Log.d("COMPLETED","Completed successfully");

    }
}*//*

*//*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int i) {
        speakText.setLanguage(Locale.US);
        speakText.setPitch(0.8f);
        speakText.setSpeechRate(1f);

        try {
            //PdfRenderer pdfReader = new PdfRenderer(new File(AssetManager.AssetInputStream("chorus.pdf")));
            //text = PdfTextExtractor.getTextFromPage()
            speakText.speak("Read me, I'm a piece of text", TextToSpeech.QUEUE_FLUSH, null, null);
        } catch (Exception e) {

        }*//*




















































*//*
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;

import java.io.File;
import java.util.Locale;

public class ReaderModel extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private String text;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void readPdfFile(){
        try{
            //PdfRenderer pdfReader = new PdfRenderer(new File(AssetManager.AssetInputStream("chorus.pdf")));
            //text = PdfTextExtractor.getTextFromPage()
            tts.speak("hello there i want to tell you a lot of things", TextToSpeech.QUEUE_FLUSH,null,null);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onInit(int i) {
        tts.setLanguage(Locale.US);
        tts.setPitch(0.8f);
        tts.setSpeechRate(1f);

        readPdfFile();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
*//*
















    *//*public void extractText(File file)

    {
        PDFBoxResourceLoader.init(getApplicationContent());

        try (PDDocument document = PDDocument.load(file)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);

            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                // let the magic happen
                String t
                ext = null;
                try {
                    text = stripper.getText(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // do some nice output with a header
                String pageStr = String.format("page %d:", p);
                System.out.println(pageStr);
                for (int i = 0; i < pageStr.length(); ++i) {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(text.trim());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/