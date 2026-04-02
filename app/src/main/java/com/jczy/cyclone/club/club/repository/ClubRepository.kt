package com.jczy.cyclone.club.club.repository

import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.model.ClubActivity
import com.jczy.cyclone.club.network.ClubApi
import com.jczy.cyclone.club.network.RetrofitClient
import javax.inject.Inject

class ClubRepository @Inject constructor() {
    private val api = RetrofitClient.createService<ClubApi>()
    
    suspend fun getClubList(page: Int, pageSize: Int) = api.getClubList(page, pageSize)
    suspend fun getClubDetail(id: String) = api.getClubDetail(id)
    suspend fun createClub(name: String, description: String, logo: String) = api.createClub(name, description, logo)
    suspend fun editClub(id: String, name: String, description: String, logo: String) = api.editClub(id, name, description, logo)
    suspend fun getClubActivityList(clubId: String, page: Int, pageSize: Int) = api.getClubActivityList(clubId, page, pageSize)
    suspend fun getClubActivityDetail(id: String) = api.getClubActivityDetail(id)
    suspend fun createClubActivity(clubId: String, title: String, description: String, startTime: Long, endTime: Long, location: String) = api.createClubActivity(clubId, title, description, startTime, endTime, location)
    suspend fun joinClubActivity(id: String) = api.joinClubActivity(id)
    suspend fun getClubMemberList(clubId: String, page: Int, pageSize: Int) = api.getClubMemberList(clubId, page, pageSize)
    suspend fun auditClubMember(id: String, status: Int) = api.auditClubMember(id, status)
    suspend fun scoreClub(id: String, score: Float) = api.scoreClub(id, score)
}