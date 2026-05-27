package com.example.focusmate.utils

import com.example.focusmate.domain.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TaskUtils {

    /*
    ====================================
    DATE FORMAT
    ====================================
    */

    private const val DEADLINE_PATTERN =
        "dd MMM yyyy • hh:mm a"

    private val dateFormatter by lazy {

        SimpleDateFormat(

            DEADLINE_PATTERN,

            Locale.getDefault()
        )
    }

    /*
    ====================================
    SORT TASKS
    ====================================
    */

    fun sortTasksByPriority(

        tasks: List<Task>

    ): List<Task> {

        return tasks.sortedWith(

            compareBy<Task> {

                when {

                    isTaskOverdue(it) -> 0

                    isTaskCompleted(it) -> 2

                    else -> 1
                }

            }.thenBy {

                parseDeadline(
                    it.deadline
                )?.time ?: Long.MAX_VALUE

            }.thenBy {

                it.title.lowercase()
            }
        )
    }

    /*
    ====================================
    DISPLAY TASKS
    ====================================
    */

    fun getDisplayTasks(

        tasks: List<Task>

    ): List<Task> {

        return tasks.map { task ->

            when {

                isTaskCompleted(task) -> {

                    task
                }

                TimeUtils.isTaskOverdue(
                    task.deadline
                ) -> {

                    task.copy(
                        status = "Overdue"
                    )
                }

                task.status ==
                        "Overdue" -> {

                    task.copy(
                        status =
                            "Not Started"
                    )
                }

                else -> {

                    task
                }
            }
        }
    }

    /*
    ====================================
    ACTIVE TASKS
    ====================================
    */

    fun getActiveTasks(

        tasks: List<Task>

    ): List<Task> {

        return sortTasksByPriority(

            getDisplayTasks(tasks)
                .filterNot {

                    isTaskCompleted(it)
                }
        )
    }

    /*
    ====================================
    COMPLETED TASKS
    ====================================
    */

    fun getCompletedTasks(

        tasks: List<Task>

    ): List<Task> {

        return getDisplayTasks(tasks)
            .filter {

                isTaskCompleted(it)
            }
    }

    /*
    ====================================
    PARSE DEADLINE
    ====================================
    */

    fun parseDeadline(

        deadline: String

    ): Date? {

        return runCatching {

            dateFormatter.parse(
                deadline.trim()
            )

        }.getOrNull()
    }

    /*
    ====================================
    TASK STATUS
    ====================================
    */

    fun isTaskCompleted(

        task: Task

    ): Boolean {

        return task.status ==
                "Completed"
    }

    fun isTaskOverdue(

        task: Task

    ): Boolean {

        return task.status ==
                "Overdue" ||

                TimeUtils.isTaskOverdue(
                    task.deadline
                )
    }

    fun isTaskPending(

        task: Task

    ): Boolean {

        return task.status ==
                "Not Started"
    }
}