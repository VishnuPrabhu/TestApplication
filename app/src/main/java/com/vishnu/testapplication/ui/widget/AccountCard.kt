package com.vishnu.testapplication.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.vishnu.testapplication.databinding.WidgetAccountCardBinding

class AccountCard(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    var binding = WidgetAccountCardBinding.inflate(LayoutInflater.from(context), this, true)

    var accountName: CharSequence?
        get() = binding.title1.text
        set(value) {
            binding.title1.text = value
        }

    var accountNumber: CharSequence?
        get() = binding.subtitle1.text
        set(value) {
            binding.subtitle1.text = value
        }

    var accountBalance: CharSequence?
        get() = binding.subtitle3.text
        set(value) {
            binding.subtitle3.text = value
        }

    var amountTitle: CharSequence?
        get() = binding.subtitle2.text
        set(value) {
            binding.subtitle2.text = value
        }

    var amountNote: CharSequence?
        get() = binding.subtitle4.text
        set(value) {
            binding.subtitle4.text = value
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.shimmerLayout.showShimmer(true)
            } else {
                binding.shimmerLayout.hideShimmer()
            }
        }
}