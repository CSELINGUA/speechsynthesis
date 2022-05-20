package com.example.pdfreader.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader.*
import com.example.pdfreader.adapters.TTSViewHolder
import com.example.pdfreader.adapters.TextToSpeechAdapter
import com.example.pdfreader.utils.BackgroundSoundService
import com.example.pdfreader.utils.Striper
import kotlinx.android.synthetic.main.activity_text_to_speech.*

class TextToSpeechActivity : AppCompatActivity() {


    private lateinit var textToSpeechAdapter: TextToSpeechAdapter
    private lateinit var uri: Uri
    private var pagenumber : Int = 0
    private lateinit var striper: Striper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        val bundle: Bundle?=intent.extras
        uri = Uri.parse(bundle!!.getString("uri"))
        pagenumber = bundle!!.getInt("page_number")



        striper = Striper(uri, contentResolver, this)

        my_recycler_view_tts.apply{
            layoutManager = LinearLayoutManager(this@TextToSpeechActivity)
            textToSpeechAdapter = TextToSpeechAdapter()
            adapter = textToSpeechAdapter
        }

        addDataSet()

        TTSViewHolder.pos.observe(this,androidx.lifecycle.Observer {
            my_recycler_view_tts.post{
                my_recycler_view_tts.smoothScrollToPosition(it.toInt()) //.smoothScrollToPosition(TTSViewHolder.pos.value!!.toInt())//textToSpeechAdapter.itemCount-1)//TTSViewHolder.pos.value!!.toInt())//textToSpeechAdapter.itemCount-1)
            }

        })
        prev_button.setOnClickListener{
            getTextFromPageAndSubmit(3)
            val intent = Intent(applicationContext, BackgroundSoundService:: class.java)
            startService(intent)

        }
        play_pause_button.setOnClickListener {
//            textToSpeechAdapter.speak

        }

        next_button.setOnClickListener {
            getTextFromPageAndSubmit(1)


        }
    }
    private fun addDataSet(){
        val dataT =striper.stripText(pagenumber)
        val data = striper.separateText()
        textToSpeechAdapter.submitList(data)

//        textToSpeechAdapter.submitList(arrayListOf(TTSModel("hey, now im readint"),TTSModel("Im reading bodint")))
    }
    fun getTextFromPageAndSubmit(page:Int){
        striper.stripText(page)
        val data = striper.separateText()
        textToSpeechAdapter.submitList(data)

    }
}
