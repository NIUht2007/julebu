package com.jczy.cyclone.club.network

import com.jczy.cyclone.club.network.ApiResponse
import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.model.ClubActivity
import retrofit2.http.GET
import retrofit2.http.Query

interface ClubApi {
    // 获取俱乐部列表
    @GET("api/v1/club/list")
    suspend fun getClubList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Club>>
    
    // 获取俱乐部详情
    @GET("api/v1/club/detail")
    suspend fun getClubDetail(@Query("id") id: String): ApiResponse<Club>
    
    // 创建俱乐部
    @GET("api/v1/club/create")
    suspend fun createClub(
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("logo") logo: String
    ): ApiResponse<Boolean>
    
    // 编辑俱乐部
    @GET("api/v1/club/edit")
    suspend fun editClub(
        @Query("id") id: String,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("logo") logo: String
    ): ApiResponse<Boolean>
    
    // 获取俱乐部活动列表
    @GET("api/v1/club/activity/list")
    suspend fun getClubActivityList(
        @Query("clubId") clubId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<ClubActivity>>
    
    // 获取俱乐部活动详情
    @GET("api/v1/club/activity/detail")
    suspend fun getClubActivityDetail(@Query("id") id: String): ApiResponse<ClubActivity>
    
    // 创建俱乐部活动
    @GET("api/v1/club/activity/create")
    suspend fun createClubActivity(
        @Query("clubId") clubId: String,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("startTime") startTime: Long,
        @Query("endTime") endTime: Long,
        @Query("location") location: String
    ): ApiResponse<Boolean>
    
    // 报名俱乐部活动
    @GET("api/v1/club/activity/join")
    suspend fun joinClubActivity(@Query("id") id: String): ApiResponse<Boolean>
    
    // 获取俱乐部成员列表
    @GET("api/v1/club/member/list")
    suspend fun getClubMemberList(
        @Query("clubId") clubId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Any>>
    
    // 审核俱乐部成员
    @GET("api/v1/club/member/audit")
    suspend fun auditClubMember(
        @Query("id") id: String,
        @Query("status") status: Int
    ): ApiResponse<Boolean>
    
    // 俱乐部评分
    @GET("api/v1/club/score")
    suspend fun scoreClub(
        @Query("id") id: String,
        @Query("score") score: Float
    ): ApiResponse<Boolean>
}