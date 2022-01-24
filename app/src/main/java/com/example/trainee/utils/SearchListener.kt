package com.example.trainee.utils

interface SearchListener {
    
    fun onPageSelected(searchText: String)

    fun onSearchTextChanged(searchText: String)
}