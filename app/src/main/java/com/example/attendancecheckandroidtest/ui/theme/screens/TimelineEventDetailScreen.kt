//package com.example.attendancecheckandroidtest.ui.theme.screens
//
//import android.text.format.DateFormat
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.attendancecheckandroidtest.R
//import com.example.attendancecheckandroidtest.data.models.Event
//import com.example.attendancecheckandroidtest.data.models.Participant
//import com.google.gson.Gson
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//
//@Composable
//fun TimelineEventDetailScreen(
//    eventJson: String,
//    onClose: () -> Unit,
//    onTabSelected: (Int) -> Unit,
//    isTimelineView: Boolean,
//    refreshEvents: () -> Unit,
//)
//
//{
//    val event = Gson().fromJson(eventJson, Event::class.java)
//    val context = LocalContext.current
//    val is24Hour = DateFormat.is24HourFormat(context) // 사용자의 시간 형식 가져오기
//
//    //추가 버전
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .background(Color.White),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text(
//            text = "상세 정보",
//            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp),
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(30.dp),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.Top
//        ) {
//            // 배너 이미지
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .clip(RoundedCornerShape(12.dp)) // 둥근 모서리 적용
//                    .border(
//                        BorderStroke(1.dp, Color.LightGray),
//                        RoundedCornerShape(12.dp)
//                    ) // 테두리 적용
//                    .background(Color.White) // 배경색 추가
//            ) {
//                Image(
//                    painter = painterResource(id = getEventImageResource(event.eventCode)), // 이벤트 코드에 따라 이미지 가져오기
//                    contentDescription = event.eventName,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            // 위치 섹션
//            TimelineEventSectionHeader("위치")
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                shape = RoundedCornerShape(12.dp), // 라운드 모서리
//                border = BorderStroke(1.dp, Color.LightGray), // 테두리
//                colors = CardDefaults.cardColors(containerColor = Color.White)
//            ) {
//                Text(
//                    text = event.location,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier
//                        .padding(16.dp), // 내부 패딩 추가
//                    textAlign = TextAlign.Start // 왼쪽 정렬
//                )
//            }
//
//            // 시간 섹션
//            TimelineEventSectionHeader("시간")
//            val startTime = iso8601ToDate(event.eventStartTime)
//            val endTime = iso8601ToDate(event.eventEndTime)
//
//            startTime?.let {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp)
//                        .background(Color.White.copy(alpha = 1.0f)),
//                    shape = RoundedCornerShape(12.dp), // 라운드 모서리
//                    border = BorderStroke(1.dp, Color.LightGray), // 테두리
//                    colors = CardDefaults.cardColors(containerColor = Color.White)
//                ) {
//                    Text(
//                        text = "시작 | ${dateToString(it, is24Hour = is24Hour)}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier
//                            .padding(16.dp), // 내부 패딩 추가
//                        textAlign = TextAlign.Start // 왼쪽 정렬
//                    )
//                }
//            }
//
//            endTime?.let {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 8.dp)
//                        .background(Color.White.copy(alpha = 1.0f)),
//                    shape = RoundedCornerShape(12.dp), // 라운드 모서리
//                    border = BorderStroke(1.dp, Color.LightGray), // 테두리
//                    colors = CardDefaults.cardColors(containerColor = Color.White)
//                ) {
//                    Text(
//                        text = "종료 | ${dateToString(it, is24Hour = is24Hour)}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier
//                            .padding(16.dp), // 내부 패딩 추가
//                        textAlign = TextAlign.Start // 왼쪽 정렬
//                    )
//                }
//            }
//
//            // 설명 섹션
//            TimelineEventSectionHeader("설명")
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
//                    .background(Color.White.copy(alpha = 1.0f)),
//                shape = RoundedCornerShape(12.dp), // 라운드 모서리
//                border = BorderStroke(1.dp, Color.LightGray), // 테두리
//                colors = CardDefaults.cardColors(containerColor = Color.White)
//            ) {
//                Text(
//                    text = event.description,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier
//                        .padding(16.dp), // 내부 패딩 추가
//                    textAlign = TextAlign.Start // 왼쪽 정렬
//                )
//            }
//
//            // 버튼을 화면 가운데에 배치
//            Button(
//                onClick = {
//                    onClose()
//                    onTabSelected(3)
//                    refreshEvents()
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally) // 수평 가운데 정렬
//                    .padding(top = 30.dp)
//            ) {
//                Text("닫기")
//            }
//        }
//    }
//}
//
//private fun getEventImageResource(eventCode: String): Int {
//    return when (eventCode) {
//        "SCHUSWCU1stAF_OpeningCeremony" -> R.drawable.swcuaf_event_1
//        "SCHUSWCU1stAF_ProjectPresentationParticipation" -> R.drawable.swcuaf_event_2
//        "SCHUSWCU1stAF_TalkConcertwithGraduatedStudent" -> R.drawable.swcuaf_event_4
//        "SCHUSWCU1stAF_SWCUGameContest" -> R.drawable.swcuaf_event_3
//        "SCHUSWCU1stAF_IndustryExpertSpecialLecture" -> R.drawable.swcuaf_event_5
//        "SCHUSWCU1stAF_ClosingCeremony" -> R.drawable.swcuaf_event_6
//        else -> R.drawable.sch_logo // 기본 이미지
//    }
//}
//
//// ISO 8601 문자열을 Kotlin Date 타입으로 변환하는 함수
//private fun iso8601ToDate(iso8601: String): Date? {
//    return try {
//        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
//            timeZone = TimeZone.getTimeZone("UTC") // UTC로 설정
//        }
//        format.parse(iso8601)
//    } catch (e: Exception) {
//        null // 변환 실패 시 null 반환
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
//// SectionHeader 컴포저블
//@Composable
//fun TimelineEventSectionHeader(title: String) {
//    Text(
//        text = title,
//        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//        modifier = Modifier
//            .background(Color.White)
//            .padding(top = 14.dp, bottom = 6.dp, start = 5.dp), // 상단 패딩 추가
//        textAlign = TextAlign.Start // 왼쪽 정렬
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun TimelineEventDetailScreenPreview() {
//    MaterialTheme {
//        // 프리뷰 배경색을 흰색으로 변경
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            // 더미 참가자 목록 생성 및 이벤트 객체 생성
//            val dummyParticipants = listOf(
//                Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "EVENT_1"),
//                Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "EVENT_1")
//            )
//
//            val dummyEvent = Event(
//                eventCode = "EVENT_1",
//                eventName = "개회식",
//                description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식을 함께해보세요!",
//                location = "6129",
//                eventStartTime = "2024-11-05T10:30:00.000Z",
//                eventEndTime = "2024-11-05T11:00:00.000Z",
//                createdAt = "2024-10-25T11:09:00.000Z",
//                participants = dummyParticipants
//            )
//
//            // JSON 직렬화
//            val eventJson = Gson().toJson(dummyEvent)
//
//            // EventDetailScreen 호출
//            EventDetailScreen(eventJson = eventJson) {
//                // 닫기 버튼 클릭 시 동작 정의 (프리뷰에서는 아무 동작도 필요 없음)
//            }
//        }
//    }
//}
//
//

package com.example.attendancecheckandroidtest.ui.theme.screens

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.models.Event
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun TimelineEventDetailScreen(
    eventJson: String,
    onClose: () -> Unit,
    onTabSelected: (Int) -> Unit,
    isTimelineView: Boolean,
    refreshEvents: () -> Unit,
) {
    val event = Gson().fromJson(eventJson, Event::class.java)
    val context = LocalContext.current
    val is24Hour = DateFormat.is24HourFormat(context)

    // 상태 관리
    var isButtonEnabled by remember { mutableStateOf(true) } // 버튼 활성화 상태
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성


    Log.d("TimelineEventDetail", "Event JSON: $eventJson") // 추가된 JSON 로그

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "상세 정보",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // 배너 이미지
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = getEventImageResource(event.eventCode)),
                    contentDescription = event.eventName,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 위치 섹션
            TimelineEventSectionHeader("위치")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 시간 섹션
            TimelineEventSectionHeader("시간")
            val startTime = iso8601ToDate(event.eventStartTime)
            val endTime = iso8601ToDate(event.eventEndTime)

            startTime?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "시작 | ${dateToString(it, is24Hour = is24Hour)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            endTime?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "종료 | ${dateToString(it, is24Hour = is24Hour)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // 설명 섹션
            TimelineEventSectionHeader("설명")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 닫기 버튼을 화면 가운데에 배치
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C)),
                onClick = {
                    if (isButtonEnabled) {
                        Log.d("TimelineEventDetail", "Close button clicked") // 버튼 클릭 로그
                        isButtonEnabled = false // 버튼 비활성화

                        // 로그 추가: 현재 상태 확인
                        Log.d("TimelineEventDetail", "Button state before closing: $isButtonEnabled")

                        // 비동기 네비게이션 처리
                        coroutineScope.launch {
                            onClose() // 이전 화면으로 돌아가고
                            onTabSelected(1) // '일정' 탭으로 변경
                            refreshEvents() // 이벤트 새로 고침

                            // 일정 시간 후 버튼 재활성화
                            delay(10000) // 10초 대기
                            isButtonEnabled = true // 버튼 활성화
                            Log.d("TimelineEventDetail", "Close button re-enabled") // 버튼 재활성화 로그
                        }
                    } else {
                        Log.d("TimelineEventDetail", "Close button was already disabled") // 버튼 비활성화 상태 로그
                    }
                },
                enabled = isButtonEnabled, // 버튼 활성화 상태 설정
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            ) {
                Text("닫기")
            }

        }
    }
}

private fun getEventImageResource(eventCode: String): Int {
    return when (eventCode) {
        "SCHUSWCU1stAF_OpeningCeremony" -> R.drawable.swcuaf_event_1
        "SCHUSWCU1stAF_ProjectPresentationParticipation" -> R.drawable.swcuaf_event_2
        "SCHUSWCU1stAF_TalkConcertwithGraduatedStudent" -> R.drawable.swcuaf_event_4
        "SCHUSWCU1stAF_SWCUGameContest" -> R.drawable.swcuaf_event_3
        "SCHUSWCU1stAF_IndustryExpertSpecialLecture" -> R.drawable.swcuaf_event_5
        "SCHUSWCU1stAF_ClosingCeremony" -> R.drawable.swcuaf_event_6
        else -> R.drawable.sch_logo
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
    format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
    return format.format(date)
}

// SectionHeader 컴포저블
@Composable
fun TimelineEventSectionHeader(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 14.dp, bottom = 6.dp, start = 5.dp),
        textAlign = TextAlign.Start
    )
}

@Preview(showBackground = true)
@Composable
fun TimelineEventDetailScreenPreview() {
    // 더미 이벤트 데이터 생성
    val dummyEvent = Event(
        eventCode = "EVENT_1",
        eventName = "개회식",
        description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식을 함께해보세요!",
        location = "6129",
        eventStartTime = "2024-11-05T10:30:00.000Z",
        eventEndTime = "2024-11-05T11:00:00.000Z",
        createdAt = "2024-10-25T11:09:00.000Z",
        participants = emptyList() // 더미 참가자 목록
    )

    val eventJson = Gson().toJson(dummyEvent)

    // EventDetailScreen 호출
    TimelineEventDetailScreen(
        eventJson = eventJson,
        onClose = { /* Handle close action */ },
        onTabSelected = { /* Handle tab selection */ },
        isTimelineView = false,
        refreshEvents = { /* Refresh events logic */ }
    )
}

