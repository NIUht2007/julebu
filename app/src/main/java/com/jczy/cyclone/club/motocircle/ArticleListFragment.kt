package com.jczy.cyclone.club.motocircle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import com.jczy.cyclone.club.motocircle.adapter.ArticleAdapter
import com.jczy.cyclone.club.motocircle.viewmodel.MotoCircleViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleListFragment : BaseFragment() {

    private val viewModel: MotoCircleViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var adapter: ArticleAdapter

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById(R.id.refresh_layout)

        adapter = ArticleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            adapter.refresh()
            refreshLayout.finishRefresh()
        }
    }

    override fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articleList.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }
}
