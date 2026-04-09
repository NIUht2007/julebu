package com.jczy.cyclone.club.motocircle.repository

import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.model.Guide
import com.jczy.cyclone.club.network.MotoCircleApi
import com.jczy.cyclone.club.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class MotoCircleRepository @Inject constructor() {
    private val api = RetrofitClient.createService<MotoCircleApi>()

    private fun jsonBody(vararg pairs: Pair<String, Any?>): okhttp3.RequestBody {
        val json = JSONObject()
        pairs.forEach { (k, v) -> if (v != null) json.put(k, v) }
        return json.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }

    // 动态
    suspend fun getPostList(page: Int, pageSize: Int) = api.getPostList(page, pageSize)
    suspend fun getPostDetail(id: String) = api.getPostDetail(id)
    suspend fun likePost(id: String) = api.likePost(jsonBody("postsId" to id))
    suspend fun commentPost(id: String, content: String) =
        api.commentPost(jsonBody("postsId" to id, "content" to content))
    suspend fun sharePost(id: String) = api.sharePost(jsonBody("postsId" to id))

    // 文章
    suspend fun getArticleList(page: Int, pageSize: Int) = api.getArticleList(page, pageSize)
    suspend fun getArticleDetail(id: String) = api.getArticleDetail(id)

    // 攻略
    suspend fun getGuideList(page: Int, pageSize: Int) = api.getGuideList(page, pageSize)
    suspend fun getGuideDetail(id: String) = api.getGuideDetail(id)
}
