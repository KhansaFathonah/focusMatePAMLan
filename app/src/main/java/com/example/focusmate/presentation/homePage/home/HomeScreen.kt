package com.example.focusmate.presentation.homePage.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.focusmate.domain.model.Task
import com.example.focusmate.presentation.components.BottomNavbar
import com.example.focusmate.presentation.components.MotivationCard
import com.example.focusmate.presentation.components.TaskCard
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary
import com.example.focusmate.utils.TaskUtils
import java.util.Calendar

@Composable
fun HomeScreen(

    navController: NavController,

    viewModel: HomeViewModel =
        hiltViewModel()
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
    GREETING
    =========================================
    */

    val greeting =
        getGreetingMessage()

    val usernamePart =
        uiState.username
            .takeIf {
                it.isNotBlank()
            }
            ?.let { username ->
                " $username"
            }
            .orEmpty()

    /*
    =========================================
    ACTIVE TASKS
    =========================================
    */

    val activeTasks = remember(
        uiState.tasks
    ) {

        TaskUtils.getActiveTasks(
            uiState.tasks
        )
    }

    /*
    =========================================
    COMPLETED TASKS
    =========================================
    */

    val completedTasks = remember(
        uiState.tasks
    ) {

        TaskUtils.getCompletedTasks(
            uiState.tasks
        )
    }

    /*
    =========================================
    SCREEN
    =========================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    BackgroundDark
                )
        ) {

            /*
            =====================================
            MAIN CONTENT
            =====================================
            */

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .padding(
                        horizontal = 24.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(22.dp),

                contentPadding =
                    PaddingValues(

                        top = 24.dp,

                        bottom = 200.dp
                    )
            ) {

                /*
                =====================================
                HEADER
                =====================================
                */

                item {

                    Column {

                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(

                                text = "Good ",

                                color = TextPrimary,

                                fontSize = 34.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(

                                text =
                                    "$greeting$usernamePart",

                                color =
                                    ButtonPrimary,

                                fontSize = 34.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(

                            text =
                                "Let's make today productive",

                            color =
                                TextSecondary,

                            fontSize = 16.sp
                        )
                    }
                }

                /*
                =====================================
                MOTIVATION CARD
                =====================================
                */

                item {

                    MotivationCard(

                        quote =
                            uiState.motivation?.quote
                                ?: "Let's make today productive"
                    )
                }

                /*
                =====================================
                EMPTY STATE
                =====================================
                */

                if (
                    activeTasks.isEmpty() &&
                    completedTasks.isEmpty()
                ) {

                    item {

                        Box(

                            modifier = Modifier
                                .fillParentMaxSize(),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Column(

                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Text(

                                    text =
                                        "No tasks yet ✨",

                                    color =
                                        TextPrimary,

                                    fontSize = 20.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(

                                    text =
                                        "Tap the + button to create your first task.",

                                    color =
                                        TextSecondary,

                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                /*
                =====================================
                ONGOING TASKS
                =====================================
                */

                if (activeTasks.isNotEmpty()) {

                    item {

                        Column {

                            Text(

                                text =
                                    "Ongoing Tasks",

                                color =
                                    TextPrimary,

                                fontSize = 22.sp,

                                fontWeight =
                                    FontWeight.ExtraBold
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(

                                text =
                                    "${activeTasks.size} active tasks",

                                color =
                                    TextSecondary,

                                fontSize = 14.sp
                            )
                        }
                    }

                    items(

                        items = activeTasks,

                        key = { task ->

                            task.id
                        }

                    ) { task ->

                        HomeTaskItem(

                            task = task,

                            viewModel = viewModel
                        )
                    }
                }

                /*
                =====================================
                COMPLETED TASKS
                =====================================
                */

                if (completedTasks.isNotEmpty()) {

                    item {

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                    }

                    item {

                        Column {

                            Text(

                                text =
                                    "Completed Tasks",

                                color =
                                    TextPrimary,

                                fontSize = 22.sp,

                                fontWeight =
                                    FontWeight.ExtraBold
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(

                                text =
                                    "${completedTasks.size} completed tasks",

                                color =
                                    TextSecondary,

                                fontSize = 14.sp
                            )
                        }
                    }

                    items(

                        items = completedTasks,

                        key = { task ->

                            task.id
                        }

                    ) { task ->

                        HomeTaskItem(

                            task = task,

                            viewModel = viewModel
                        )
                    }
                }
            }

            /*
            =====================================
            FLOATING NAVBAR
            =====================================
            */

            Box(

                modifier = Modifier
                    .fillMaxSize()
            ) {

                BottomNavbar(

                    navController = navController,

                    modifier = Modifier
                        .align(
                            Alignment.BottomCenter
                        )
                        .padding(

                            horizontal = 20.dp,

                            vertical = 18.dp
                        )
                )

                /*
                =====================================
                FLOATING ACTION BUTTON
                =====================================
                */

                FloatingActionButton(

                    onClick = {

                        navController.navigate(
                            Screen.AddTask.route
                        )
                    },

                    modifier = Modifier
                        .align(
                            Alignment.BottomEnd
                        )
                        .padding(

                            end = 28.dp,

                            bottom = 112.dp
                        ),

                    shape = CircleShape,

                    containerColor =
                        Color(0xFF364775)

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            "Add Task",

                        tint = Color.White
                    )
                }
            }
        }
    }
}

/*
=========================================
HOME TASK ITEM
=========================================
*/

@Composable
private fun HomeTaskItem(

    task: Task,

    viewModel: HomeViewModel
) {

    TaskCard(

        task = task,

        /*
        =================================
        EDIT TASK
        =================================
        */

        onEditClick = { editedTask ->

            viewModel.updateTask(
                editedTask
            )
        },

        /*
        =================================
        DELETE TASK
        =================================
        */

        onDeleteClick = {

            viewModel.deleteTask(
                task
            )
        }
    )
}

/*
=========================================
GREETING LOGIC
=========================================
*/

fun getGreetingMessage(): String {

    val hour =

        Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY)

    return when (hour) {

        in 0..11 ->
            "Morning"

        in 12..16 ->
            "Afternoon"

        in 17..20 ->
            "Evening"

        else ->
            "Night"
    }
}