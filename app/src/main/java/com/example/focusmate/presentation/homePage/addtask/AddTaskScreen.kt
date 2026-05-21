package com.example.focusmate.presentation.homePage.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTaskScreen(

    navController: NavController,

    viewModel: AddTaskViewModel = hiltViewModel()

) {

    /*
    =========================================
    UI STATE
    =========================================
    */

    val uiState by
    viewModel.uiState.collectAsState()

    /*
    =========================================
    CONTEXT
    =========================================
    */

    val context =
        LocalContext.current

    /*
    =========================================
    CALENDAR
    =========================================
    */

    val calendar =
        remember {

            Calendar.getInstance()
        }

    val todayStartMillis =
        remember {
            Calendar.getInstance()
                .apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                .timeInMillis
        }

    /*
    =========================================
    SCREEN
    =========================================
    */

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF0C1A33)
            )
            .padding(

                horizontal = 24.dp,
                vertical = 22.dp
            ),

        verticalArrangement =
            Arrangement.Top
    ) {

        /*
        =====================================
        TOP BAR
        =====================================
        */

        Row(

            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
            =================================
            BACK BUTTON
            =================================
            */

            IconButton(

                onClick = {

                    navController.popBackStack()
                }
            ) {

                Icon(

                    imageVector =
                        Icons.AutoMirrored.Outlined.ArrowBack,

                    contentDescription =
                        "Back",

                    tint = Color.White
                )
            }

            /*
            =================================
            TITLE
            =================================
            */

            Text(

                text = "Add New Task",

                color = Color.White,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(34.dp)
        )

        /*
        =====================================
        TASK TITLE LABEL
        =====================================
        */

        Text(

            text = "Task Title",

            color = Color.White,

            fontSize = 16.sp,

            fontWeight =
                FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        /*
        =====================================
        TASK TITLE INPUT
        =====================================
        */

        OutlinedTextField(

            value = uiState.title,

            onValueChange = {

                viewModel.onEvent(

                    AddTaskEvent.OnTitleChange(it)
                )
            },

            placeholder = {

                Text(

                    text =
                        "Enter task title...",

                    color =
                        Color.Gray
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            singleLine = true,

            shape = RoundedCornerShape(20.dp),

            colors =
                OutlinedTextFieldDefaults.colors(

                    focusedContainerColor =
                        Color(0xFFD9D9D9),

                    unfocusedContainerColor =
                        Color(0xFFD9D9D9),

                    focusedBorderColor =
                        Color(0xFFB1C4FF),

                    unfocusedBorderColor =
                        Color(0xFFB1C4FF),

                    focusedTextColor =
                        Color(0xFF0C1A33),

                    unfocusedTextColor =
                        Color(0xFF0C1A33),

                    cursorColor =
                        Color(0xFF0C1A33)
                )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
        =====================================
        DEADLINE LABEL
        =====================================
        */

        Text(

            text = "Deadline",

            color = Color.White,

            fontSize = 16.sp,

            fontWeight =
                FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        /*
        =====================================
        DEADLINE FIELD
        =====================================
        */

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .clickable(

                    indication = null,

                    interactionSource =
                        remember {

                            MutableInteractionSource()
                        }

                ) {

                    /*
                    =================================
                    DATE PICKER
                    =================================
                    */

                    DatePickerDialog(

                        context,

                        { _, year, month, dayOfMonth ->

                            /*
                            =========================
                            TIME PICKER
                            =========================
                            */

                            TimePickerDialog(

                                context,

                                { _, hour, minute ->

                                    calendar.set(

                                        year,
                                        month,
                                        dayOfMonth,
                                        hour,
                                        minute
                                    )

                                    val formatter =

                                        SimpleDateFormat(

                                            "dd MMM yyyy • hh:mm a",

                                            Locale.getDefault()
                                        )

                                    val formattedDate =

                                        formatter.format(
                                            calendar.time
                                        )

                                    viewModel.onEvent(

                                        AddTaskEvent
                                            .OnDeadlineChange(

                                                formattedDate,

                                                calendar.timeInMillis
                                            )
                                    )
                                },

                                calendar.get(
                                    Calendar.HOUR_OF_DAY
                                ),

                                calendar.get(
                                    Calendar.MINUTE
                                ),

                                false

                            ).show()
                        },

                        calendar.get(
                            Calendar.YEAR
                        ),

                        calendar.get(
                            Calendar.MONTH
                        ),

                        calendar.get(
                            Calendar.DAY_OF_MONTH
                        )

                    ).apply {

                        datePicker.minDate =
                            todayStartMillis

                    }.show()
                }
        ) {

            OutlinedTextField(

                value = uiState.deadline,

                onValueChange = {},

                readOnly = true,

                enabled = false,

                placeholder = {

                    Text(

                        text =
                            "Select deadline",

                        color =
                            Color.Gray
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                singleLine = true,

                shape = RoundedCornerShape(20.dp),

                colors =
                    OutlinedTextFieldDefaults.colors(

                        disabledContainerColor =
                            Color(0xFFD9D9D9),

                        disabledBorderColor =
                            Color(0xFFB1C4FF),

                        disabledTextColor =
                            Color(0xFF0C1A33),

                        disabledPlaceholderColor =
                            Color.Gray
                    )
            )
        }

        uiState.errorMessage?.let { errorMessage ->

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = errorMessage,
                color = Color(0xFFFFB4AB),
                fontSize = 13.sp
            )
        }

        Spacer(
            modifier = Modifier.height(42.dp)
        )

        /*
        =====================================
        SAVE BUTTON
        =====================================
        */

        Button(

            onClick = {

                viewModel.onEvent(
                    AddTaskEvent.SaveTask
                )

                navController.popBackStack()
            },

            enabled =
                uiState.isSaveEnabled,

            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .width(168.dp)
                .height(52.dp),

            shape = RoundedCornerShape(22.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =

                        if (
                            uiState.isSaveEnabled
                        )

                            Color(0xFFB1C4FF)

                        else

                            Color(0xFF6F778B),

                    contentColor =
                        Color(0xFF0C1A33)
                )
        ) {

            Text(

                text = "Save Task",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}
