package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.ui.theme.components.TimelineEventItem

@Composable
fun TimelineEventScreen(events: List<Event>, onEventSelected: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Padding for content
    ) {
        items(events) { event ->
            TimelineEventItem(event = event) {
                onEventSelected(event)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimelineEventScreenPreview() {
    // 더미 이벤트 목록 생성
    val dummyEvents = listOf(
        Event(
            eventCode = "SCHUSWCU1stAF_ClosingCeremony",
            eventName = "폐회식 및 시상식",
            description = "학술제에 마지막까지 함께해주세요!\n시상식이 끝난 후 이벤트에 모두 참여하신 분께는 경품 추첨의 기회가 주어집니다\n1등의 주인공이 되어보세요!",
            location = "인문과학관 1층 대강당 [6129호]",
            eventStartTime = "2024-11-06T06:00:00.000Z",
            eventEndTime = "2024-11-06T07:30:00.000Z",
            createdAt = "2024-10-18T04:18:16.000Z",
            participants = emptyList() // 참가자 정보
        ),
        Event(
            eventCode = "1stAcademicFesival,OpeningCeremony,SWConvergenceCollege,SoonchunhyangUniversity",
            eventName = "개회식",
            description = "SW융합대학의 첫 학술제, 그 시작을 알리는 개회식에 참여하세요!",
            location = "인문과학관 1층 대강당 [6129호]",
            eventStartTime = "2024-11-05T01:30:00.000Z",
            eventEndTime = "2024-11-05T02:00:00.000Z",
            createdAt = "2024-10-18T04:18:59.000Z",
            participants = emptyList() // 참가자 정보
        )
    )

    // TimelineEventScreen 호출
    TimelineEventScreen(events = dummyEvents) { selectedEvent ->
        // 선택된 이벤트 처리 (예: EventDetailScreen으로 이동하는 로직)
    }
}