package com.jczy.cyclone.club.im

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jczy.cyclone.club.R

class MyChatP2PActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_p2p)

        val account = intent.getStringExtra("account") ?: ""
        val nickname = intent.getStringExtra("nickname") ?: ""
        title = nickname
        // TODO: 集成网易云信聊天UI
    }
}
