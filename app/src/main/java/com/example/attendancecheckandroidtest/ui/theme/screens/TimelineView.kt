package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.ui.theme.components.TimelineEventItem
import com.google.gson.Gson

// TimelineView 정의
@Composable
fun TimelineView(events: List<Event>, navController: NavController) {
    // 새로운 이벤트를 생성
    val closingCeremonyEvent = createClosingCeremonyEvent()

    // 외부 이벤트와 합쳐서 새로운 리스트 생성
    val timelineEvents = events + closingCeremonyEvent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // 내부 여백 설정
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 정렬
        verticalArrangement = Arrangement.Top // 상단 정렬
    ) {
        // 이벤트 리스트 표시
        LazyColumn {
            items(timelineEvents) { event ->
                TimelineEventItem(event = event) {
                    // 이벤트 클릭 시 TimelineEventDetailScreen으로 이동
                    val eventJson = Gson().toJson(event) // JSON 직렬화
                    navController.navigate("timelineEventDetail/$eventJson") // JSON 문자열 전달
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// 새로운 이벤트를 생성하는 함수
fun createClosingCeremonyEvent(): Event {
    return Event(
        eventCode = "SCHUSWCU1stAF_ClosingCeremony",
        eventName = "폐회식 및 시상식",
        description = "학술제에 마지막까지 함께해주세요!\n시상식이 끝난 후 이벤트에 모두 참여하신 분께는 경품 추첨의 기회가 주어집니다\n1등의 주인공이 되어보세요!",
        location = "인문과학관 1층 대강당 [6129호]",
        eventStartTime = "2024-11-06T06:00:00.000Z",
        eventEndTime = "2024-11-06T07:30:00.000Z",
        createdAt = "2024-10-18T04:18:16.000Z",
        participants = emptyList() // 참가자 정보
    )
}