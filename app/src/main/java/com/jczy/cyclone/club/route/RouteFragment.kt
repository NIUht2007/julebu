package com.jczy.cyclone.club.route

import android.os.Bundle
import android.view.View
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_route

    override fun initView(view: View) {
        // TODO: 初始化路线地图
    }

    override fun initData() {
        // TODO: 加载路线数据
    }
}
