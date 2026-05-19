package com.example.focusmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.focusmate.presentation.navigation.BottomNavItem
import com.example.focusmate.presentation.navigation.Screen

@Composable
fun BottomNavbar(

    navController: NavController,

    modifier: Modifier = Modifier
) {

    /*
    ========================================
    CURRENT ROUTE
    ========================================
    */

    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    /*
    ========================================
    NAV ITEMS
    ========================================
    */

    val items = listOf(

        BottomNavItem(

            title = "Home",

            route = Screen.Home.route,

            icon = Icons.Outlined.Home
        ),

        BottomNavItem(

            title = "Focus",

            route = Screen.Focus.route,

            icon = Icons.Filled.TrackChanges
        ),

        BottomNavItem(

            title = "Progress",

            route = Screen.Progress.route,

            icon = Icons.AutoMirrored.Filled.ShowChart
        ),

        BottomNavItem(

            title = "Settings",

            route = Screen.Settings.route,

            icon = Icons.Outlined.Settings
        )
    )

    /*
    ========================================
    NAVBAR
    ========================================
    */

    NavigationBar(

        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(64.dp)
            .shadow(

                elevation = 14.dp,

                shape =
                    RoundedCornerShape(32.dp),

                clip = false
            )
            .clip(
                RoundedCornerShape(32.dp)
            )
            .background(

                brush = Brush.linearGradient(

                    colors = listOf(

                        /*
                        LEFT
                        */

                        Color(0xFF465A93),

                        /*
                        CENTER
                        */

                        Color(0xFF364775),

                        /*
                        RIGHT
                        */

                        Color(0xFF2B3557)
                    )
                )
            )
            .padding(

                horizontal = 10.dp,

                vertical = 2.dp
            ),

        /*
        ====================================
        TRANSPARENT CONTAINER
        ====================================
        */

        containerColor =
            Color.Transparent,

        tonalElevation = 0.dp
    ) {

        items.forEach { item ->

            /*
            ====================================
            SELECTED STATE
            ====================================
            */

            val selected =
                currentRoute == item.route

            NavigationBarItem(

                selected = selected,

                onClick = {

                    /*
                    ============================
                    AVOID MULTIPLE STACK
                    ============================
                    */

                    if (currentRoute != item.route) {

                        navController.navigate(
                            item.route
                        ) {

                            launchSingleTop = true

                            restoreState = true

                            popUpTo(
                                navController.graph.startDestinationId
                            ) {

                                saveState = true
                            }
                        }
                    }
                },

                /*
                ====================================
                ICON
                ====================================
                */

                icon = {

                    Box(
                        modifier = Modifier.size(22.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(

                            imageVector =
                                item.icon,

                            contentDescription =
                                item.title,

                            modifier = Modifier.size(18.dp)
                        )
                    }
                },

                /*
                ====================================
                LABEL
                ====================================
                */

                label = {

                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                },

                /*
                ====================================
                COLORS
                ====================================
                */

                colors =
                    NavigationBarItemDefaults.colors(

                        /*
                        ACTIVE
                        */

                        selectedIconColor =
                            Color.White,

                        selectedTextColor =
                            Color.White,

                        /*
                        INACTIVE
                        */

                        unselectedIconColor =
                            Color(0xFFA3ADC2),

                        unselectedTextColor =
                            Color(0xFFA3ADC2),

                        /*
                        REMOVE DEFAULT INDICATOR
                        */

                        indicatorColor =
                            Color.Transparent
                    )
            )
        }
    }
}
