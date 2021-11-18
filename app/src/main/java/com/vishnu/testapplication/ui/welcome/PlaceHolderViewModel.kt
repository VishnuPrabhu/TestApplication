package com.vishnu.testapplication.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vishnu.testapplication.R

class PlaceHolderViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val drawableResId: LiveData<Int> = Transformations.map(_index) {
        when(it) {
            0 -> R.drawable.welcome1
            1 -> R.drawable.welcome2
            2 -> R.drawable.welcome3
            3 -> R.drawable.welcome4
            else -> R.drawable.welcome1
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}