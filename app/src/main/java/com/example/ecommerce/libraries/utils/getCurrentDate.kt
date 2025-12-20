package com.example.ecommerce.libraries.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtil {

    companion object {


        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(Date())
        }
    }
}