package com.example.trainee.ui.screens.screen_department_host

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DepartmentHostViewModel @Inject constructor(private val departmentHostRepository: DepartmentHostRepositoryImpl) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUsers()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    private fun fetchUsers() {
        compositeDisposable.add(departmentHostRepository.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("ViewModel", "$it")
            }, {
                Log.e("ViewModel", it.localizedMessage)
            })
        )
    }
}