package com.bugzilla.features.bugs.data.api

import com.bugzilla.features.bugs.data.dto.BugDetailDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BugsApi {

    /** Информация по багу */
    @GET("bug/{id}")
    fun getBugDetail(
        /** ID бага */
        @Path("id") id: Int?
    ): Single<BugDetailDto>

}
