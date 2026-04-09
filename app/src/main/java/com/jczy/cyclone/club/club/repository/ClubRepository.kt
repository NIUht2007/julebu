package com.jczy.cyclone.club.club.repository

import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.model.ClubActivity
import com.jczy.cyclone.club.network.ClubApi
import com.jczy.cyclone.club.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class ClubRepository @Inject constructor() {
    private val api = RetrofitClient.createService<ClubApi>()

    private fun jsonBody(vararg pairs: Pair<String, Any?>): okhttp3.RequestBody {
        val json = JSONObject()
        pairs.forEach { (k, v) -> if (v != null) json.put(k, v) }
        return json.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }

    suspend fun getClubList(page: Int, pageSize: Int) = api.getClubList(page, pageSize)
    suspend fun getClubDetail(id: String) = api.getClubDetail(id)
    suspend fun createClub(name: String, description: String, logo: String) =
        api.createClub(jsonBody("name" to name, "description" to description, "logo" to logo))
    suspend fun editClub(id: String, name: String, description: String, logo: String) =
        api.editClub(jsonBody("id" to id, "name" to name, "description" to description, "logo" to logo))
    suspend fun getClubActivityList(clubId: String, page: Int, pageSize: Int) =
        api.getClubActivityList(clubId, page, pageSize)
    suspend fun getClubActivityDetail(id: String) = api.getClubActivityDetail(id)
    suspend fun createClubActivity(
        clubId: String, title: String, description: String,
        startTime: Long, endTime: Long, location: String
    ) = api.createClubActivity(
        jsonBody(
            "clubId" to clubId, "title" to title, "description" to description,
            "startTime" to startTime, "endTime" to endTime, "location" to location
        )
    )
    suspend fun joinClubActivity(id: String) =
        api.joinClubActivity(jsonBody("postsId" to id))
    suspend fun getClubMemberList(clubId: String, page: Int, pageSize: Int) =
        api.getClubMemberList(clubId, page, pageSize)
    suspend fun auditClubMember(id: String, status: Int) =
        api.auditClubMember(jsonBody("id" to id, "status" to status))
    suspend fun scoreClub(id: String, score: Float) =
        api.scoreClub(jsonBody("postsId" to id, "score" to score))
}
