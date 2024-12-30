package com.tymex.test.domain.repository

import com.tymex.test.domain.model.User
import com.tymex.test.domain.model.UserDetail

interface UserRepository {
    suspend fun getUsers(perPage: Int, since: Int): List<User>
    suspend fun getUserDetail(login: String): UserDetail
}