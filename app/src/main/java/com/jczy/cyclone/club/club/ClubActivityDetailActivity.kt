package com.jczy.cyclone.club.club

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.club.viewmodel.ClubViewModel
import com.jczy.cyclone.club.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubActivityDetailActivity : AppCompatActivity() {

    private val viewModel: ClubViewModel by viewModels()

    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var timeView: TextView
    private lateinit var locationView: TextView
    private lateinit var joinCountView: TextView
    private lateinit var joinButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_activity_detail)

        titleView = findViewById(R.id.title)
        descriptionView = findViewById(R.id.description)
        timeView = findViewById(R.id.time)
        locationView = findViewById(R.id.location)
        joinCountView = findViewById(R.id.join_count)
        joinButton = findViewById(R.id.join_button)

        val activityId = intent.getStringExtra("activityId") ?: ""

        // 加载活动详情
        viewModel.getClubActivityDetail(activityId)

        // 观察活动详情状态
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activityDetailState.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // 显示加载中
                        }
                        is UiState.Success -> {
                            val activity = state.data
                            titleView.text = activity.title
                            descriptionView.text = activity.description
                            timeView.text = "${activity.startTime} - ${activity.endTime}"
                            locationView.text = activity.location
                            joinCountView.text = "${activity.joinCount}人已报名"

                            if (activity.isJoined) {
                                joinButton.text = "已报名"
                                joinButton.isEnabled = false
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@ClubActivityDetailActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // 观察操作状态
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            if (state.data) {
                                joinButton.text = "已报名"
                                joinButton.isEnabled = false
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@ClubActivityDetailActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }

        joinButton.setOnClickListener {
            viewModel.joinClubActivity(activityId)
        }
    }
}