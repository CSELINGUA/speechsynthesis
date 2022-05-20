package com.example.pdfreader.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.speech.tts.TextToSpeech
import com.example.pdfreader.activities.ViewerActivity
import java.util.*

class ReadInPageClass:TextToSpeech.OnUtteranceCompletedListener {


    private lateinit var striper: Striper
    private lateinit var ttsOptions: HashMap<String, String>
    private val UTTER_ID = "utterance"
    private lateinit var speaker: TextToSpeech
    private lateinit var splitSpeech: List<String>
    private var sentencesSpoken = -1
    private var readerPosition = 0
    private var totalSentenceInArray = 0

    private lateinit var data: String
    private lateinit var viewerActivity: ViewerActivity
//    private lateinit var simpleFloatingWindow: SimpleFloatingWindow

    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver
    private lateinit var simpleFloatingWindow: SimpleFloatingWindow

    constructor(context: Context, contentResolver: ContentResolver, simpleFloatingWindow: SimpleFloatingWindow) {
        this.context = context
        this.contentResolver = contentResolver
        this.simpleFloatingWindow = simpleFloatingWindow
        init(context)

    }

    constructor()


    fun extractAndRead(uri: Uri?, currentPage: Int) {
        striper = Striper(uri, contentResolver, context)
        data = striper.stripText(currentPage)
        //val separate_text_using_full_stop = striper.separateText()
        if (splitter(data))
            speak()


    }

    private fun init(context: Context) {
        speaker = TextToSpeech(context, TextToSpeech.OnInitListener {
            ttsOptions = HashMap()
            ttsOptions[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = UTTER_ID
            speaker.language = Locale.US
        })
        speaker.setOnUtteranceCompletedListener(this)
    }

    private fun splitter(text: String): Boolean {
        if (text != null) {
            splitSpeech = text.split("*")
            totalSentenceInArray = splitSpeech.size
            return true
        } else
            return false
    }


    private fun speak(boolean: Boolean = false) {
        if (speaker.isSpeaking)
            speaker.stop()
        for (i in readerPosition until splitSpeech.size) {
            if ((i == 0) || boolean) {
                speaker.speak(splitSpeech[i].trim(), TextToSpeech.QUEUE_FLUSH, ttsOptions)
                sentencesSpoken += 1
            }

            else {
                speaker.speak(splitSpeech[i].trim(), TextToSpeech.QUEUE_ADD, ttsOptions)
                sentencesSpoken +=1
            }
        }

    }

    override fun onUtteranceCompleted(p0: String?) {
//        Log.d("COMPLETION","reading completed")
        viewerActivity = ViewerActivity()
        viewerActivity.hideBrowseButtons()
        simpleFloatingWindow.dismiss()

    }

    fun pausePlayTTS(b: Boolean) {
        if (b)
            if (speaker.isSpeaking)
                speaker.stop()
        return
    }

    fun invalidateData() {
        speaker.stop()
        simpleFloatingWindow.dismiss()
    }

    fun goBackOne() {

        if (speaker.isSpeaking) {
            if (sentencesSpoken > 1 && totalSentenceInArray > 1) {
                readerPosition -= 1
                speak(true)

            }
        }
    }

    fun goForwardOne() {
        if (speaker.isSpeaking) {
            if (sentencesSpoken < totalSentenceInArray && totalSentenceInArray>1) {
                readerPosition +=1
                speak(true)

            }
        }
    }
}