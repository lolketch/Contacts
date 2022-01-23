package com.example.trainee.ui.screens.screen_section_department

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainee.data.model.User
import com.example.trainee.data.model.Users
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(private val departmentHostRepository: DepartmentHostRepositoryImpl) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _listUsers = MutableLiveData<Users>()
    val listUsers: LiveData<Users> = _listUsers
    private val _searchList = MutableLiveData<List<User>>()
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
        val searchText2 = searchText.toLowerCase()
        val q = listUsers.filter {
            it.firstName.toLowerCase().contains(searchText2) ||
                    it.lastName.toLowerCase().contains(searchText2) ||
                    it.position.toLowerCase().contains(searchText2)
        }
        _searchList.postValue(q)

    }
//    fun fetchUsers(departmentName:String) {
//        val cachedCharacters = TestCache.characterMap[departmentName]
//        if (cachedCharacters != null){
//            _listUsers.postValue(cachedCharacters!!)
//            return
//        }
//        compositeDisposable.add(
//            departmentHostRepository.fetchUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    _listUsers.postValue(it)
//                    TestCache.characterMap[departmentName] = it
//                }, {
//                    Log.e("ViewModel", it.localizedMessage)
//                })
//        )
//    }
//
//    private fun fetchDepartments() {
//        compositeDisposable.add(
//            departmentHostRepository.fetchUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    _listDepartments.postValue(it.items.map { it.department }.distinct().sortedBy { it })
//                    val q = it.items.map { it.department }.distinct().sortedBy { it }
//                }, {
//                    Log.e("ViewModel", it.localizedMessage)
//                })
//        )
//    }
}