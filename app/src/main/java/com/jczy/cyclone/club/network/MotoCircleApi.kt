package com.jczy.cyclone.club.network

import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.model.Guide
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 玩车圈 API（宗申后端，BASE_URL = https://iovapicyclone.zonsenmotor.com/）
 * 接口路径对应宗申项目 API.kt 中 club-moto/app/ 下的接口
 */
interface MotoCircleApi {

    // ==================== 动态（帖子）====================

    /** 获取动态列表（分页） */
    @GET("club-moto/app/postsInfo")
    suspend fun getPostList(
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Post>>

    /** 获取动态详情 */
    @GET("club-moto/app/postsInfo/getByPostsId")
    suspend fun getPostDetail(@Query("id") id: String): ApiResponse<Post>

    /** 点赞/取消点赞动态 */
    @POST("club-moto/app/postsInfo/giveLike")
    suspend fun likePost(@Body body: RequestBody): ApiResponse<Boolean>

    /** 发布动态评论 */
    @POST("club-moto/app/postsInfo/saveComment")
    suspend fun commentPost(@Body body: RequestBody): ApiResponse<Boolean>

    /** 分享动态 */
    @POST("club-moto/app/postsInfo/giveShare")
    suspend fun sharePost(@Body body: RequestBody): ApiResponse<Boolean>

    // ==================== 文章 ====================

    /** 获取文章列表（分页） */
    @GET("club-moto/app/articleInfo")
    suspend fun getArticleList(
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Article>>

    /** 获取文章详情 */
    @GET("club-moto/app/articleInfo/getByArticleId")
    suspend fun getArticleDetail(@Query("id") id: String): ApiResponse<Article>

    // ==================== 攻略（指南）====================

    /** 获取攻略列表（分页） */
    @GET("club-moto/app/guideInfo")
    suspend fun getGuideList(
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Guide>>

    /** 获取攻略详情 */
    @GET("club-moto/app/guideInfo/getByGuideId")
    suspend fun getGuideDetail(@Query("id") id: String): ApiResponse<Guide>
}
