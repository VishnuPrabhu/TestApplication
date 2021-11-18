package com.vishnu.testapplication.ui.home

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vishnu.testapplication.R
import com.vishnu.testapplication.data.Payee
import com.vishnu.testapplication.databinding.BottomsheetPayeesListBinding

class PayeesListDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetPayeesListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomsheetPayeesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)!!
            bottomSheetInternal.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

            val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheetInternal)
            bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val payeesList = arguments?.getParcelableArrayList<Payee>("payee_list")
        val adapter = PayeesListAdapter()
        binding.payeesListView.adapter = adapter
        adapter.setOnClickListener {
            setFragmentResult("recipient", bundleOf("payee" to it))
            dismiss()
        }
        adapter.submitList(payeesList)
        binding.back.setOnClickListener {
            dismiss()
        }
    }

}
