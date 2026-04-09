package com.jczy.cyclone.club.mall

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import com.jczy.cyclone.club.common.UiState
import com.jczy.cyclone.club.mall.adapter.GoodsAdapter
import com.jczy.cyclone.club.mall.viewmodel.MallViewModel
import com.jczy.cyclone.club.mmkv.AuthMMKV
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MallFragment : BaseFragment() {

    private val viewModel: MallViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var adapter: GoodsAdapter
    private var keyword: String = ""

    companion object {
        private const val TAG = "MallFragment"
    }

    override fun getLayoutId(): Int = R.layout.fragment_mall

    override fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById(R.id.refresh_layout)
        searchView = view.findViewById(R.id.search_view)

        adapter = GoodsAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            adapter.refresh()
            refreshLayout.finishRefresh()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                keyword = query ?: ""
                loadGoodsList()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun initData() {
        val token = AuthMMKV.openApiAccessToken
        Log.d(TAG, "商城加载: openApiAccessToken=${if (token.isNullOrBlank()) "空!" else "有值(长度=${token.length})"}")
        loadGoodsList()

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

    private fun loadGoodsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val flow = if (keyword.isNotEmpty()) {
                    viewModel.searchGoods(keyword)
                } else {
                    viewModel.getGoodsList()
                }
                flow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }
}
