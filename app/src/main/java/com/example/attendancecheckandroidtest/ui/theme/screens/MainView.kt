package com.example.attendancecheckandroidtest.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.data.models.NotificationManager
import com.example.attendancecheckandroidtest.data.network.ApiService
import okhttp3.OkHttpClient

@Composable
fun MainView(
    navController: NavController,
    selectedTabIndex: Int,
    isTimelineView: Boolean,
    onTabSelected: (Int) -> Unit,
    onTimelineViewChange: (Boolean) -> Unit,
    refreshEvents: () -> Unit,
    isNotificationEnabled: Boolean // 알림 수신 여부 파라미터 추가
) {
    val tabs = listOf("QR", "지도", "홈", "일정", "메뉴")
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val apiService = ApiService(OkHttpClient(), context)
    val notificationManager = NotificationManager(context)

    // 이벤트를 새로 고치는 함수
    fun fetchEvents() {
        apiService.fetchEventList(context, onSuccess = { fetchedEvents ->
            events = fetchedEvents
            isLoading = false
        }, onError = { error ->
            errorMessage = error
            isLoading = false
        })
    }

    // 초기 이벤트 목록을 가져오는 함수 호출
    LaunchedEffect(Unit) {
        fetchEvents()
        // 알림 권한 확인
        if (isNotificationEnabled) {
            notificationManager.scheduleNotificationList() // 알림 예약
        }
    }

    // selectedTabIndex가 변경될 때마다 fetchEvents 호출
    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 2 || selectedTabIndex == 3) {
            fetchEvents()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = when (tab) {
                                    "QR" -> R.drawable.ic_qr
                                    "지도" -> R.drawable.ic_map
                                    "홈" -> R.drawable.ic_home
                                    "일정" -> R.drawable.ic_calendar
                                    "메뉴" -> R.drawable.ic_menu
                                    else -> R.drawable.ic_home // 기본 아이콘 설정
                                }),
                                contentDescription = tab
                            )
                        },
                        label = { Text(tab) },
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (selectedTabIndex) {
                0 -> QRScreen(navController)
                1 -> MapScreen(navController)
                2 -> {
                    if (isLoading) {
                        Text(
                            "이벤트를 불러오는 중...",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    } else if (errorMessage.isNotEmpty()) {
                        Text(
                            "오류 발생: $errorMessage",
                            color = Color.Red,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        HomeScreen(
                            events = events,
                            context = context,
                            navController = navController
                        ) { event ->
                            Log.d("HomeView", "Selected event: ${event.eventName}")
                        }
                    }
                }
                3 -> CalendarScreen(events, navController, isTimelineView) { newValue ->
                    fetchEvents()
                    onTimelineViewChange(newValue) // 상태 업데이트
                }
                4 -> MenuScreen(
                    navController = navController,
                    deleteAccount = {
                        // 계정 삭제 처리 로직 추가
                    },
                    onTabSelected = onTabSelected,
                    isNotificationEnabled = isNotificationEnabled // 알림 수신 여부 전달
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // MyApp() 대신 직접 호출
    val navController = rememberNavController()
    MainView(
        navController = navController,
        selectedTabIndex = 2,
        isTimelineView = false,
        onTabSelected = {},
        onTimelineViewChange = {},
        refreshEvents = {},
        isNotificationEnabled = true // 기본값 설정
    )
}