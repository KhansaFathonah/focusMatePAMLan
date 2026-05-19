package com.example.focusmate.presentation.focus.selecttask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun SelectTaskScreen(

    navController: NavController,

    viewModel: FocusViewModel =
        hiltViewModel()
) {

    /*
    ====================================
    UI STATE
    ====================================
    */

    val uiState by
    viewModel.uiState.collectAsState()

    val displayTasks =
        uiState.tasks

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

                    horizontal = 29.dp,
                    vertical = 50.dp
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
                modifier = Modifier.height(32.dp)
            )

            /*
            ====================================
            TASK LIST
            ====================================
            */

            if (displayTasks.isEmpty()) {

                Column(

                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally,

                    verticalArrangement =
                        Arrangement.Center
                ) {

                    Text(

                        text = "No tasks available",

                        color = Color.White,

                        fontSize = 18.sp,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(

                        text = "Create a task from Home first.",

                        color = Color.White.copy(alpha = 0.6f),

                        fontSize = 14.sp
                    )
                }

            } else {

                LazyColumn(

                    modifier = Modifier
                        .weight(1f),

                    verticalArrangement =
                        Arrangement.spacedBy(20.dp)
                ) {

                    items(

                        items = displayTasks,

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

                                navController.navigate(
                                    Screen.StartFocus.route
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
