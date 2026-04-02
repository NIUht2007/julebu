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
import com.jczy.cyclone.club.motocircle.adapter.PostAdapter
import com.jczy.cyclone.club.motocircle.viewmodel.MotoCircleViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostListFragment : BaseFragment() {

    private val viewModel: MotoCircleViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var adapter: PostAdapter

    override fun getLayoutId(): Int = R.layout.fragment_post_list

    override fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById(R.id.refresh_layout)

        adapter = PostAdapter()
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
                viewModel.postList.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }
}
