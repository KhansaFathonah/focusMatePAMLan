package com.example.focusmate.presentation.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.domain.model.Task
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextMuted
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TaskCard(

    task: Task,

    onEditClick: (Task) -> Unit,

    onDeleteClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    DROPDOWN STATE
    ====================================
    */

    var expanded by remember {

        mutableStateOf(false)
    }

    /*
    ====================================
    DIALOG STATE
    ====================================
    */

    var showDeleteDialog by remember {

        mutableStateOf(false)
    }

    var showEditDialog by remember {

        mutableStateOf(false)
    }

    /*
    ====================================
    CONTEXT
    ====================================
    */

    val context = LocalContext.current

    /*
    ====================================
    CALENDAR
    ====================================
    */

    val calendar = remember {

        Calendar.getInstance()
    }

    /*
    ====================================
    EDIT STATE
    ====================================
    */

    var editedTitle by remember(task.title) {

        mutableStateOf(task.title)
    }

    var editedDeadline by remember(task.deadline) {

        mutableStateOf(task.deadline)
    }

    /*
    ====================================
    CARD
    ====================================
    */

    Card(

        modifier = modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                Color(0xFFF4F4F4)
        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 10.dp
        )
    ) {

        Box {

            /*
            ====================================
            MAIN CONTENT
            ====================================
            */

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {

                /*
                ====================================
                TITLE
                ====================================
                */

                Text(

                    text = task.title,

                    color = TextDark,

                    fontSize = 19.sp,

                    lineHeight = 24.sp,

                    fontWeight =
                        FontWeight.Bold,

                    maxLines = 1,

                    overflow =
                        TextOverflow.Ellipsis,

                    modifier = Modifier
                        .padding(end = 38.dp)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                /*
                ====================================
                DEADLINE
                ====================================
                */

                Text(

                    text = task.deadline,

                    color = TextMuted,

                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                /*
                ====================================
                BOTTOM SECTION
                ====================================
                */

                Row(

                    modifier = Modifier
                        .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    /*
                    ====================================
                    STATUS BADGE
                    ====================================
                    */

                    StatusBadge(
                        status = task.status
                    )

                    /*
                    ====================================
                    FOCUS TIME
                    ====================================
                    */

                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(

                            imageVector =
                                Icons.Outlined.AccessTime,

                            contentDescription =
                                "Focus Time",

                            tint = TextDark
                        )

                        Spacer(
                            modifier = Modifier.width(5.dp)
                        )

                        Text(

                            text =
                                "Focused: ${task.focusMinutes} min",

                            color = TextDark,

                            fontSize = 14.sp
                        )
                    }
                }
            }

            /*
            ====================================
            MORE BUTTON
            ====================================
            */

            Box(

                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {

                IconButton(

                    onClick = {

                        expanded = true
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.MoreVert,

                        contentDescription =
                            "More Options",

                        tint =
                            Color(0xFF3A4763)
                    )
                }

                /*
                ====================================
                DROPDOWN MENU
                ====================================
                */

                DropdownMenu(

                    expanded = expanded,

                    onDismissRequest = {

                        expanded = false
                    },

                    modifier = Modifier
                        .background(

                            color =
                                Color(0xEE25345A),

                            shape =
                                RoundedCornerShape(18.dp)
                        )
                ) {

                    /*
                    ====================================
                    EDIT
                    ====================================
                    */

                    DropdownMenuItem(

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Outlined.Edit,

                                contentDescription = null,

                                tint = Color.White
                            )
                        },

                        text = {

                            Text(

                                text = "Edit",

                                color = Color.White
                            )
                        },

                        onClick = {

                            expanded = false

                            showEditDialog = true
                        }
                    )

                    HorizontalDivider(

                        color =
                            Color.White.copy(
                                alpha = 0.12f
                            )
                    )

                    /*
                    ====================================
                    DELETE
                    ====================================
                    */

                    DropdownMenuItem(

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Outlined.DeleteOutline,

                                contentDescription = null,

                                tint =
                                    Color(0xFFFF9A9A)
                            )
                        },

                        text = {

                            Text(

                                text = "Delete",

                                color =
                                    Color(0xFFFF9A9A)
                            )
                        },

                        onClick = {

                            expanded = false

                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }

    /*
    ====================================
    DELETE DIALOG
    ====================================
    */

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {

                showDeleteDialog = false
            },

            shape = RoundedCornerShape(24.dp),

            containerColor =
                Color(0xFF1E2A47),

            title = {

                Text(

                    text = "Delete Task?",

                    color = Color.White,

                    fontWeight =
                        FontWeight.Bold
                )
            },

            text = {

                Text(

                    text =
                        "This task will be permanently deleted.",

                    color =
                        Color(0xFFD5D9E3)
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        showDeleteDialog = false

                        onDeleteClick()
                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFFE05656)
                        ),

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Text(
                        text = "Delete"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showDeleteDialog = false
                    }
                ) {

                    Text(

                        text = "Cancel",

                        color = Color.White
                    )
                }
            }
        )
    }

    /*
    ====================================
    EDIT DIALOG
    ====================================
    */

    if (showEditDialog) {

        AlertDialog(

            onDismissRequest = {

                showEditDialog = false
            },

            shape = RoundedCornerShape(24.dp),

            containerColor =
                Color(0xFF1E2A47),

            title = {

                Text(

                    text = "Edit Task",

                    color = Color.White,

                    fontWeight =
                        FontWeight.Bold
                )
            },

            text = {

                Column {

                    /*
                    ====================================
                    TITLE INPUT
                    ====================================
                    */

                    OutlinedTextField(

                        value = editedTitle,

                        onValueChange = {

                            editedTitle = it
                        },

                        label = {

                            Text("Task Title")
                        },

                        singleLine = true,

                        modifier = Modifier
                            .fillMaxWidth(),

                        colors =
                            OutlinedTextFieldDefaults.colors(

                                focusedContainerColor =
                                    Color(0xFF2A395C),

                                unfocusedContainerColor =
                                    Color(0xFF2A395C),

                                focusedBorderColor =
                                    Color(0xFFB1C4FF),

                                unfocusedBorderColor =
                                    Color(0xFF7282A9),

                                focusedTextColor =
                                    Color.White,

                                unfocusedTextColor =
                                    Color.White,

                                focusedLabelColor =
                                    Color(0xFFB1C4FF),

                                unfocusedLabelColor =
                                    Color.LightGray
                            )
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    /*
                    ====================================
                    DEADLINE PICKER
                    ====================================
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

                                DatePickerDialog(

                                    context,

                                    { _, year, month, dayOfMonth ->

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

                                                editedDeadline =

                                                    formatter.format(
                                                        calendar.time
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

                                    calendar.get(Calendar.YEAR),

                                    calendar.get(Calendar.MONTH),

                                    calendar.get(Calendar.DAY_OF_MONTH)

                                ).show()
                            }

                    ) {

                        OutlinedTextField(

                            value = editedDeadline,

                            onValueChange = {},

                            enabled = false,

                            label = {

                                Text("Deadline")
                            },

                            singleLine = true,

                            modifier = Modifier
                                .fillMaxWidth(),

                            colors =
                                OutlinedTextFieldDefaults.colors(

                                    disabledContainerColor =
                                        Color(0xFF2A395C),

                                    disabledBorderColor =
                                        Color(0xFF7282A9),

                                    disabledTextColor =
                                        Color.White,

                                    disabledLabelColor =
                                        Color.LightGray
                                )
                        )
                    }
                }
            },

            confirmButton = {

                Button(

                    onClick = {

                        showEditDialog = false

                        onEditClick(

                            task.copy(

                                title = editedTitle,

                                deadline = editedDeadline
                            )
                        )
                    },

                    enabled =
                        editedTitle.isNotBlank()
                                &&
                                editedDeadline.isNotBlank(),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF6D8DFF),

                            disabledContainerColor =
                                Color(0xFF4F5D82)
                        ),

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Text(
                        text = "Save"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showEditDialog = false
                    }
                ) {

                    Text(

                        text = "Cancel",

                        color = Color.White
                    )
                }
            }
        )
    }
}