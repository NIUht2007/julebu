package com.jczy.cyclone.club.motocircle.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jczy.cyclone.club.motocircle.PostListFragment
import com.jczy.cyclone.club.motocircle.ArticleListFragment
import com.jczy.cyclone.club.motocircle.GuideListFragment

class MotoCirclePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostListFragment()
            1 -> ArticleListFragment()
            2 -> GuideListFragment()
            else -> PostListFragment()
        }
    }
}
