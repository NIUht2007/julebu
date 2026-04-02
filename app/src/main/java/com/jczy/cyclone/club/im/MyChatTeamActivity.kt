package com.jczy.cyclone.club.im

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jczy.cyclone.club.R

class MyChatTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_team)

        val teamId = intent.getStringExtra("teamId") ?: ""
        val teamName = intent.getStringExtra("teamName") ?: ""
        title = teamName
        // TODO: 集成网易云信群聊UI
    }
}
