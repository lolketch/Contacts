package com.example.trainee.ui.screens.screen_section_department

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainee.data.model.User
import com.example.trainee.data.model.Users
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostRepositoryImpl
import com.example.trainee.utils.SearchParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(private val departmentHostRepository: DepartmentHostRepositoryImpl) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _listUsers = MutableLiveData<Users>()
    private val _searchList = MutableLiveData<List<User>>()
    val listUsers: LiveData<Users> = _listUsers
    val searchList: LiveData<List<User>> = _searchList

    init {
        fetchUsers()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    private fun fetchUsers() {
        compositeDisposable.add(
            departmentHostRepository.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _listUsers.postValue(it)
                }, {
                    throw Exception(it)
                })
        )
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