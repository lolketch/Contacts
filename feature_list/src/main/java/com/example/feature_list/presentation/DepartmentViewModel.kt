package com.example.feature_list.presentation

import androidx.lifecycle.*
import com.example.core.ConnectResolver
import com.example.api.UserDto
import com.example.feature_list.UsersListViewState
import com.example.feature_list.domain.DepartmentUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class DepartmentViewModel @Inject constructor(
    private val departmentUseCase: DepartmentUseCase,
    private val connectResolver: ConnectResolver
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _searchList = MutableLiveData<List<UserDto>>()
    private val _viewState = MutableLiveData<UsersListViewState>()
    val searchList: LiveData<List<UserDto>> = _searchList
    val viewState: LiveData<UsersListViewState> = _viewState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        if (connectResolver.isOnline()) {
            _viewState.postValue(UsersListViewState.Loading)
            compositeDisposable.add(
                departmentUseCase.fetchUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _viewState.postValue(UsersListViewState.Success(items = it))
                    }, {
                        _viewState.postValue(UsersListViewState.Error(message = it.localizedMessage))
                    })
            )
        } else _viewState.postValue(UsersListViewState.Error("Not internet"))
    }

    fun onSearchTextChanged(searchText: String, listUsers: List<UserDto>) {
        val filteredUserList = departmentUseCase.onSearchTextChanged(searchText, listUsers)
        _searchList.postValue(filteredUserList)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}