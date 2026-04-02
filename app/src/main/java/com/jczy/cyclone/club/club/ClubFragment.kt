package com.jczy.cyclone.club.club

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.club.adapter.ClubAdapter
import com.jczy.cyclone.club.club.viewmodel.ClubViewModel
import com.jczy.cyclone.club.common.BaseFragment
import com.jczy.cyclone.club.common.UiState
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubFragment : BaseFragment() {

    private val viewModel: ClubViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var adapter: ClubAdapter

    override fun getLayoutId(): Int = R.layout.fragment_club

    override fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById(R.id.refresh_layout)

        adapter = ClubAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            adapter.refresh()
            refreshLayout.finishRefresh()
        }
    }

    override fun initData() {
        // 收集分页数据
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clubList.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        // 收集操作状态
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            if (state.data) {
                                Toast.makeText(requireContext(), "操作成功", Toast.LENGTH_SHORT).show()
                                viewModel.resetActionState()
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
