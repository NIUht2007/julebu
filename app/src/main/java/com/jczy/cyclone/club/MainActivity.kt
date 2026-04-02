package com.jczy.cyclone.club

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jczy.cyclone.club.club.ClubFragment
import com.jczy.cyclone.club.mall.MallFragment
import com.jczy.cyclone.club.message.MessageFragment
import com.jczy.cyclone.club.mine.MineFragment
import com.jczy.cyclone.club.motocircle.MotoCircleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val fragments = listOf<Fragment>(
        MotoCircleFragment(),
        ClubFragment(),
        MallFragment(),
        MessageFragment(),
        MineFragment()
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        // 初始化默认显示第一个 Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragments[0])
            .commit()
        
        // 设置底部导航栏监听
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_moto_circle -> showFragment(0)
                R.id.nav_club -> showFragment(1)
                R.id.nav_mall -> showFragment(2)
                R.id.nav_message -> showFragment(3)
                R.id.nav_mine -> showFragment(4)
                else -> false
            }
        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun showFragment(position: Int): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragments[position])
            .commit()
        return true
    }
}