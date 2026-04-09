package com.jczy.cyclone.club.network

import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.model.ClubActivity
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * 主业务 API（宗申后端，BASE_URL = https://iovapicyclone.zonsenmotor.com/）
 */
interface ClubApi {

    // ==================== 登录 ====================

    /**
     * 获取登录短信验证码
     * POST club-system/sms/sendLoginCode
     * Field: phone, operatorType(2=mobile)
     */
    @POST("club-system/sms/sendLoginCode")
    @FormUrlEncoded
    suspend fun getLoginSmsCode(
        @Field("phone") phone: String,
        @Field("operatorType") operatorType: Int = 2
    ): ApiResponse<Boolean>

    /**
     * 短信/密码登录
     * POST club-auth/login
     * Body: {"userName":"","password":"","grantType":"sms|password","operatorType":2,"code":""}
     */
    @POST("club-auth/login")
    suspend fun login(@Body body: RequestBody): ApiResponse<Map<String, Any>>

    /**
     * 获取当前用户信息
     * GET club-system/app/member/getMemberInfo
     */
    @GET("club-system/app/member/getMemberInfo")
    suspend fun getUserInfo(): ApiResponse<Map<String, Any>>

    // ==================== 俱乐部 ====================

    /** 获取帖子（俱乐部动态）列表，分页 */
    @GET("club-moto/app/postsInfo")
    suspend fun getClubList(
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Club>>

    /** 根据帖子ID获取俱乐部详情 */
    @GET("club-moto/app/postsInfo/getByPostsId")
    suspend fun getClubDetail(@Query("id") id: String): ApiResponse<Club>

    /** 发布帖子（创建俱乐部内容） */
    @POST("club-moto/app/postsInfo/savePosts")
    suspend fun createClub(@Body body: RequestBody): ApiResponse<Boolean>

    /** 编辑帖子 */
    @POST("club-moto/app/postsInfo/savePosts")
    suspend fun editClub(@Body body: RequestBody): ApiResponse<Boolean>

    /** 获取俱乐部活动列表（售后/活动分页） */
    @GET("club-moto/app/postsInfo/palePage")
    suspend fun getClubActivityList(
        @Query("clubId") clubId: String,
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<ClubActivity>>

    /** 获取俱乐部活动详情 */
    @GET("club-moto/app/postsInfo/getByPostsId")
    suspend fun getClubActivityDetail(@Query("id") id: String): ApiResponse<ClubActivity>

    /** 创建俱乐部活动 */
    @POST("club-moto/app/postsInfo/savePosts")
    suspend fun createClubActivity(@Body body: RequestBody): ApiResponse<Boolean>

    /** 报名俱乐部活动（点赞/关注接口复用） */
    @POST("club-moto/app/postsInfo/giveLike")
    suspend fun joinClubActivity(@Body body: RequestBody): ApiResponse<Boolean>

    /** 获取俱乐部成员列表（里程排行） */
    @GET("club-moto/app/rank")
    suspend fun getClubMemberList(
        @Query("clubId") clubId: String,
        @Query("pageNum") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Any>>

    /** 审核俱乐部成员 */
    @POST("club-moto/app/postsInfo/saveComment")
    suspend fun auditClubMember(@Body body: RequestBody): ApiResponse<Boolean>

    /** 俱乐部评分（点赞复用） */
    @POST("club-moto/app/postsInfo/giveLike")
    suspend fun scoreClub(@Body body: RequestBody): ApiResponse<Boolean>
}
