package com.vishnu.testapplication.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vishnu.testapplication.R
import com.vishnu.testapplication.data.Payee
import com.vishnu.testapplication.databinding.TransferFragmentBinding
import com.vishnu.testapplication.domain.EventObserver
import com.vishnu.testapplication.domain.util.toAmount
import com.vishnu.testapplication.domain.util.toEditableAmount
import com.vishnu.testapplication.ui.DialogsController.showAlertDialog
import com.vishnu.testapplication.ui.DialogsController.showErrorDialog
import com.vishnu.testapplication.ui.FragmentNavigationController.hideProgress
import com.vishnu.testapplication.ui.FragmentNavigationController.showProgress
import com.vishnu.testapplication.ui.util.toDateString
import com.vishnu.testapplication.ui.util.toTransferCalendar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TransferFragment : Fragment() {

    val viewModel: TransferViewModel by viewModel()

    private lateinit var binding: TransferFragmentBinding
    private var datePickerDialog: DatePickerDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TransferFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpLoading()
        setUpTransfer()
        setUpRecipient()
        setUpDate()
        viewModel.setUpValidations()
        setUpButtons()
    }

    private fun setUpDate() {
        binding.calendarEditText.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setUpLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    private fun setUpTransfer() {
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            showErrorDialog(requireActivity(), it, getString(R.string.ok)) {
                findNavController().popBackStack()
            }
        })
        viewModel.transferSuccess.observe(viewLifecycleOwner, EventObserver {
            showAlertDialog(requireActivity(), getString(R.string.transaction_success), it, getString(R.string.ok)) {
                findNavController().popBackStack()
            }
        })
    }

    private fun setUpRecipient() {
        viewModel.payees.observe(viewLifecycleOwner, EventObserver {
            binding.payee.isEnabled = it.isNotEmpty()
            if (it.isEmpty()) {
                showErrorDialog(requireActivity(), getString(R.string.general_error), getString(R.string.ok)) {
                    findNavController().popBackStack()
                }
            }
        })
        binding.payeeEditText.setOnClickListener {
            val dialog = PayeesListDialogFragment()
            val bundle = arrayListOf<Payee>()
            bundle.addAll(viewModel.payees.value?.peekContent().orEmpty())
            dialog.arguments = bundleOf("payee_list" to bundle)
            dialog.show(parentFragmentManager, "")
        }
        setFragmentResultListener("recipient") { key, bundle ->
            viewModel.transferPayee.value = bundle.getParcelable<Payee>("payee")
        }
    }

    private fun showDatePicker() {
        val transferDate = viewModel.transferDate.value.toTransferCalendar()

        datePickerDialog = DatePickerDialog(
            requireActivity(), ::onDateSelected,
            transferDate.get(Calendar.YEAR),
            transferDate.get(Calendar.MONTH),
            transferDate.get(Calendar.DAY_OF_MONTH)
        )
        // set the limit
        setUpMinMaxDate()
        datePickerDialog?.show()
    }

    private fun onDateSelected(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val selectedDate = calendar.time.toDateString()

        viewModel.transferDate.value = selectedDate
    }

    fun setUpMinMaxDate() {
        datePickerDialog?.datePicker?.minDate = Date().time
        datePickerDialog?.datePicker?.maxDate = Date().time + (730 * ONE_DAY)
    }

    companion object {
        private const val ONE_DAY = 86400000L
    }

    private fun setUpButtons() {
        binding.btnTransfer.setOnClickListener {
            viewModel.transferAmount()
        }
    }
}

@BindingAdapter("amount")
fun TextInputEditText.amount(value: String) {
    if (this.text.toString() != value) {
        val formatAmount = value.toAmount()
        this.setText(formatAmount)
        this.setSelection(formatAmount.count())
    }
}

@InverseBindingAdapter(attribute = "amount", event = "app:amountAttrChanged")
fun TextInputEditText.getAmount(): String {
    return text.toString()
}

@BindingAdapter("app:amountAttrChanged")
fun TextInputEditText.textChanged(textChanged: InverseBindingListener) {
    doAfterTextChanged {
        textChanged.onChange()
    }
}

