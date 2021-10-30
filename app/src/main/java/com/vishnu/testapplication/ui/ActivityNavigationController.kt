package com.vishnu.testapplication.ui

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.vishnu.testapplication.ui.home.HomeActivity

object ActivityNavigationController {

    fun Activity.navigateToHome(isRoot: Boolean = false) {
        val homeIntent = Intent(this, HomeActivity::class.java)
        if (isRoot) {
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(homeIntent)
    }
}

object FragmentNavigationController {

    fun Fragment.showProgress() {
        (requireActivity() as? ProgressBarDialogInterface)?.showProgress()
    }

    fun Fragment.hideProgress() {
        (requireActivity() as? ProgressBarDialogInterface)?.hideProgress()
    }
}

interface ProgressBarDialogInterface {
    fun showProgress()
    fun hideProgress()
}


