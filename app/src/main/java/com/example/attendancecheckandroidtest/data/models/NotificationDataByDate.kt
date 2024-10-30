package com.example.attendancecheckandroidtest.data.models

import com.example.attendancecheckandroidtest.ui.theme.components.createDateFromString
import java.util.Date

data class NotificationDataByDate(
    val titleString: String,
    val bodyString: String,
    val date: Date
)