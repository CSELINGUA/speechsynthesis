package com.example.pdfreader.utils;


import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Locale;


public class ReadAloud implements TextToSpeech.OnUtteranceCompletedListener {


    private final String UTTER_ID = "utterance";
    private TextToSpeech speaker;
    private HashMap<String, String> ttsOptions;
    public static final MutableLiveData<Integer> marker = null;



    public void init(Context context ) {
        marker.setValue(0);
        speaker = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttsOptions = new HashMap<String, String>();
                ttsOptions.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTER_ID);

                speaker.setLanguage(Locale.ENGLISH);
            }
        });
        speaker.setOnUtteranceCompletedListener(this);

    }
    public void speakFun(String text){
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH, ttsOptions);
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        if (utteranceId.equals(UTTER_ID)) {
            Log.d("textToSpeech", "completed");
            speaker.speak("Completed", TextToSpeech.QUEUE_FLUSH, ttsOptions);
           }
    }
}