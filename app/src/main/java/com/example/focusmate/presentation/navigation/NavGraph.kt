package com.example.focusmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.focusmate.presentation.settingsPage.about.AboutScreen
import com.example.focusmate.presentation.addtask.AddTaskScreen
import com.example.focusmate.presentation.settingsPage.backup.BackupScreen
import com.example.focusmate.presentation.focus.active.ActiveSessionScreen
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.focus.mode.FocusModeScreen
import com.example.focusmate.presentation.focus.selecttask.SelectTaskScreen
import com.example.focusmate.presentation.focus.complete.SessionCompleteScreen
import com.example.focusmate.presentation.focus.start.StartFocusScreen
import com.example.focusmate.presentation.history.HistoryScreen
import com.example.focusmate.presentation.home.HomeScreen
import com.example.focusmate.presentation.settingsPage.notification.NotificationScreen
import com.example.focusmate.presentation.progress.ProgressScreen
import com.example.focusmate.presentation.settingsPage.settings.SettingsScreen
import com.example.focusmate.presentation.splash.SplashScreen

@Composable
fun NavGraph(

    navController: NavHostController =
        rememberNavController()

) {

    NavHost(

        navController = navController,

        startDestination =
            Screen.Splash.route

    ) {

        /*
        ========================================
        SPLASH
        ========================================
        */

        composable(
            route = Screen.Splash.route
        ) {

            SplashScreen(
                navController = navController
            )
        }

        /*
        ========================================
        HOME
        ========================================
        */

        composable(
            route = Screen.Home.route
        ) {

            HomeScreen(
                navController = navController
            )
        }

        /*
        ========================================
        ADD TASK
        ========================================
        */

        composable(
            route = Screen.AddTask.route
        ) {

            AddTaskScreen(
                navController = navController
            )
        }

        /*
        ========================================
        FOCUS MODE
        ========================================
        */

        composable(
            route = Screen.Focus.route
        ) {

            val focusViewModel: FocusViewModel =
                hiltViewModel()

            FocusModeScreen(
                navController = navController,
                viewModel = focusViewModel
            )
        }

        /*
        ========================================
        SELECT TASK
        ========================================
        */

        composable(
            route = Screen.SelectTask.route
        ) {

            val focusBackStackEntry =
                remember(
                    navController
                ) {
                    navController.getBackStackEntry(
                        Screen.Focus.route
                    )
                }

            val focusViewModel: FocusViewModel =
                hiltViewModel(
                    focusBackStackEntry
                )

            SelectTaskScreen(
                navController = navController,
                viewModel = focusViewModel
            )
        }

        /*
        ========================================
        START FOCUS
        ========================================
        */

        composable(
            route = Screen.StartFocus.route
        ) {

            val focusBackStackEntry =
                remember(
                    navController
                ) {
                    navController.getBackStackEntry(
                        Screen.Focus.route
                    )
                }

            val focusViewModel: FocusViewModel =
                hiltViewModel(
                    focusBackStackEntry
                )

            StartFocusScreen(
                navController = navController,
                viewModel = focusViewModel
            )
        }

        /*
        ========================================
        ACTIVE SESSION
        ========================================
        */

        composable(
            route = Screen.ActiveSession.route
        ) {

            ActiveSessionScreen(
                navController = navController
            )
        }

        /*
        ========================================
        SESSION COMPLETE
        ========================================
        */

        composable(
            route = Screen.SessionComplete.route
        ) {

            SessionCompleteScreen(
                navController = navController
            )
        }

        /*
        ========================================
        PROGRESS
        ========================================
        */

        composable(
            route = Screen.Progress.route
        ) {

            ProgressScreen(
                navController = navController
            )
        }

        /*
        ========================================
        HISTORY
        ========================================
        */

        composable(
            route = Screen.History.route
        ) {

            HistoryScreen(
                navController = navController
            )
        }

        /*
        ========================================
        SETTINGS
        ========================================
        */

        composable(
            route = Screen.Settings.route
        ) {

            SettingsScreen(
                navController = navController
            )
        }

        /*
        ========================================
        NOTIFICATION
        ========================================
        */

        composable(
            route = Screen.Notification.route
        ) {

            NotificationScreen(
                navController = navController
            )
        }

        /*
        ========================================
        BACKUP
        ========================================
        */

        composable(
            route = Screen.Backup.route
        ) {

            BackupScreen(
                navController = navController
            )
        }

        /*
        ========================================
        ABOUT
        ========================================
        */

        composable(
            route = Screen.About.route
        ) {

            AboutScreen(
                navController = navController
            )
        }
    }
}
