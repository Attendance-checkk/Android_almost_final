package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.data.models.Banner
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.data.models.Participant
import com.example.attendancecheckandroidtest.ui.theme.components.EventItem
import com.example.attendancecheckandroidtest.ui.theme.components.BannerItem
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import kotlinx.coroutines.delay

// 배너 목록 정의
val Banners = listOf(
    Banner("앱을 처음 사용하시나요?", "swcuaf_banner_1", "https://potent-barnacle-025.notion.site/123c07204d2980beb56fededabe0d6a8?pvs=4"),
    Banner("이벤트 경품을 확인해보세요!", "swcuaf_banner_2", "https://potent-barnacle-025.notion.site/123c07204d2980d1bed9d435f2b48ed3?pvs=4"),
    Banner("궁금하신 사항이 있으신가요?", "swcuaf_banner_3", "https://potent-barnacle-025.notion.site/FAQ-116c07204d29805a8418d9a37bf330a2?pvs=4")
)

// 홈 화면을 표시하는 Composable 함수
@Composable
fun HomeScreen(events: List<Event>, context: Context, navController: NavController, onEventSelected: (Event) -> Unit) {
    val completedStamps = events.count { it.participants?.isNotEmpty() ?: false }
    val progress = completedStamps * 20

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "체크리스트",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )

        TabViewBanners { url ->
            openLinkInBrowser(context, url)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = if (progress == 100) "🥳 스탬프 모으기 완료!" else "스탬프를 모아서 경품에 도전해보세요!",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )

                LinearProgressIndicator(
                    progress = { completedStamps / 5f },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    color = Color.Blue,
                )

                Text(
                    text = "스탬프 모으기",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            items(events) { event ->
                EventItem(event) {
                    // 이벤트 클릭 시 EventDetailScreen으로 이벤트 객체 전체를 JSON 형태로 전달
                    val eventJson = Gson().toJson(event) // JSON 직렬화
                    navController.navigate("eventDetail/$eventJson") // JSON 문자열 전달
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


// 배너를 표시하는 탭 뷰 함수
@Composable
fun TabViewBanners(onBannerClick: (String) -> Unit) {
    val banners = Banners

    if (banners.isNotEmpty()) {
        val pagerState = rememberPagerState(
            pageCount = { banners.size }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(vertical = 8.dp)
        ) { index ->
            val banner = banners[index]
            BannerItem(banner) { url ->
                onBannerClick(url)
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { currentPage ->
                var lastPage = currentPage
                while (true) {
                    // 사용자 입력이 발생했는지 확인
                    if (pagerState.currentPage != lastPage) {
                        lastPage = pagerState.currentPage
                        // 지연 시간 초기화
                        delay(7000)
                    } else {
                        delay(7000)
                        val nextPage = (pagerState.currentPage + 1) % banners.size
                        Log.d("HorizontalPager", "Moving to page: $nextPage")
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    } else {
        Text(text = "배너가 없습니다.", modifier = Modifier.padding(16.dp))
    }
}

// 브라우저에서 URL 열기
fun openLinkInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyParticipants = listOf(
        Participant(id = 1, createdAt = "2024-10-24T06:51:14.000Z", userId = 71, eventCode = "EVENT_1"),
        Participant(id = 2, createdAt = "2024-10-24T06:51:55.000Z", userId = 72, eventCode = "EVENT_1")
    )

    val events = listOf(
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

    // HomeScreen 호출
    HomeScreen(events = events, context = LocalContext.current, navController = navController, onEventSelected = { event ->
        Log.d("HomeScreenPreview", "Selected event: ${event.eventName}")
    })
}