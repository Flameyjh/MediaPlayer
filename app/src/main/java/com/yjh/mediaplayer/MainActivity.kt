package com.yjh.mediaplayer

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //申请权限
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
            100
        )
    }

    fun record(view: View) {
        startActivity(Intent(this, MediaRecordActivity::class.java))
    }
    fun playVideo(view: View) {
        startActivity(Intent(this, VideoActivity::class.java))
    }
    fun playVideoView(view: View) {
        startActivity(Intent(this, VideoViewActivity::class.java))
    }
    fun playAudio(view: View) {
        startActivity(Intent(this, SoundActivity::class.java))
    }
}