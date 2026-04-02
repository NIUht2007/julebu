package com.jczy.cyclone.club.motocircle.repository

import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.model.Guide
import com.jczy.cyclone.club.network.MotoCircleApi
import com.jczy.cyclone.club.network.RetrofitClient
import javax.inject.Inject

class MotoCircleRepository @Inject constructor() {
    private val api = RetrofitClient.createService<MotoCircleApi>()
    
    suspend fun getPostList(page: Int, pageSize: Int) = api.getPostList(page, pageSize)
    suspend fun getArticleList(page: Int, pageSize: Int) = api.getArticleList(page, pageSize)
    suspend fun getGuideList(page: Int, pageSize: Int) = api.getGuideList(page, pageSize)
    suspend fun getPostDetail(id: String) = api.getPostDetail(id)
    suspend fun getArticleDetail(id: String) = api.getArticleDetail(id)
    suspend fun getGuideDetail(id: String) = api.getGuideDetail(id)
    suspend fun likePost(id: String) = api.likePost(id)
    suspend fun commentPost(id: String, content: String) = api.commentPost(id, content)
    suspend fun sharePost(id: String) = api.sharePost(id)
}