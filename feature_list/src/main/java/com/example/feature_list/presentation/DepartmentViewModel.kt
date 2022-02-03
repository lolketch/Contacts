package com.example.feature_list.presentation

import androidx.lifecycle.*
import com.example.api.ConnectResolver
import com.example.feature_list.UsersListViewState
import com.example.feature_list.domain.GetUsersUseCase
import com.example.trainee.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider

internal class DepartmentViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val connectResolver: ConnectResolver
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _searchList = MutableLiveData<List<User>>()
    private val _viewState = MutableLiveData<UsersListViewState>()
    val searchList: LiveData<List<User>> = _searchList
    val viewState: LiveData<UsersListViewState> = _viewState

    init {
        fetchUsers()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchUsers() {
        if (connectResolver.isOnline()) {
            _viewState.postValue(UsersListViewState.Loading)
            compositeDisposable.add(
                getUsersUseCase.fetchUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _viewState.postValue(UsersListViewState.Success(items = it.items))
                    }, {
                        _viewState.postValue(UsersListViewState.Error(message = it.localizedMessage))
                    })
            )
        } else _viewState.postValue(UsersListViewState.Error("Not internet"))
    }

    fun onSearchTextChanged(searchText: String, listUsers: List<User>) {
        val filteredUserList = listUsers.filter {
            it.firstName.lowercase().contains(searchText.lowercase()) ||
                    it.lastName.lowercase().contains(searchText.lowercase()) ||
                    it.position.lowercase().contains(searchText.lowercase())
        }
        _searchList.postValue(filteredUserList)
    }

    class Factory @Inject constructor(
        private val getUsersUseCase: Provider<GetUsersUseCase>,
        private val connectResolver: Provider<ConnectResolver>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == DepartmentViewModel::class.java)
            return DepartmentViewModel(getUsersUseCase.get(),connectResolver.get()) as T
        }
    }
}