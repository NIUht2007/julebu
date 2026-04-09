package com.jczy.cyclone.club

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jczy.cyclone.club.club.ClubFragment
import com.jczy.cyclone.club.login.LoginActivity
import com.jczy.cyclone.club.mall.MallFragment
import com.jczy.cyclone.club.message.MessageFragment
import com.jczy.cyclone.club.mine.MineFragment
import com.jczy.cyclone.club.mmkv.AuthMMKV
import com.jczy.cyclone.club.motocircle.MotoCircleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Fragment tag 列表，与底部导航顺序对应
    private val fragmentTags = listOf(
        "tag_moto_circle",
        "tag_club",
        "tag_mall",
        "tag_message",
        "tag_mine"
    )

    // Fragment 工厂，只在 BackStack 里找不到时才 new 新实例
    private val fragmentFactories: List<() -> Fragment> = listOf(
        { MotoCircleFragment() },
        { ClubFragment() },
        { MallFragment() },
        { MessageFragment() },
        { MineFragment() }
    )

    private var currentTag: String = fragmentTags[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检查登录状态，未登录跳转到登录页
        if (!AuthMMKV.isLogin || AuthMMKV.token.isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            // 首次启动：只添加第一个 Fragment
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentFactories[0](), fragmentTags[0])
                .commit()
            currentTag = fragmentTags[0]
        } else {
            // Activity 重建：恢复上次选中的 tag
            currentTag = savedInstanceState.getString(KEY_CURRENT_TAG, fragmentTags[0])!!
        }

        // 设置底部导航栏监听
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_moto_circle -> showFragment(0)
                R.id.nav_club        -> showFragment(1)
                R.id.nav_mall        -> showFragment(2)
                R.id.nav_message     -> showFragment(3)
                R.id.nav_mine        -> showFragment(4)
                else                 -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_TAG, currentTag)
    }

    /**
     * show/hide 模式取代 replace：
     * - Fragment 实例复用，ViewPager2 子 Fragment 的 SavedState 保持一致
     * - 避免 Activity 重建后出现 "Fragment no longer exists for key f#0" 崩溃
     */
    private fun showFragment(position: Int): Boolean {
        val tag = fragmentTags[position]
        if (tag == currentTag) return true

        val fm = supportFragmentManager
        val tx = fm.beginTransaction()

        // 找到当前显示的 Fragment 并隐藏
        fm.findFragmentByTag(currentTag)?.let { tx.hide(it) }

        // 找到目标 Fragment；不存在时才创建并添加
        val target = fm.findFragmentByTag(tag)
            ?: fragmentFactories[position]().also { tx.add(R.id.fragment_container, it, tag) }
        tx.show(target)

        tx.commit()
        currentTag = tag
        return true
    }

    companion object {
        private const val KEY_CURRENT_TAG = "current_fragment_tag"
    }
}