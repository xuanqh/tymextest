package com.tymex.test.data.repository

import com.tymex.test.data.remote.ServiceApi
import com.tymex.test.domain.model.User
import com.tymex.test.domain.model.UserDetail
import com.tymex.test.domain.repository.UserRepository
import javax.inject.Inject

open class UserRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : UserRepository {
    override suspend fun getUsers(perPage: Int, since: Int): List<User> {
        return serviceApi.getUsers(perPage, since)
    }

    override suspend fun getUserDetail(login: String): UserDetail {
        return serviceApi.getUserDetail(login)
    }
}