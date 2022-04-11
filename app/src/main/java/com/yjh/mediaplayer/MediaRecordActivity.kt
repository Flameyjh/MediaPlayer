package com.yjh.mediaplayer

import android.hardware.Camera
import android.media.MediaRecorder
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class MediaRecordActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var textureView: TextureView
    lateinit var btn_opt: Button
    lateinit var mediaRecorder: MediaRecorder
    lateinit var camera: Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        textureView = findViewById(R.id.textureView)
        btn_opt = findViewById(R.id.btn_opt)
        btn_opt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (TextUtils.equals(btn_opt.text, "开始录制")){
            btn_opt.text = "结束录制"
            camera =  Camera.open().apply {
                setDisplayOrientation(90) //设置录制预览画面角度（不设置录制画面就是颠倒的）
                unlock()
            }
            mediaRecorder = MediaRecorder().apply {
                setCamera(camera)
                setAudioSource(MediaRecorder.AudioSource.MIC) //设置音频源 麦克风
                setVideoSource(MediaRecorder.VideoSource.CAMERA) //设置视频源 摄像头
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) //指定视频文件格式
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                setOrientationHint(90) //设置文件画面角度（不设置文件就是颠倒的）
                setOutputFile(File(getExternalFilesDir(""), "a.mp4").absolutePath) //设置视频输出文件(安卓私有目录：sdcard/android/data/包名)
                setVideoSize(640, 480)
                setPreviewDisplay(Surface(textureView.surfaceTexture))
            }
            try {
                mediaRecorder.prepare() //准备阶段
            }catch (e: IOException){
                e.printStackTrace()
            }
            mediaRecorder.start() //录制阶段

        }else{
            btn_opt.text = "开始录制"
            mediaRecorder.apply {
                stop() //结束阶段
                release()
            }
            camera.apply {
                stopPreview()
                release()
            }
        }

    }
}