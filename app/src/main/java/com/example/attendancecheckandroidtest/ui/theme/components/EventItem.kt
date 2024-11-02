//package com.example.attendancecheckandroidtest.ui.theme.components
//
//import android.text.format.DateFormat
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.attendancecheckandroidtest.R
//import com.example.attendancecheckandroidtest.data.models.Event
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.attendancecheckandroidtest.data.models.Participant
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//
//@Composable
//fun EventItem(event: Event, onClick: () -> Unit) {
//    val context = LocalContext.current
//    val is24Hour = DateFormat.is24HourFormat(context) // 사용자의 시간 형식 가져오기
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 2.dp)
//            .clickable { onClick() },
//        shape = RoundedCornerShape(15.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(1.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
//                .background(color = Color.White)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                EventImage(event)
//
//                Spacer(modifier = Modifier.width(10.dp))
//
//                Column(modifier = Modifier.weight(1f)) {
//                    val startTime = iso8601ToDate(event.eventStartTime)
//                    val endTime = iso8601ToDate(event.eventEndTime)
//
//                    // Null 체크 추가
//                    if (startTime != null) {
//                        Text(
//                            text = dateToString(startTime, is24Hour),
//                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
//                            color = Color.Gray
//                        )
//                    }
//                    if (endTime != null) {
//                        Text(
//                            text = dateToString(endTime, is24Hour),
//                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
//                            color = Color.Gray
//                        )
//                    }
//                    Text(
//                        text = event.eventName,
//                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//private fun EventImage(event: Event) {
//    val imageName = when (event.eventCode) {
//        "SCHUSWCU1stAF_OpeningCeremony" -> "openingceremony"
//        "SCHUSWCU1stAF_ProjectPresentationParticipation" -> "journalposter"
//        "SCHUSWCU1stAF_TalkConcertwithGraduatedStudent" -> "talkconcert"
//        "SCHUSWCU1stAF_SWCUGameContest" -> "gamepad"
//        "SCHUSWCU1stAF_IndustryExpertSpecialLecture" -> "speciallecture"
//        else -> "default_image" // 기본 이미지 이름
//    }
//
//    val context = LocalContext.current
//    val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
//
//    Box(modifier = Modifier.size(60.dp)) {
//        Image(
//            painter = if (resId != 0) painterResource(id = resId) else painterResource(id = R.drawable.sch_stamp), // 기본 이미지
//            contentDescription = event.eventName,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(8.dp))
//                .graphicsLayer(alpha = if (event.participants?.isNotEmpty() == true) 0.4f else 1.0f)
//        )
//
//        // 로고 표시
//        if (event.participants?.isNotEmpty() == true) {
//            Image(
//                painter = painterResource(id = R.drawable.sch_stamp),
//                contentDescription = "SCH 로고",
//                modifier = Modifier
//                    .size(60.dp)
//                    .align(Alignment.Center) // Box 내에서 중앙에 위치
//            )
//        }
//    }
//}
//
//// ISO 8601 문자열을 Kotlin Date 타입으로 변환하는 함수
//private fun iso8601ToDate(iso8601: String): Date? {
//    return try {
//        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
//            timeZone = TimeZone.getTimeZone("UTC")
//        }
//        format.parse(iso8601)
//    } catch (e: Exception) {
//        null
//    }
//}
//
//// Kotlin Date 타입을 12시간제 또는 24시간제 형식의 문자열로 변환하는 함수
//private fun dateToString(date: Date, is24Hour: Boolean): String {
//    val format: SimpleDateFormat = if (is24Hour) {
//        SimpleDateFormat("MM월 d일(E) HH:mm", Locale.getDefault())
//    } else {
//        SimpleDateFormat("MM월 d일(E) a h:mm", Locale.getDefault())
//    }
//    format.timeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정
//    return format.format(date)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun EventItemPreview() {
//    val dummyParticipants = listOf(
//        Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "SCHUSWCU1stAF_OpeningCeremony"),
//        Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "SCHUSWCU1stAF_OpeningCeremony")
//    )
//
//    val dummyEvent = Event(
//        eventCode = "SCHUSWCU1stAF_OpeningCeremony",
//        eventName = "개회식",
//        description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식을 함께해보세요!",
//        location = "6129",
//        eventStartTime = "2024-11-05T10:30:00.000Z",
//        eventEndTime = "2024-11-05T11:00:00.000Z",
//        createdAt = "2024-10-25T11:09:00.000Z",
//        participants = dummyParticipants
//    )
//
//    EventItem(event = dummyEvent, onClick = { /* Do nothing */ })
//}
package com.example.attendancecheckandroidtest.ui.theme.components

import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.models.Event
import androidx.compose.ui.tooling.preview.Preview
import com.example.attendancecheckandroidtest.data.models.Participant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    val context = LocalContext.current
    val is24Hour = DateFormat.is24HourFormat(context)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colorScheme.surface) // 다크 모드에 맞는 배경색
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EventImage(event)

                Spacer(modifier = Modifier.width(15.dp))

                Column(modifier = Modifier.weight(1f)) {
                    val startTime = iso8601ToDate(event.eventStartTime)
                    val endTime = iso8601ToDate(event.eventEndTime)

                    // Null 체크 추가
                    startTime?.let {
                        Text(
                            text = dateToString(it, is24Hour),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurface // 텍스트 색상 적용
                        )
                    }
                    endTime?.let {
                        Text(
                            text = dateToString(it, is24Hour),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurface // 텍스트 색상 적용
                        )
                    }
                    Text(
                        text = event.eventName,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) // 텍스트 색상 적용
                    )
                }

                Spacer(modifier = Modifier)

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "오른쪽 꺾쇠",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun EventImage(event: Event) {
    val imageName = when (event.eventCode) {
        "SCHUSWCU1stAF_OpeningCeremony" -> "openingceremony"
        "SCHUSWCU1stAF_ProjectPresentationParticipation" -> "journalposter"
        "SCHUSWCU1stAF_TalkConcertwithGraduatedStudent" -> "talkconcert"
        "SCHUSWCU1stAF_SWCUGameContest" -> "gamepad"
        "SCHUSWCU1stAF_IndustryExpertSpecialLecture" -> "speciallecture"
        else -> "default_image" // 기본 이미지 이름
    }

    val context = LocalContext.current
    val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Box(modifier = Modifier
        .size(60.dp)
        .border(
            BorderStroke(1.dp, if (isSystemInDarkTheme()) Color.Transparent else Color.LightGray),
            shape = RoundedCornerShape(8.dp)
        )
        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = if (resId != 0) painterResource(id = resId) else painterResource(id = R.drawable.sch_stamp), // 기본 이미지
            contentDescription = event.eventName,
            modifier = Modifier
                .size((60 * 0.8).dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .align(Alignment.Center)
                .graphicsLayer(alpha = if (event.participants?.isNotEmpty() == true) 0.0f else 1.0f)
        )

        // 로고 표시
        if (event.participants?.isNotEmpty() == true) {
            Image(
                painter = painterResource(id = R.drawable.sch_stamp),
                contentDescription = "SCH 로고",
                modifier = Modifier
                    .size((60 * 0.8).dp)
                    .align(Alignment.Center) // Box 내에서 중앙에 위치
            )
        }
    }
}

// ISO 8601 문자열을 Kotlin Date 타입으로 변환하는 함수
private fun iso8601ToDate(iso8601: String): Date? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        format.parse(iso8601)
    } catch (e: Exception) {
        null
    }
}

// Kotlin Date 타입을 12시간제 또는 24시간제 형식의 문자열로 변환하는 함수
private fun dateToString(date: Date, is24Hour: Boolean): String {
    val format: SimpleDateFormat = if (is24Hour) {
        SimpleDateFormat("MM월 d일(E) HH:mm", Locale.getDefault())
    } else {
        SimpleDateFormat("MM월 d일(E) a h:mm", Locale.getDefault())
    }
    format.timeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정
    return format.format(date)
}

@Preview(showBackground = true)
@Composable
fun EventItemPreview() {
    val dummyParticipants = listOf(
        Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "SCHUSWCU1stAF_OpeningCeremony"),
        Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "SCHUSWCU1stAF_OpeningCeremony")
    )

    val dummyEvent = Event(
        eventCode = "SCHUSWCU1stAF_OpeningCeremony",
        eventName = "개회식",
        description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식을 함께해보세요!",
        location = "6129",
        eventStartTime = "2024-11-05T10:30:00.000Z",
        eventEndTime = "2024-11-05T11:00:00.000Z",
        createdAt = "2024-10-25T11:09:00.000Z",
        participants = dummyParticipants
    )

    EventItem(event = dummyEvent, onClick = { /* Do nothing */ })
}
