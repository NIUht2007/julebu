package com.jczy.cyclone.club.network

import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.model.Guide
import retrofit2.http.GET
import retrofit2.http.Query

interface MotoCircleApi {
    // 获取动态列表
    @GET("api/v1/post/list")
    suspend fun getPostList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Post>>
    
    // 获取文章列表
    @GET("api/v1/article/list")
    suspend fun getArticleList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Article>>
    
    // 获取攻略列表
    @GET("api/v1/guide/list")
    suspend fun getGuideList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Guide>>
    
    // 获取动态详情
    @GET("api/v1/post/detail")
    suspend fun getPostDetail(@Query("id") id: String): ApiResponse<Post>
    
    // 获取文章详情
    @GET("api/v1/article/detail")
    suspend fun getArticleDetail(@Query("id") id: String): ApiResponse<Article>
    
    // 获取攻略详情
    @GET("api/v1/guide/detail")
    suspend fun getGuideDetail(@Query("id") id: String): ApiResponse<Guide>
    
    // 点赞
    @GET("api/v1/post/like")
    suspend fun likePost(@Query("id") id: String): ApiResponse<Boolean>
    
    // 评论
    @GET("api/v1/post/comment")
    suspend fun commentPost(
        @Query("id") id: String,
        @Query("content") content: String
    ): ApiResponse<Boolean>
    
    // 转发
    @GET("api/v1/post/share")
    suspend fun sharePost(@Query("id") id: String): ApiResponse<Boolean>
}