/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vishnu.testapplication.ui.util

/**
 * Extension functions and Binding Adapters.
 */

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("app:textVisible")
fun TextView.setTextVisible(value: String) {
    text = value
    isVisible = value.isNotEmpty()
}

val ACCOUNT_NUMBER_FORMATTER = NumberFormatTextWatchers(limit= "-")

class NumberFormatTextWatchers(val chunk: Int = 3, val max: Int = 100, val limit: String = " ") : TextWatcher {
    private var current = ""

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (s.toString() != current) {
            val input = s.toString().replace(junkDigits, "")
            current = input.chunked(chunk).joinToString(limit)
            s.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max + (max / chunk)))
            s.replace(0, s.length, current, 0, current.length)
        }
    }

    companion object {
        private val junkDigits = Regex("[^\\d.]")
    }
}