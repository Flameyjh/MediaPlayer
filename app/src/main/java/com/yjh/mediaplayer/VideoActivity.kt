package com.yjh.mediaplayer

/*
* MediaPlayer视频播放
* */

import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import java.io.File
import java.io.IOException

class VideoActivity : AppCompatActivity(), View.OnClickListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {
    lateinit var textureView: TextureView
    lateinit var btn_opt: Button
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        textureView = findViewById(R.id.textureView)
        btn_opt = findViewById(R.id.btn_opt)
        btn_opt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (TextUtils.equals(btn_opt.text, "开始播放")) {
            btn_opt.text = "结束播放"
            mediaPlayer = MediaPlayer().apply {
                //设置准备监听
                setOnPreparedListener(this@VideoActivity)
                setOnCompletionListener(this@VideoActivity)
            }
            try {
                //指定视频源
                mediaPlayer.setDataSource(File(getExternalFilesDir(""), "a.mp4").absolutePath)
            }catch (e: IOException){
                e.printStackTrace()
            }
            mediaPlayer.apply {
                setSurface(Surface(textureView.surfaceTexture))
                prepareAsync()
            }

        }else {
            btn_opt.text = "开始播放"
            mediaPlayer.apply {
                stop()
                release()
            }
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        btn_opt.text = "开始播放"
        mediaPlayer.release()
    }
}


