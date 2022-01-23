package com.example.trainee.ui.screens.screen_section_department

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.data.model.User
import com.example.trainee.data.model.Users
import com.example.trainee.ui.screens.screen_profile.ProfileFragment
import com.example.trainee.utils.MultiViewModelFactory
import com.example.trainee.utils.SearchListener
import javax.inject.Inject

class DepartmentFragment : Fragment(R.layout.fragment_department), SearchListener {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    lateinit var departmentViewModel: DepartmentViewModel
    private var nameDepartment: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TestAdapter
    private val listUsers: MutableList<User> = mutableListOf()

    companion object {
        const val NAME_DEPARTMENT = "NAME_DEPARTMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentFragment = this)
        departmentViewModel =
            ViewModelProvider(this, multiViewModelFactory)[DepartmentViewModel::class.java]
        nameDepartment = arguments?.get(NAME_DEPARTMENT).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.departmentRecyclerView)
        adapter = TestAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        observeListUsers(adapter)
        observeSearchList(adapter)

        adapter.attachClicks(object : UserListAdapterClicks {
            override fun onItemClick(model: User) {
                findNavController().navigate(
                    R.id.action_departmentHostFragment_to_profileFragment,
                    bundleOf(ProfileFragment.USER_DATA to model)
                )
            }
        })
    }

    private fun observeSearchList(adapter: TestAdapter) {
        departmentViewModel.searchList.observe(viewLifecycleOwner, { listUsers ->
            adapter.addData(listUsers)
        })
    }

    private fun observeListUsers(adapter: TestAdapter) {
        departmentViewModel.listUsers.observe(viewLifecycleOwner, {
            if (nameDepartment == "all") {
                listUsers.addAll(it.items)
                adapter.addData(listUsers)
            } else {
                listUsers.addAll(it.items.filter { user -> user.department == nameDepartment })
                adapter.addData(listUsers)
            }
        })
    }

    override fun onSearchTextChanged(searchText: String) {
        departmentViewModel.onSearchTextChanged(searchText, listUsers)
    }
}