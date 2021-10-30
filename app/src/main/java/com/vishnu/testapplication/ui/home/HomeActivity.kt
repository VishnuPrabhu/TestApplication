package com.vishnu.testapplication.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vishnu.testapplication.R
import com.vishnu.testapplication.ui.ProgressBarDialog
import com.vishnu.testapplication.ui.ProgressBarDialogInterface

class HomeActivity : AppCompatActivity(), ProgressBarDialogInterface {

    val progressDialog = ProgressBarDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

    override fun showProgress() {
        progressDialog.show(supportFragmentManager, "")
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }
}