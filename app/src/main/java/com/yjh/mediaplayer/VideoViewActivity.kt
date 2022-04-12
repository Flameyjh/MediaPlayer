package com.yjh.mediaplayer

/*
* VideoView视频播放
* 相当于封装了MediaPlayer，提供了进度条、暂停、继续、上一条、下一条等功能
* 但是这些UI不能自己改，如需自定义还是要用MediaPlayer
* */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import java.io.File

class VideoViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        val mediaController = MediaController(this)
        mediaController.setPrevNextListeners(this, this)

        val videoView: VideoView = findViewById(R.id.videoView)
        videoView.apply {
            setMediaController(mediaController)
            setVideoPath(File(getExternalFilesDir(""), "a.mp4").absolutePath)
            start()
        }
    }

    override fun onClick(p0: View?) {
        Log.i("Video", "====")
    }
}