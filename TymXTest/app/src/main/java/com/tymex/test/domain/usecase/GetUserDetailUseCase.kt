package com.tymex.test.domain.usecase
import com.tymex.test.domain.model.UserDetail
import com.tymex.test.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(login: String): UserDetail {
        return repository.getUserDetail(login)
    }
}