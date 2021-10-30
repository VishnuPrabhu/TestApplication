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

import java.text.SimpleDateFormat
import java.util.*


const val DD_MMM = "dd MMM"                                 // 12 Sep
const val YYYYMMDDHHMMSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"   // 2021-09-12T02:13:03.054Z

fun String?.convertDate(from: String, to: String): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return try {
        val fromFormat = SimpleDateFormat(from, Locale.US)
        val fromDate = fromFormat.parse(this)
        val toFormat = SimpleDateFormat(to, Locale.US)
        val toDate = toFormat.format(fromDate)
        toDate
    } catch (e: Exception) {
        ""
    }
}
