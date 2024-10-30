package com.example.attendancecheckandroidtest.ui.theme.components

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.models.Event
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
//
//@Composable
//fun TimelineEventItem(event: Event, onClick: () -> Unit) {
//    val context = LocalContext.current
//    val is24Hour = DateFormat.is24HourFormat(context)
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
//                    startTime?.let {
//                        Text(
//                            text = dateToString(it, is24Hour),
//                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
//                            color = Color.Gray
//                        )
//                    }
//                    endTime?.let {
//                        Text(
//                            text = dateToString(it, is24Hour),
//                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
//                            color = Color.Gray
//                        )
//                    }
//                    Text(
//                        text = event.eventName,
//                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
//                    )
//                }
//
//                Spacer(modifier = Modifier.padding())
//
//                val (statusText, statusColor) = EventStatus(event)
//                Text(
//                    text = statusText,
//                    color = statusColor,
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                )
//            }
//        }
//    }
//}
@Composable
fun TimelineEventItem(event: Event, onClick: () -> Unit) {
    val context = LocalContext.current
    val is24Hour = DateFormat.is24HourFormat(context)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colorScheme.surface) // 테마에 맞는 배경색 적용
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EventImage(event)

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    val startTime = iso8601ToDate(event.eventStartTime)
                    val endTime = iso8601ToDate(event.eventEndTime)

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

                Spacer(modifier = Modifier.padding())

                val (statusText, statusColor) = EventStatus(event)
                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
@Composable
fun EventStatus(event: Event): Pair<String, Color> {
    val now = System.currentTimeMillis()
    val startTime = iso8601ToDate(event.eventStartTime)?.time ?: 0
    val endTime = iso8601ToDate(event.eventEndTime)?.time ?: 0

    return when {
        now in startTime..endTime -> "진행 중" to Color.Green
        now < startTime -> "진행 예정" to Color.Blue
        else -> "종료됨" to Color.Red
    }
}

@Composable
private fun EventImage(event: Event) {
    val imageName = when (event.eventCode) {
        "SCHUSWCU1stAF_OpeningCeremony" -> R.drawable.openingceremony
        "SCHUSWCU1stAF_ProjectPresentationParticipation" -> R.drawable.journalposter
        "SCHUSWCU1stAF_TalkConcertwithGraduatedStudent" -> R.drawable.talkconcert
        "SCHUSWCU1stAF_SWCUGameContest" -> R.drawable.gamepad
        "SCHUSWCU1stAF_IndustryExpertSpecialLecture" -> R.drawable.speciallecture
        "SCHUSWCU1stAF_ClosingCeremony" -> R.drawable.award
        else -> R.drawable.sch_stamp // 기본 이미지
    }

    Box(modifier = Modifier.size(60.dp)) {
        Image(
            painter = painterResource(id = imageName),
            contentDescription = event.eventName,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
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
fun TimelineEventItemPreview() {
    val dummyEvent = Event(
        eventCode = "SCHUSWCU1stAF_ClosingCeremony",
        eventName = "폐회식 및 시상식",
        description = "학술제에 마지막까지 함께해주세요!\n시상식이 끝난 후 이벤트에 모두 참여하신 분께는 경품 추첨의 기회가 주어집니다\n1등의 주인공이 되어보세요!",
        location = "인문과학관 1층 대강당 [6129호]",
        eventStartTime = "2024-10-15T06:00:00.000Z",
        eventEndTime = "2024-11-06T07:30:00.000Z",
        createdAt = "2024-10-18T04:18:16.000Z",
        participants = emptyList() // 참가자 정보
    )

    // TimelineEventItem 호출
    TimelineEventItem(event = dummyEvent, onClick = { /* Do nothing */ })
}
