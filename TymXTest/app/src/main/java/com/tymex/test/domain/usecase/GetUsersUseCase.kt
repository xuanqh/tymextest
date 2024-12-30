package com.tymex.test.domain.usecase
import com.tymex.test.domain.model.User
import com.tymex.test.domain.repository.UserRepository
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(perPage: Int, since: Int): List<User> {
        return repository.getUsers(perPage, since)
    }
}