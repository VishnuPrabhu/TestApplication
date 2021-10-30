package com.vishnu.testapplication.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vishnu.testapplication.R
import com.vishnu.testapplication.databinding.ProgressbarDialogBinding
import com.vishnu.testapplication.ui.ProgressBarDialog
import com.vishnu.testapplication.ui.ProgressBarDialogInterface
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoginActivity : AppCompatActivity(), ProgressBarDialogInterface {

    val progressDialog = ProgressBarDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }

    override fun showProgress() {
        progressDialog.show(supportFragmentManager, "")
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }
}

/*
 class ProgressBarActivityDelegate : ReadOnlyProperty<AppCompatActivity, ProgressBarDialogInterface>, ProgressBarDialogInterface {

    private var weakReferenceActivity: WeakReference<AppCompatActivity>? = null

    val progressDialog = ProgressBarDialog()

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): ProgressBarDialogInterface {
        return ProgressBarActivityDelegate()
            .also { weakReferenceActivity = WeakReference(thisRef) }
    }

    override fun showProgress() {
        weakReferenceActivity?.get()?.let {
            progressDialog.show(it.supportFragmentManager, "")
        }
    }

    override fun hideProgress() {
        weakReferenceActivity?.get()?.let {
            progressDialog.dismiss()
        }
    }
}*/