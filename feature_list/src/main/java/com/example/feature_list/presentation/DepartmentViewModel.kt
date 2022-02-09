package com.example.feature_list.presentation

import androidx.lifecycle.*
import com.example.api.UserDto
import com.example.feature_list.UsersListViewState
import com.example.feature_list.domain.usecase.FetchUsers
import com.example.feature_list.domain.usecase.SearchUsers
import javax.inject.Inject

internal class DepartmentViewModel @Inject constructor(
    private val fetchUsers: FetchUsers,
    private val searchUsers: SearchUsers
) : ViewModel() {
    private val _searchList = MutableLiveData<List<UserDto>>()
    private val _viewState = MutableLiveData<UsersListViewState>()
    val searchList: LiveData<List<UserDto>> = _searchList
    val viewState: LiveData<UsersListViewState> = _viewState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        fetchUsers.execute(
            onLoading = {
                _viewState.postValue(UsersListViewState.Loading)
            },
            onSuccess = {
                _viewState.postValue(UsersListViewState.Success(items = it))
            },
            onError = {
                _viewState.postValue(UsersListViewState.Error(message = it.localizedMessage))
            }
        )
    }

    fun onSearchTextChanged(searchText: String, listUsers: List<UserDto>) {
        val filteredUserList = searchUsers.onSearchTextChanged(searchText, listUsers)
        _searchList.postValue(filteredUserList)
    }

    override fun onCleared() {
        fetchUsers.dispose()
        super.onCleared()
    }
}