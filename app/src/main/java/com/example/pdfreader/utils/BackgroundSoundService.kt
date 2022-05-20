package com.example.pdfreader.utils

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundSoundService: Service(){
    internal lateinit var player:MediaPlayer
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate(){
        super.onCreate()
        val afd = applicationContext.assets.openFd("sound1.mp3") as AssetFileDescriptor
        val player = MediaPlayer()
        player.setDataSource(afd.fileDescriptor)
        player.isLooping=true
        player.setVolume(100f,100f)

    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.start()
        return 1
    }

    override fun onStart(intent: Intent?, startId: Int) {
        //super.onStart(intent, startId)
    }
    fun onUnBind(arg0: Intent): IBinder?{
        return null
    }
    fun onStop(){

    }
    fun onPause(){

    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }

    override fun onLowMemory() {

    }
    companion object{
        private val TAG:String? = null
    }

}