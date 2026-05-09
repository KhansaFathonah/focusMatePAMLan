package com.example.focusmate.utils

import java.text.SimpleDateFormat
import java.util.Locale

object TimeUtils {

    /*
    ====================================
    CHECK OVERDUE
    ====================================
    */

    fun isTaskOverdue(
        deadline: String
    ): Boolean {

        return try {

            /*
            ================================
            FORMATTER
            ================================
            */

            val formatter =

                SimpleDateFormat(

                    "dd MMM yyyy • hh:mm a",

                    Locale.getDefault()
                )

            /*
            ================================
            PARSE DEADLINE
            ================================
            */

            val deadlineDate =
                formatter.parse(deadline)

            /*
            ================================
            CURRENT TIME
            ================================
            */

            val currentTime =
                System.currentTimeMillis()

            /*
            ================================
            CHECK
            ================================
            */

            deadlineDate != null &&
                    deadlineDate.time < currentTime

        } catch (e: Exception) {

            false
        }
    }
}