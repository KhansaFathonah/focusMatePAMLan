package com.example.focusmate.presentation.settingsPage.backup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.settings.BackupFrequencyCard
import com.example.focusmate.presentation.components.settings.BackupHeaderCard
import com.example.focusmate.presentation.components.settings.BackupToggleCard
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun BackupScreen(

    navController: NavController,

    viewModel: BackupViewModel =
        hiltViewModel()
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
    BUTTON ENABLE STATE
    ====================================
    */

    val isBackupEnabled =

        uiState.autoBackup

    /*
    ====================================
    SCREEN
    ====================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        /*
        ====================================
        ROOT CONTAINER
        ====================================
        */

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    BackgroundDark
                )
        ) {

            /*
            ====================================
            MAIN CONTENT
            ====================================
            */

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .padding(

                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
                    .navigationBarsPadding()
            ) {

                /*
                ====================================
                TOP BAR
                ====================================
                */

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

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

                            tint = TextPrimary
                        )
                    }

                    Text(

                        text = "Backup & Sync",

                        color = TextPrimary,

                        fontSize = 21.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(26.dp)
                )

                /*
                ====================================
                LAST BACKUP CARD
                ====================================
                */

                BackupHeaderCard(

                    lastBackup =
                        uiState.lastBackupTime
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                /*
                ====================================
                AUTO BACKUP CARD
                ====================================
                */

                BackupToggleCard(

                    checked =
                        uiState.autoBackup,

                    onCheckedChange = {

                        viewModel.updateAutoBackup(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                /*
                ====================================
                BACKUP FREQUENCY CARD
                ====================================
                */

                BackupFrequencyCard(

                    selectedFrequency =
                        uiState.selectedFrequency,

                    onFrequencySelected = {

                        viewModel.updateFrequency(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(46.dp)
                )

                /*
                ====================================
                BACKUP BUTTON
                ====================================
                */

                Button(

                    onClick = {

                        /*
                        TODO:
                        START BACKUP
                        */
                    },

                    enabled =
                        isBackupEnabled,

                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .fillMaxWidth(0.55f)
                        .height(58.dp),

                    shape =
                        RoundedCornerShape(30.dp),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =

                                if (isBackupEnabled)

                                    ButtonPrimary

                                else

                                    ButtonPrimary.copy(
                                        alpha = 0.42f
                                    ),

                            disabledContainerColor =
                                ButtonPrimary.copy(
                                    alpha = 0.42f
                                )
                        )
                ) {

                    Text(

                        text = "Backup Now",

                        color =

                            if (isBackupEnabled)

                                TextDark

                            else

                                TextDark.copy(
                                    alpha = 0.5f
                                ),

                        fontSize = 17.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                /*
                ====================================
                FOOTER TEXT
                ====================================
                */

                Text(

                    text =
                        "Your productivity data is securely stored",

                    color =
                        TextSecondary.copy(
                            alpha = 0.82f
                        ),

                    fontSize = 15.sp,

                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                )
            }
        }
    }
}