package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
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
    isNotificationEnabled: Boolean
) {
    val tabs = listOf("QR", "일정", "홈", "지도", "메뉴")
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val apiService = ApiService( context,client2 = OkHttpClient())
//    val apiService = ApiService(context)
    val notificationManager = NotificationManager(context)
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val accessToken = sharedPreferences.getString("access_token", null)

    var showAlertDialog by remember { mutableStateOf(false) }
    var alertDialogTitle by remember { mutableStateOf("") }
    var alertDialogMessage by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }



    // 이벤트를 새로 고치는 함수
    fun fetchEvents() {
        apiService.fetchEventList(context, onSuccess = { fetchedEvents ->
            events = fetchedEvents
            isLoading = false
        }, onError = { error ->
            errorMessage = error
            isLoading = false
            if (error.isNotEmpty()){
                if (accessToken != null) {
                    if (errorMessage.contains("401")) {
                        with(sharedPreferences.edit()) {
                            putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                            remove("access_token") // 액세스 토큰 삭제
                            apply()
                        }
                        navController.navigate("TokenOver")
                    } else if (errorMessage.contains("409")) {
                        with(sharedPreferences.edit()) {
                            putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                            remove("access_token") // 액세스 토큰 삭제
                            apply()
                        }
                        navController.navigate("DeleteByAdmin")
                    } else if (errorMessage.contains("412")) {
                        with(sharedPreferences.edit()) {
                            putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                            remove("access_token") // 액세스 토큰 삭제
                            apply()
                        }
                        navController.navigate("Duplicate")
                    } else if (errorMessage.contains("430")) {  //430dms
                        navController.navigate("TryLoginFreq")
                    } else {
                        errorMessage = "오류가 발생하였습니다."
                    }

                }else {
                    errorMessage = "액세스 토큰이 없습니다."
                }

            }
        })
    }




    // 초기 이벤트 목록을 가져오는 함수 호출
    LaunchedEffect(Unit) {
        fetchEvents()
        // 알림 권한 확인
        if (isNotificationEnabled) {
            notificationManager.scheduleNotificationListFromApi() // 알림 예약
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
                backgroundColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.height(60.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = when (tab) {
                                    "QR" -> R.drawable.ic_qr
                                    "일정" -> R.drawable.ic_calendar
                                    "홈" -> R.drawable.ic_home
                                    "지도" -> R.drawable.ic_map
                                    "메뉴" -> R.drawable.ic_menu
                                    else -> R.drawable.ic_home // 기본 아이콘 설정
                                }),
                                contentDescription = tab,
                                tint = if (selectedTabIndex == index) if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C) else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                tab,
                                color = if (selectedTabIndex == index) if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C) else Color.Gray, // 선택된 탭의 텍스트 색상 설정
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        },
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
                1 -> CalendarScreen(events, navController, isTimelineView) { newValue ->
                    fetchEvents()
                    onTimelineViewChange(newValue) // 상태 업데이트
                }
                2 -> {
                    if (isLoading) {
                        Text(
                            "이벤트를 불러오는 중...",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    } else if (errorMessage.isNotEmpty()) {
                        if (accessToken != null) {
                            if (errorMessage.contains("401")) {
                                with(sharedPreferences.edit()) {
                                    putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                                    remove("access_token") // 액세스 토큰 삭제
                                    apply()
                                }
                                navController.navigate("TokenOver")
                            } else if (errorMessage.contains("409")) {
                                with(sharedPreferences.edit()) {
                                    putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                                    remove("access_token") // 액세스 토큰 삭제
                                    apply()
                                }
                                navController.navigate("DeleteByAdmin")
                            } else if (errorMessage.contains("412")) {
                                with(sharedPreferences.edit()) {
                                    putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                                    remove("access_token") // 액세스 토큰 삭제
                                    apply()
                                }
                                navController.navigate("Duplicate")
                            } else if (errorMessage.contains("430")) {      //430은 액세스 토큰 삭제
                                navController.navigate("TryLoginFreq")
                            }else{
//                            Text("정보를 불러오는 데 실패했습니다: $errorMessage", color = Color.Red)
////                            Text("정보를 불러오는 데 실패했습니다", color = Color.Red)
                            }
                        }else {
                            ////
                            errorMessage = "액세스 토큰이 없습니다."
                        }

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
                3 -> MapScreen(navController)
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

