package com.example.feature_list.data

import com.example.api.DataDto
import com.example.api.RemoteDataSource
import com.example.api.UserDto
import com.example.api.UsersApi
import com.example.feature_list.domain.DepartmentRepository
import com.example.feature_list.base.RxRule
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.util.*

class DepartmentRepositoryImplTest {


    @get:Rule
    val rxRule: RxRule = RxRule()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    val usersApi = mock<UsersApi>()

    @Mock
    private lateinit var repository: DepartmentRepository

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(usersApi)
        repository = DepartmentRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `should emit user by query`() {
        val expectedUser = listOf(
            UserDto("1", "asd", "Maksim", "Pres", "ar", "android", "developer", Date(23), "")
        )

        val expectedData = DataDto(expectedUser)

        Mockito.`when`(remoteDataSource.fetchUsers()).thenReturn(Single.just(expectedData))

        repository.fetchUsers()
            .test()
            .assertResult(expectedUser)
            .assertNoErrors()
    }
}


