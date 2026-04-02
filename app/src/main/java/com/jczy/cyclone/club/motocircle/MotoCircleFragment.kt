package com.jczy.cyclone.club.motocircle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import com.jczy.cyclone.club.motocircle.adapter.MotoCirclePagerAdapter
import com.jczy.cyclone.club.motocircle.viewmodel.MotoCircleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotoCircleFragment : BaseFragment() {

    private val viewModel: MotoCircleViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun getLayoutId(): Int = R.layout.fragment_moto_circle

    override fun initView(view: View) {
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)

        val adapter = MotoCirclePagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "动态"
                1 -> "文章"
                2 -> "攻略"
                else -> ""
            }
        }.attach()
    }

    override fun initData() {
        // 数据由子 Fragment 加载
    }
}
