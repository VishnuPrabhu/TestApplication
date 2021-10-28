package com.vishnu.testapplication.domain

import android.content.SharedPreferences
import androidx.core.content.edit

class OnBoardingCompleteUseCase(private val preferences: SharedPreferences) {

    operator fun invoke(value: Boolean) {
        preferences.edit { putBoolean("IS_USER_ONBOARDED", value) }
    }
}

class OnBoardingCompletedUseCase(private val preferences: SharedPreferences) {
    operator fun invoke(): Boolean {
        return preferences.getBoolean("IS_USER_ONBOARDED", false)
    }
}