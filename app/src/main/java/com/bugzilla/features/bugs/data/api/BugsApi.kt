package com.bugzilla.features.bugs.data.api

import com.bugzilla.features.bugs.data.dto.BugDetailDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BugsApi {

    @GET("bug")
    fun searchBugs(
        @Query("quicksearch") query: String,
        @Query("limit") limit: Int = 20,
    ): Single<BugDetailDto>

    @GET("bug/{id}")
    fun searchBugById(
        @Path("id") id: String
    ): Single<BugDetailDto>

}
