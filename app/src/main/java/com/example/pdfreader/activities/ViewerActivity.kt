package com.example.pdfreader.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.pdfreader.*
import com.example.pdfreader.utils.*

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.android.synthetic.main.activity_reader_screen.*

class ViewerActivity : AppCompatActivity(), OnPageChangeListener {

    override fun onPageChanged(page: Int, pageCount: Int) {

    }

    private lateinit var striper: Striper
    private var textExtracted: String? = null
    private var uri:Uri? = null
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var arr:ArrayList<Int>
    private var isRotate = false
    private lateinit var simpleFloatingWindow: SimpleFloatingWindow
    private lateinit var readInPageClass: ReadInPageClass
    private lateinit var getImageLocationsAndSize: GetImageLocationsAndSize
    private var currentPage:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader_screen)
        sharedPreferences = this.getSharedPreferences("PDFReader", Context.MODE_PRIVATE)
        val bundle: Bundle?=intent?.extras
        uri = Uri.parse(bundle!!.getString("uri"))


        striper = Striper(uri, contentResolver, this)

        simpleFloatingWindow = SimpleFloatingWindow(applicationContext)

        readInPageClass = ReadInPageClass(this, contentResolver, simpleFloatingWindow)

        getImageLocationsAndSize = GetImageLocationsAndSize(uri, contentResolver, this)



//        init(readInPage)
//        init(readOutPage)
//
//        readOutPage.setOnClickListener {
//            val intent = Intent(it.context, TextToSpeechActivity::class.java)
//            intent.putExtra("uri", uri.toString())
//            intent.putExtra("page_number",(currentPage+1))
//            startActivity(intent)
//        }
//        fab.setOnClickListener{
//                isRotate = rotateFab(it, !isRotate)
//                if (isRotate) {
//                    showIn(readInPage)
//                    showIn(readOutPage)
//                } else {
//                    showOut(readInPage)
//                    showOut(readOutPage)
//                }
//        }



        MyPDFView.fromUri(uri)

            .enableSwipe(true)
            .swipeHorizontal(true)
            .pageSnap(true)
            .autoSpacing(true)
            .pageFling(true)
            .enableDoubletap(true)
            .defaultPage(sharedPreferences.getInt ("pages$uri",0))
            .enableAnnotationRendering(false)
            .password(null)
            .onLoad {
                currentPage = it
                but_page_count.text = it.toString()
                arr = ArrayList()
                for(i in 1..it)
                    arr.add(i)
                //val spinner = pages_spinner

                val adapterPages = ArrayAdapter(this, android.R.layout.simple_spinner_item, arr)
                adapterPages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                pages_spinner.adapter = adapterPages
                pages_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        MyPDFView.jumpTo(position)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
//
            }
            .onTap {
//                Toast.makeText(this,"xy clicked",Toast.LENGTH_SHORT).show()
                when (it.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = it.rawX.toInt()
                        lastY = it.rawY.toInt()
                        readEmbeddedText(lastX,lastY)
                        firstX = lastX
                        firstY = lastY
                        when {

                            lastX<199 -> Toast.makeText(this,"Left clicked",Toast.LENGTH_SHORT).show()
                            lastX>250 -> Toast.makeText(this,"Right clicked",Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this,"all clicked",Toast.LENGTH_SHORT).show()
                        }
                        getImageLocationsAndSize.getImages()
                    }
                    else -> {
                    }
                }
                true
            }

            .scrollHandle(null)
            .enableAntialiasing(true)
//            .spacing(0)
            .pageFitPolicy(FitPolicy.WIDTH)
            .onPageChange { page, pageCount ->
                currentPage = page
                pages_spinner.setSelection(page)
                val edit : SharedPreferences.Editor = sharedPreferences.edit()
                edit.putInt("pages$uri",page)
                edit.commit()
            }
            .onDraw { canvas, pageWidth, pageHeight, displayedPage ->
                var p = Paint()
                p.color = Color.BLUE
                canvas.drawRect(10F,10F,60F,60F,p)
            }
            .load()


        readInPage.setOnClickListener {
            if (canDrawOverlays) {
                //Toast.makeText(this,(currentPage+1).toString(),Toast.LENGTH_SHORT).show()
                readInPageClass.extractAndRead(uri,(currentPage+1))

                back_paragraph.visibility = View.VISIBLE
                forward_paragraph.visibility = View.VISIBLE

                simpleFloatingWindow.dismiss()
                simpleFloatingWindow.show()
            } else {
                startManageDrawOverlaysPermission()
            }
        }

        back_paragraph.setOnClickListener {
            Toast.makeText(it.context,"Back clicked",Toast.LENGTH_SHORT).show()
            readInPageClass.goBackOne()
        }

        forward_paragraph.setOnClickListener {
            Toast.makeText(it.context,"Forward clicked",Toast.LENGTH_SHORT).show()
            readInPageClass.goForwardOne()
        }
    }


    private lateinit var layoutParams: WindowManager.LayoutParams
    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0

    private var isShowing = false
    private var touchConsumedByMove = false


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_DRAW_OVERLAY_PERMISSION -> {
                if (canDrawOverlays) {
                    simpleFloatingWindow.show()
                } else {
                    showToast("Permission is not granted!")
                }
            }
        }
    }

    private fun startManageDrawOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${applicationContext.packageName}")
            ).let {
                startActivityForResult(it,
                    REQUEST_CODE_DRAW_OVERLAY_PERMISSION
                )
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_DRAW_OVERLAY_PERMISSION = 5
    }

    fun hideBrowseButtons(){
        back_paragraph.visibility = View.GONE
        forward_paragraph.visibility = View.GONE
    }

    fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }
    fun showIn(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationY = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun showOut(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 1f
        v.translationY = 0f
        v.animate()
            .setDuration(200)
            .translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()

    }

    fun init(v: View) {
        v.visibility = View.GONE
        v.translationY = v.height.toFloat()
        v.alpha = 0f
    }
    fun readEmbeddedText(x:Int,y:Int){


    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBackPressed() {

        super.onBackPressed()
        readInPageClass.invalidateData()
        Runtime.getRuntime().gc()
        finish()

    }


    override fun onDestroy() {
        super.onDestroy()
        readInPageClass.invalidateData()
        Runtime.getRuntime().gc()
        finish()
    }


    /*override fun onClick(view: View?) {
        when (view) {
            back_paragraph -> {
                Toast.makeText(view!!.context,"Back clicked",Toast.LENGTH_SHORT).show()
                readInPageClass.goBackOne()
            }
            forward_paragraph -> {
                Toast.makeText(view!!.context,"Forward clicked",Toast.LENGTH_SHORT).show()
                readInPageClass.goForwardOne()

            }
        }
    }*/


}
