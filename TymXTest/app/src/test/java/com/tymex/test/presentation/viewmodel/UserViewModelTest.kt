package com.tymex.test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tymex.test.domain.model.User
import com.tymex.test.domain.repository.UserRepository
import com.tymex.test.domain.usecase.GetUsersUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class UserViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: UserRepository = Mockito.mock(UserRepository::class.java)
    private val getUsersUseCase: GetUsersUseCase = GetUsersUseCase(mockRepository) // Sửa cách khởi tạo UseCase
    private val userViewModel = UserViewModel(getUsersUseCase) // Truyền đúng UseCase vào ViewModel

    @Test
    fun testFetchUsersSuccess() = runBlocking {
        val mockUsers = listOf(
            User(
                login = "User0",
                id = 0,
                avatarUrl = "",
                eventsUrl = "",
                followersUrl = "",
                receivedEventsUrl = "",
                url = "",
                followingUrl = "",
                organizationsUrl = "",
                subscriptionsUrl = "",
                htmlUrl = "",
                gistsUrl = "",
                reposUrl = "",
                starredUrl = "",
                userViewType = "",
                type = "",
                nodeId = "",
                siteAdmin = false,
                gravatarId = ""
            ),
            User(
                login = "User1",
                id = 1,
                avatarUrl = "",
                eventsUrl = "",
                followersUrl = "",
                receivedEventsUrl = "",
                url = "",
                followingUrl = "",
                organizationsUrl = "",
                subscriptionsUrl = "",
                htmlUrl = "",
                gistsUrl = "",
                reposUrl = "",
                starredUrl = "",
                userViewType = "",
                type = "",
                nodeId = "",
                siteAdmin = false,
                gravatarId = ""
            )
        )
        `when`(mockRepository.getUsers(20, 0)).thenReturn(mockUsers) // Mock chính xác repository

        val observer = Observer<List<User>> {}
        try {
            userViewModel.users.observeForever { users ->
                assertEquals(mockUsers, users)
            }

            userViewModel.fetchUsers(20, 0)
        } finally {
            userViewModel.users.removeObserver(observer)
        }
    }
}