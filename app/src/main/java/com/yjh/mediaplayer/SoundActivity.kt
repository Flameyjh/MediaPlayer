package com.yjh.mediaplayer

/*
* SoundPool音效播放
* */

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SoundActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {

    data class Sound(val name: String, val id: Int) //数据列表中的数据
    val soundPool = SoundPool.Builder().setMaxStreams(6).build()
    val dataList = ArrayList<Sound>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        //创建数据List
        dataList.apply {
            add(Sound("a1", soundPool.load(this@SoundActivity, R.raw.a1, 1))) //soundPool把sound load进去后用id去调用它
            add(Sound("a2", soundPool.load(this@SoundActivity, R.raw.a2, 1)))
            add(Sound("a3", soundPool.load(this@SoundActivity, R.raw.a3, 1)))
            add(Sound("a4", soundPool.load(this@SoundActivity, R.raw.a4, 1)))
            add(Sound("a5", soundPool.load(this@SoundActivity, R.raw.a5, 1)))
            add(Sound("a6", soundPool.load(this@SoundActivity, R.raw.a6, 1)))
        }

        //创建显示布局
        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(dataList, recyclerView)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val sound = dataList[position]
        soundPool.play(sound.id, 0.5f, 0.5f, 1, 0, 1.0f)
    }

    //释放声音
    override fun onDestroy() {
        super.onDestroy()
        for (sound: Sound in dataList){
            soundPool.unload(sound.id)
        }
        soundPool.release()
    }
}


class MyAdapter(private val dataList: List<SoundActivity.Sound>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(), View.OnClickListener {

    lateinit var listener: OnItemClickListener

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val holder = MyViewHolder(view)
        //方法一：直接在holder内部实现点击事件
//        holder.itemView.setOnClickListener{
//            val sound = dataList[holder.adapterPosition]
//            soundPool.play(sound.id, 0.5f, 0.5f, 1, 0, 1.0f)
//        }
        holder.itemView.setOnClickListener(this)
        return holder
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.textView.text = " " + dataList[position].name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //方法二：在外部的activity实现点击事件
    override fun onClick(v: View?) {
        v?.let { recyclerView.getChildAdapterPosition(it) }?.let { listener.onItemClick(it) }
    }

    //给外界提供接口
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
