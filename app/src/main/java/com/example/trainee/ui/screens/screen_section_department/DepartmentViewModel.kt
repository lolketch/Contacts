package com.example.trainee.ui.screens.screen_section_department

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainee.data.model.User
import com.example.trainee.utils.ConnectResolver
import com.example.trainee.utils.UsersListViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val departmentHostRepository: DepartmentHostRepositoryImpl,
    private val connectResolver: ConnectResolver,
    application: Application
) :
    AndroidViewModel(application) {
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
                departmentHostRepository.fetchUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _viewState.postValue(UsersListViewState.Success(items = it.items))
                    }, {
                        _viewState.postValue(UsersListViewState.Error(it.localizedMessage))
                        throw Exception(it)
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
}