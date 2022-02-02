package com.example.core

interface SearchListener {
    
    fun onPageSelected(searchText: String)

    fun onSearchTextChanged(searchText: String)

    fun onRefresh()
}