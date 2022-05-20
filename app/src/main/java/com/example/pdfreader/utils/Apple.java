package com.example.pdfreader.utils;

import android.app.Activity;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;

import static androidx.constraintlayout.widget.StateSet.TAG;

class Apple extends Activity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener{
private TextToSpeech mTts;

private void speak(String text) {
        if(text != null) {

        HashMap<String, String> myHashAlarm = new HashMap<String, String>();

        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");

        mTts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
        }
// Fired after TTS initialization
public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
        mTts.setOnUtteranceCompletedListener(this);
        }
        }
// It's callback
public void onUtteranceCompleted(String utteranceId) {
        Log.i(TAG, utteranceId); //utteranceId == "SOME MESSAGE"
        }

        }