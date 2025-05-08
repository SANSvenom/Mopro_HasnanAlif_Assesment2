package com.hasnan0062.assesment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hasnan0062.assesment2.database.BukuDao
import com.hasnan0062.assesment2.screen.MainViewModel
import com.hasnan0062.assesment2.ui.screen.DetailViewModel

class ViewModelFactory (
    private val dao: BukuDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel:: class.java)) {
            return MainViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel:: class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}