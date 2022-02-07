package com.example.feature_list.domain.usecase

import com.example.api.UserDto
import com.example.core.base.SingleUseCase
import com.example.feature_list.domain.DepartmentRepository
import io.reactivex.Single
import javax.inject.Inject

internal class FetchUsers @Inject constructor(
    private val repository: DepartmentRepository
) : SingleUseCase<List<UserDto>>() {
    override fun buildUseCaseSingle(): Single<List<UserDto>> {
        return repository.fetchUsers()
    }
}