package com.vishnu.testapplication.ui

import android.app.Activity
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogsController {

    fun showErrorDialog(context: Activity, title: String, okBtn: String, onClick: () -> Unit = {}) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(null)
            .setPositiveButton(okBtn) { dialog, which ->
                dialog.dismiss()
                onClick()
            }
            .show()
    }

    fun showAlertDialog(
        context: Context, title: String, message: String, okBtn: String,
        onClick: () -> Unit = {}, cancelBtn: String, onCancel: () -> Unit = {}
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okBtn) { dialog, which ->
                dialog.dismiss()
                onClick()
            }
            .setNegativeButton(cancelBtn) { dialog, which ->
                dialog.dismiss()
                onCancel()
            }
            .show()
    }
}