package com.example.focusmate.presentation.focus.withTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.TaskSelectionCard
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.utils.TaskUtils

@Composable
fun SelectTaskScreen(

    navController: NavController,

    viewModel: FocusViewModel
) {

    /*
    ====================================
    UI STATE
    ====================================
    */

    val uiState by
    viewModel.uiState.collectAsState()

    /*
    ====================================
    SORT TASKS
    overdue first
    nearest deadline
    ====================================
    */

    val sortedTasks = remember(
        uiState.tasks
    ) {

        TaskUtils.sortTasksByPriority(
            uiState.tasks
        )
    }

    /*
    ====================================
    SCREEN
    ====================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    BackgroundDark
                )
                .padding(
                    paddingValues
                )
                .padding(

                    horizontal = 24.dp,
                    vertical = 22.dp
                )
                .navigationBarsPadding()
        ) {

            /*
            ====================================
            TOP BAR
            ====================================
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

                    text = "Select Task",

                    color = Color.White,

                    fontSize = 18.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            /*
            ====================================
            TASK LIST
            ====================================
            */

            LazyColumn(

                modifier = Modifier
                    .weight(1f),

                verticalArrangement =
                    Arrangement.spacedBy(18.dp),

                contentPadding =
                    PaddingValues(

                        bottom = 24.dp
                    )
            ) {

                items(

                    items = sortedTasks,

                    key = { task ->

                        task.id
                    }

                ) { task ->

                    TaskSelectionCard(

                        task = task,

                        isSelected =

                            uiState.selectedTask?.id
                                    ==
                                    task.id,

                        onClick = {

                            viewModel.selectTask(
                                task
                            )
                        }
                    )
                }
            }

            /*
            ====================================
            CONTINUE BUTTON
            ====================================
            */

            Button(

                onClick = {

                    uiState.selectedTask?.let {

                        navController.navigate(
                            Screen.StartFocus.route
                        )
                    }
                },

                enabled =
                    uiState.selectedTask != null,

                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .padding(

                        top = 12.dp,
                        bottom = 18.dp
                    )
                    .fillMaxWidth(0.58f)
                    .height(56.dp),

                shape =
                    RoundedCornerShape(22.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =

                            if (
                                uiState.selectedTask != null
                            )

                                Color(0xFFB1C4FF)

                            else

                                Color(0xFF5F6984),

                        contentColor =
                            Color(0xFF1B2336)
                    )
            ) {

                Text(

                    text = "Continue",

                    fontSize = 17.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}