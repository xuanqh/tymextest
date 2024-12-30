package com.tymex.test.domain.usecase

import com.tymex.test.domain.model.User
import com.tymex.test.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetUsersUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        mockRepository = Mockito.mock(UserRepository::class.java)
        getUsersUseCase = GetUsersUseCase(mockRepository)
    }

    @Test
    fun testExecute() = runBlocking {
        Mockito.`when`(mockRepository.getUsers(20, 0)).thenReturn(
            List(20) { index -> User(login =  "User$index", id = index, avatarUrl = "", eventsUrl = "", followersUrl = "", receivedEventsUrl = "", url = "", followingUrl = "", organizationsUrl = "", subscriptionsUrl = "", htmlUrl = "", gistsUrl = "", reposUrl = "", starredUrl = "", userViewType = "", type = "", nodeId = "", siteAdmin = false, gravatarId = "") }
        )

        val result = getUsersUseCase.execute(20, 0)
        assertEquals(20, result.size)
        assertEquals("User0", result[0].login)
    }
}