package com.example.focusmate.utils

import com.example.focusmate.domain.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TaskUtils {

    /*
    ====================================
    SORT TASKS
    OVERDUE FIRST
    THEN NEAREST DEADLINE
    ====================================
    */

    fun sortTasksByPriority(

        tasks: List<Task>

    ): List<Task> {

        return tasks.sortedWith(

            compareBy<Task> {

                !TimeUtils.isTaskOverdue(
                    it.deadline
                )

            }.thenBy {

                parseDeadline(
                    it.deadline
                ) ?: Date(Long.MAX_VALUE)
            }
        )
    }

    /*
    ====================================
    PARSE DEADLINE
    ====================================
    */

    private fun parseDeadline(
        deadline: String
    ): Date? {

        return try {

            SimpleDateFormat(

                "dd MMM yyyy • hh:mm a",

                Locale.getDefault()

            ).parse(deadline)

        } catch (_: Exception) {

            null
        }
    }
}