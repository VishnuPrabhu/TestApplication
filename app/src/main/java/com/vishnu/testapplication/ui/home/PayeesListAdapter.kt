package com.vishnu.testapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.testapplication.data.Payee
import com.vishnu.testapplication.databinding.PayeeListItemBinding
import com.vishnu.testapplication.ui.util.ACCOUNT_NUMBER_FORMATTER
import com.vishnu.testapplication.ui.util.NumberFormatTextWatchers

class PayeesListAdapter : ListAdapter<Payee, PayeeViewHolder>(PayeeDiff()) {
    private var clickListener: PayeeClickListener? = null

    fun setOnClickListener(listener: PayeeClickListener) {
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayeeViewHolder {
        return PayeeViewHolder(PayeeListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),
            ::onItemClick
        )
    }

    private fun onItemClick(position: Int) {
        clickListener?.onPayeeClick(getItem(position))
    }

    override fun onBindViewHolder(holder: PayeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class PayeeDiff : DiffUtil.ItemCallback<Payee>() {
    override fun areItemsTheSame(oldItem: Payee, newItem: Payee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Payee, newItem: Payee): Boolean {
        return oldItem.accountNo == newItem.accountNo
    }

}

class PayeeViewHolder(val binding: PayeeListItemBinding, onClick: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(bindingAdapterPosition)
        }
        binding.accountNumber.addTextChangedListener(ACCOUNT_NUMBER_FORMATTER)
    }

    fun bind(item: Payee) {
        binding.viewmodel = item
        binding.executePendingBindings()
    }
}

fun interface PayeeClickListener {
    fun onPayeeClick(payee: Payee)
}