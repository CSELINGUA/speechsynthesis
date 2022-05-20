package com.example.pdfreader.fragments
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.R
import com.example.pdfreader.activities.MainActivity
import com.example.pdfreader.adapters.Adapter
import com.example.pdfreader.adapters.onPDFItemClickListener
import com.example.pdfreader.models.Model1
import kotlinx.android.synthetic.main.home1_fragment.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.pdfreader.utils.GetPDFS as GetPDFS1

class Home1 : Fragment(), onPDFItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var arrayList: ArrayList<Model1>? = null
    private lateinit var adapters : Adapter
    private lateinit var getPDFS: GetPDFS1
    private lateinit var viewModel: MainActivity

    //speech to text
    val RecordAudioRequestCode: Int? = 1
    private lateinit var speechRecognizer: SpeechRecognizer

    private lateinit var micButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home1_fragment, container, false)

}

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var contentRes: ContentResolver = requireActivity().contentResolver
        getPDFS = com.example.pdfreader.utils.GetPDFS(contentRes)
        my_recycler_view_home.apply{
            layoutManager = LinearLayoutManager(activity)
            adapters = Adapter()
            adapter = adapters
        }
        addDataSet()
        transcribed_text.setIconifiedByDefault(false)

        //Speech to text
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }


        micButton = transcribe_button
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {
                //transcribed_text.setOnQueryTextListener()
                transcribed_text!!.setQuery("",false)
                transcribed_text.clearFocus()
                transcribed_text!!.queryHint="Listening..."
            }
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                micButton!!.setImageResource(R.drawable.ic_mic_black_off)
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                transcribed_text!!.setQuery(data!![0],true)
                transcribed_text.clearFocus()
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })

        micButton!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening()
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                micButton!!.setImageResource(R.drawable.ic_mic_black_24dp)

                speechRecognizer.startListening(speechRecognizerIntent)
            }
            false
        }
        transcribed_text.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapters.filter.filter(newText)
                return false
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer!!.destroy()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                RecordAudioRequestCode!!
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                requireContext(),
                "Permission Granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addDataSet(){
        val intent = Intent(activity,GetPDFS1::class.java)
        activity?.startService(intent)
        val data = getPDFS?.loadData()
        adapters.submitList(data)
    }
override fun onItemClick(items: Model1, position: Int) {

}
}


