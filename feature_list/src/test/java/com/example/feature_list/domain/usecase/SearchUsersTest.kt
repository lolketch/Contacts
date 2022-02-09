package com.example.feature_list.domain.usecase

import com.example.api.UserDto
import org.junit.Assert.*

import org.junit.Test
import java.util.*

class SearchUsersTest {

    @Test
    fun shouldReturnDetectedUsers() {
        val searchText: String = "a"
        val listUsers: List<UserDto> = listOf(
            UserDto("1", "asd", "Maksim", "Pres", "ar", "android", "developer", Date(23), ""),
            UserDto("2", "afesd", "Oleg", "Lurx", "ve", "backend", "developer", Date(4231), ""),
            UserDto("3", "ds", "Alex", "Fgr", "ar", "frontend", "armani", Date(123), ""),
            UserDto("4", "ds", "Alex", "Fgr", "ar", "frontend", "developer", Date(123), ""),
            UserDto("5", "qwdq", "Kirill", "Alert", "ar", "media", "hz", Date(312), "")
        )

        val actual = SearchUsers().onSearchTextChanged(searchText, listUsers)
        val expected = listOf(
            UserDto("1", "asd", "Maksim", "Pres", "ar", "android", "developer", Date(23), ""),
            UserDto("3", "ds", "Alex", "Fgr", "ar", "frontend", "armani", Date(123), ""),
            UserDto("4", "ds", "Alex", "Fgr", "ar", "frontend", "developer", Date(123), ""),
            UserDto("5", "qwdq", "Kirill", "Alert", "ar", "media", "hz", Date(312), "")
        )

        assertEquals(actual, expected)
    }

    @Test
    fun shouldReturnEmptyList() {
        val searchText: String = "TestText"
        val listUsers: List<UserDto> = listOf(
            UserDto("1", "asd", "Maksim", "Pres", "ar", "android", "developer", Date(23), ""),
            UserDto("2", "afesd", "Oleg", "Lurx", "ve", "backend", "developer", Date(4231), ""),
            UserDto("3", "ds", "Alex", "Fgr", "ar", "frontend", "armani", Date(123), ""),
            UserDto("4", "ds", "Alex", "Fgr", "ar", "frontend", "developer", Date(123), ""),
            UserDto("5", "qwdq", "Kirill", "Alert", "ar", "media", "hz", Date(312), "")
        )

        val actual = SearchUsers().onSearchTextChanged(searchText, listUsers)
        val expected = listOf<UserDto>()

        assertEquals(actual, expected)
    }
}