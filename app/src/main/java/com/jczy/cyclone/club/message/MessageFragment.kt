package com.jczy.cyclone.club.message

import android.os.Bundle
import android.view.View
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_message

    override fun initView(view: View) {
        // TODO: 初始化网易云信会话列表UI
    }

    override fun initData() {
        // TODO: 加载消息列表
    }
}
