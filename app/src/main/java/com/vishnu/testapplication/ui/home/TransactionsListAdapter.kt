package com.vishnu.testapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.testapplication.data.Transaction
import com.vishnu.testapplication.data.TransactionSummary
import com.vishnu.testapplication.databinding.TransactionListItemBinding
import com.vishnu.testapplication.domain.util.toNumber

class TransactionsListAdapter : ListAdapter<TransactionSummary, TransactionViewHolder>(TransactionDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(TransactionListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class TransactionDiff : DiffUtil.ItemCallback<TransactionSummary>() {
    override fun areItemsTheSame(oldItem: TransactionSummary, newItem: TransactionSummary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TransactionSummary, newItem: TransactionSummary): Boolean {
        return oldItem.date == newItem.date
    }

}

class TransactionViewHolder(val binding: TransactionListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TransactionSummary) {
        binding.viewmodel = item
        binding.executePendingBindings()
    }
}