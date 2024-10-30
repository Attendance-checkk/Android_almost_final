package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.data.models.Participant

@Composable
fun CalendarScreen(events: List<Event>, navController: NavController, isTimelineView: Boolean, onTimelineViewChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isTimelineView) "타임라인" else "캘린더",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp),
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Button(
            onClick = { onTimelineViewChange(!isTimelineView) }, // 상태 전환
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = if (isTimelineView) "캘린더 보기" else "타임라인 보기")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isTimelineView) Arrangement.Top else Arrangement.Center // 수정된 부분
        ) {
            // 현재 표시할 뷰 결정
            if (isTimelineView) {
                TimelineView(events, navController)
            } else {
                TimestampView() // 타임스탬프 뷰 표시
            }
        }
    }
}


@Composable
@Preview(showBackground = true) // 미리보기 설정
fun CalendarScreenPreview() {
    // 더미 이벤트 생성
    val dummyParticipants = listOf(
        Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "EVENT_1"),
        Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "EVENT_1")
    )

    val dummyEvents = listOf(
        Event(
            eventCode = "EVENT_1",
            eventName = "학술제 본선 관람",
            description = "SW융합대학 학술제 본선에 진출한 학생들의 포스터를 관람해보세요.",
            location = "인문과학관 1층",
            eventStartTime = "2024-11-05T00:00:00.000Z",
            eventEndTime = "2024-11-06T06:00:00.000Z",
            createdAt = "2024-10-18T04:18:16.000Z",
            participants = dummyParticipants
        ),
        Event(
            eventCode = "EVENT_2",
            eventName = "개회식",
            description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식을 함께해보세요!",
            location = "인문과학관 1층 대강당 [6129호]",
            eventStartTime = "2024-11-05T01:30:00.000Z",
            eventEndTime = "2024-11-05T02:00:00.000Z",
            createdAt = "2024-10-18T04:18:59.000Z",
            participants = emptyList()
        )
    )

    // Mock NavController 생성
    val navController = rememberNavController()

    // isTimelineView 추가
    val isTimelineView = remember { mutableStateOf(false) }

    // CalendarScreen에 더미 이벤트 리스트와 isTimelineView 상태 전달
    CalendarScreen(events = dummyEvents, navController = navController, isTimelineView = isTimelineView.value) { newValue ->
        isTimelineView.value = newValue // 상태 업데이트
    }
}