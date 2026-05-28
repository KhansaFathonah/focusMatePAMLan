package com.example.focusmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.focus.complete.SessionCompleteScreen
import com.example.focusmate.presentation.focus.main.FocusModeScreen
import com.example.focusmate.presentation.focus.quick.QuickFocusCompleteScreen
import com.example.focusmate.presentation.focus.quick.QuickFocusDurationScreen
import com.example.focusmate.presentation.focus.quick.QuickFocusSessionScreen
import com.example.focusmate.presentation.focus.withTask.ActiveSessionScreen
import com.example.focusmate.presentation.focus.withTask.SelectDurationScreen
import com.example.focusmate.presentation.focus.withTask.SelectTaskScreen
import com.example.focusmate.presentation.homePage.addtask.AddTaskScreen
import com.example.focusmate.presentation.homePage.home.HomeScreen
import com.example.focusmate.presentation.progressPage.history.HistoryScreen
import com.example.focusmate.presentation.progressPage.progress.ProgressScreen
import com.example.focusmate.presentation.settingsPage.about.AboutScreen
import com.example.focusmate.presentation.settingsPage.backup.BackupScreen
import com.example.focusmate.presentation.settingsPage.main.MainSettingsScreen
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

        composable(
            route = Screen.Splash.route
        ) {

            SplashScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Home.route
        ) {

            HomeScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.AddTask.route
        ) {

            AddTaskScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Focus.route
        ) {

            FocusModeScreen(
                navController = navController
            )
        }

        composable(
            route =
                Screen.QuickFocusDuration.route
        ) {

            QuickFocusDurationScreen(
                navController = navController
            )
        }

        composable(

            route =
                "${Screen.QuickFocusSession.route}/{duration}",

            arguments = listOf(

                navArgument("duration") {

                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val duration =

                backStackEntry.arguments
                    ?.getInt("duration")
                    ?: 25

            val parentEntry = remember(backStackEntry) {

                navController.getBackStackEntry(
                    Screen.Focus.route
                )
            }

            val focusViewModel:
                    FocusViewModel =

                hiltViewModel(parentEntry)

            QuickFocusSessionScreen(

                navController = navController,

                duration = duration,

                viewModel = focusViewModel
            )
        }

        composable(

            route =
                "${Screen.QuickFocusComplete.route}/{duration}",

            arguments = listOf(

                navArgument("duration") {

                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val duration =

                backStackEntry.arguments
                    ?.getInt("duration")
                    ?: 25

            QuickFocusCompleteScreen(

                navController = navController,

                duration = duration
            )
        }

        composable(
            route = Screen.SelectTask.route
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {

                navController.getBackStackEntry(
                    Screen.Focus.route
                )
            }

            val focusViewModel:
                    FocusViewModel =

                hiltViewModel(parentEntry)

            SelectTaskScreen(

                navController = navController,

                viewModel = focusViewModel
            )
        }

        composable(
            route = Screen.StartFocus.route
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {

                navController.getBackStackEntry(
                    Screen.Focus.route
                )
            }

            val focusViewModel:
                    FocusViewModel =

                hiltViewModel(parentEntry)

            SelectDurationScreen(

                navController = navController,

                viewModel = focusViewModel
            )
        }

        composable(
            route = Screen.ActiveSession.route
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {

                navController.getBackStackEntry(
                    Screen.Focus.route
                )
            }

            val focusViewModel:
                    FocusViewModel =

                hiltViewModel(parentEntry)

            ActiveSessionScreen(

                navController = navController,

                viewModel = focusViewModel
            )
        }

        composable(
            route = Screen.SessionComplete.route
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {

                navController.getBackStackEntry(
                    Screen.Focus.route
                )
            }

            val focusViewModel:
                    FocusViewModel =

                hiltViewModel(parentEntry)

            SessionCompleteScreen(

                navController = navController,

                viewModel = focusViewModel
            )
        }

        composable(
            route = Screen.Progress.route
        ) {

            ProgressScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.History.route
        ) {

            HistoryScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Settings.route
        ) {

            MainSettingsScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Backup.route
        ) {

            BackupScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.About.route
        ) {

            AboutScreen(
                navController = navController
            )
        }
    }
}