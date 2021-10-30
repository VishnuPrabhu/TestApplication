package com.vishnu.testapplication.ui

import android.app.Activity
import android.content.Intent
import com.vishnu.testapplication.ui.home.HomeActivity

object ActivityNavigation {

    fun Activity.navigateToHome(isRoot: Boolean = false) {
        val homeIntent = Intent(this, HomeActivity::class.java)
        if (isRoot) {
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(homeIntent)
    }
}