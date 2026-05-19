package com.example.focusmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.focusmate.presentation.focus.active.ActiveSessionScreen
import com.example.focusmate.presentation.focus.complete.SessionCompleteScreen
import com.example.focusmate.presentation.focus.mode.FocusModeScreen
import com.example.focusmate.presentation.focus.selecttask.SelectTaskScreen
import com.example.focusmate.presentation.focus.start.StartFocusScreen
import com.example.focusmate.presentation.homePage.addtask.AddTaskScreen
import com.example.focusmate.presentation.homePage.home.HomeScreen
import com.example.focusmate.presentation.progressPage.history.HistoryScreen
import com.example.focusmate.presentation.progressPage.progress.ProgressScreen
import com.example.focusmate.presentation.settingsPage.about.AboutScreen
import com.example.focusmate.presentation.settingsPage.backup.BackupScreen
import com.example.focusmate.presentation.settingsPage.main.MainSettingsScreen
import com.example.focusmate.presentation.settingsPage.notification.NotificationScreen
import com.example.focusmate.presentation.splash.SplashScreen

@Composable
fun NavGraph(

    navController: NavHostController =
        rememberNavController()

) {

    /*
    ========================================
    NAV HOST
    ========================================
    */

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

            FocusModeScreen(
                navController = navController
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

            SelectTaskScreen(
                navController = navController
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

            StartFocusScreen(
                navController = navController
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
        MAIN SETTINGS
        ========================================
        */

        composable(
            route = Screen.Settings.route
        ) {

            MainSettingsScreen(
                navController = navController
            )
        }

        /*
        ========================================
        NOTIFICATION SETTINGS
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
        BACKUP SETTINGS
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
        ABOUT APP
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