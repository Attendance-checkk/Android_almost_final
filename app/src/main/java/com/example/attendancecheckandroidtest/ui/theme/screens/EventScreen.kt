package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.data.models.Participant
import com.example.attendancecheckandroidtest.ui.theme.components.EventItem

// 이벤트 화면을 표시하는 Composable 함수
@Composable
fun EventScreen(events: List<Event>, onEventSelected: (Event) -> Unit) {
    val completedStamps = events.count { it.participants?.isNotEmpty() ?: false }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Padding for content
    ) {
        item {
            LinearProgressIndicator(
                progress = { completedStamps / 5f },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                color = Color.Blue,
            )

            Text(
                text = "학술제 참여하고 경품 받자!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(events) { event ->
            EventItem(event) {
                onEventSelected(event)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


// EventScreenPreview 수정
@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    // 더미 참가자 목록 생성
    val participants = listOf(
        Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "EVENT_1"),
        Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "EVENT_1")
    )

    // 더미 이벤트 목록 생성
    val dummyEvents = listOf(
        Event(
            eventCode = "EVENT_1",
            eventName = "학술제 본선 관람",
            description = "SW융합대학 학술제 본선에 진출한 학생들의 포스터를 관람해보세요.",
            location = "인문과학관 1층",
            eventStartTime = "2024-11-05T00:00:00.000Z",
            eventEndTime = "2024-11-06T06:00:00.000Z",
            createdAt = "2024-10-18T04:18:16.000Z",
            participants = participants
        ),
        // 다른 이벤트들...
    )

    // EventScreen 호출
    EventScreen(events = dummyEvents) {
        // 선택된 이벤트 처리 (예: EventDetailScreen으로 이동하는 로직)
    }
}