package com.example.pdfreader.adapters

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.R
import com.example.pdfreader.utils.ReadAloud
import com.example.pdfreader.utils.Striper
import com.example.pdfreader.adapters.TextToSpeechAdapter.Companion.items
import com.example.pdfreader.models.TTSModel
import kotlinx.android.synthetic.main.tts_partition_holder.view.*
import java.util.*

class TextToSpeechAdapter: RecyclerView.Adapter<TTSViewHolder>(){



companion object {
    lateinit var items: ArrayList<TTSModel>
}

    fun submitList(pdfList: ArrayList<TTSModel>){
        //items.clear()
        items = pdfList
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: TTSViewHolder, position: Int) {
        when(holder){
            is TTSViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TTSViewHolder {
        /*lateinit var ttsViewHolder: TTSViewHolder
        ttsViewHolder = TTSViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tts_partition_holder,parent,false))
        return ttsViewHolder*/
        return TTSViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tts_partition_holder,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class TTSViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), TextToSpeech.OnUtteranceCompletedListener {

    var sentence = itemView.sentence_string!!

    private val UTTER_ID = "utterance"
    private lateinit var speaker: TextToSpeech
    private lateinit var ttsOptions: java.util.HashMap<String, String>
    private lateinit var striper: Striper

    private lateinit var readAloud: ReadAloud

    companion object{
        val pos = MutableLiveData<Int>()
    }

    private fun init(context: Context) {

        speaker = TextToSpeech(context, TextToSpeech.OnInitListener {
            ttsOptions = java.util.HashMap<String, String>()
            ttsOptions[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = UTTER_ID

            speaker.language = Locale.US
        })

        speaker.setOnUtteranceCompletedListener(this)

    }
    init {
        //readAloud.init(itemView.context)
        pos.value = 0
        init(itemView.context)

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun bind(item: TTSModel) {
        //Toast.makeText(itemView.context, pos.value.toString(), Toast.LENGTH_SHORT).show()
        sentence.text = item.sentence

        itemView.setOnClickListener {
            pos.value = adapterPosition
            var position: Int = adapterPosition

            Toast.makeText(
                itemView.context,
                items[position].sentence.toString(),
                Toast.LENGTH_SHORT
            ).show()
            speak(items[pos.value!!].sentence.toString(),true)
        }
    }


    fun speak( text :String,isHome:Boolean) {
        if(isHome){
            speaker.speak(text, TextToSpeech.QUEUE_FLUSH, ttsOptions)
        }
        else{
            speaker.speak(items[0].sentence.toString(), TextToSpeech.QUEUE_FLUSH, ttsOptions)
        }

    }

override fun onUtteranceCompleted(p0: String?) {

        incrementAndSpeak()

        //(pos.value) === pos.value?.plus(1)
        /*speaker.speak(//"My name,, is Jonathan",
            items[pos.value!!].sentence.toString(),
            TextToSpeech.QUEUE_FLUSH,ttsOptions)*/
}


    private fun incrementAndSpeak(){
        (pos.value) = pos.value?.plus(1)
        Log.d("sizeOf", pos.value.toString())
        //pos = pos.value!!.plus(1)

    }
}