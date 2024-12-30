package com.tymex.test.data.remote

import com.tymex.test.domain.model.User
import com.tymex.test.domain.model.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int
    ): List<User>

    @GET("users/{login_username}")
    suspend fun getUserDetail(
        @Path("login_username") login: String
    ): UserDetail
}