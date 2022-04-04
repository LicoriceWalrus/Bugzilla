package com.bugzilla.features.bugs.data.api

import com.bugzilla.features.bugs.data.dto.BugDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BugsApi {

    @GET("bug")
    suspend fun searchBugs(
        @Query("quicksearch") query: String,
        @Query("limit") limit: Int = 20,
    ): Response<BugDetailDto>

    @GET("bug/{id}")
    suspend fun searchBugById(
        @Path("id") id: String
    ): Response<BugDetailDto>

}
