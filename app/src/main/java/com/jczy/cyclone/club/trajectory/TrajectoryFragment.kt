package com.jczy.cyclone.club.trajectory

import android.os.Bundle
import android.view.View
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrajectoryFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_trajectory

    override fun initView(view: View) {
        // TODO: 初始化轨迹列表
    }

    override fun initData() {
        // TODO: 加载轨迹数据
    }
}
