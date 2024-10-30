package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.data.models.NotificationDataByDate
import com.example.attendancecheckandroidtest.data.models.NotificationManager
import com.example.attendancecheckandroidtest.data.models.UserInfo
import com.example.attendancecheckandroidtest.data.network.ApiService
import com.example.attendancecheckandroidtest.ui.theme.components.NotificationToggle
import com.example.attendancecheckandroidtest.ui.theme.components.createDateFromString
import okhttp3.OkHttpClient

@Composable
fun MenuScreen(navController: NavController, onTabSelected: (Int) -> Unit, isNotificationEnabled: Boolean, deleteAccount: () -> Unit) {
    val context = LocalContext.current
    val client = OkHttpClient() // OkHttpClient 인스턴스 생성
    val apiService = ApiService(client, context)
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var notificationManager = NotificationManager(context)

    // 알림 데이터 리스트
    val notifications = listOf(
        NotificationDataByDate("개회식이 곧 시작돼요!", "개회식에 참여하고 스탬프를 받으세요 ✅", createDateFromString("2024-11-05 10:20")),
        NotificationDataByDate("🎮 게임 경진대회가 곧 시작해요!", "여러분의 숨겨진 게임 실력을 보여주세요 👍", createDateFromString("2024-11-05 10:50")),
        NotificationDataByDate("🎮 게임 경진대회가 진행중이에요", "참여를 안하신 분들은 6126호로!", createDateFromString("2024-11-05 13:00")),
        NotificationDataByDate("👨‍🎓 졸업생 토크콘서트가 곧 시작해요!", "선배님과 즐겁게 이야기해요!", createDateFromString("2024-11-05 13:50")),
        NotificationDataByDate("👨‍🎓 졸업생 토크콘서트가 곧 종료돼요!", "아직 묻고 싶은게 남으셨다면 빠르게 달려가세요 🏃", createDateFromString("2024-11-05 15:40")),
        NotificationDataByDate("🎮 게임 경진대회가 진행중이에요", "오늘 12시까지만 참여가 가능해요", createDateFromString("2024-11-06 09:30")),
        NotificationDataByDate("👨‍💻 전문가 특강이 곧 시작해요!", "사업체 전문가의 이야기들을 들어보세요. 아주 중요한 내용들이 있을지도..?!", createDateFromString("2024-11-06 09:50")),
        NotificationDataByDate("🎮 게임 경진대회가 곧 끝나요!", "게임 경진대회는 이제 더 진행되지 않아요!", createDateFromString("2024-11-06 11:30")),
        NotificationDataByDate("👨‍💻 전문가 특강이 곧 끝나요!", "아직 놓치고 싶지 않다면 대강당으로!", createDateFromString("2024-11-06 11:40")),
        NotificationDataByDate("곧 시상식과 함께 폐회식이 진행돼요!", "마지막까지 함께해요 🥳", createDateFromString("2024-11-06 14:50"))
    )

    // SharedPreferences에서 알림 수신 여부를 가져오기
    var notificationEnabled by remember {
        mutableStateOf(sharedPreferences.getBoolean("isNotificationEnabled", true))
    }

    // 사용자 설정 정보를 가져오는 함수 호출
    LaunchedEffect(Unit) {
        apiService.fetchUserSettingInfo(
            onSuccess = { info ->
                userInfo = info
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            } // onError 파라미터 추가
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "메뉴",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Column(
            modifier = Modifier.padding(30.dp) // 내부 요소들에 대한 패딩 추가
        ) {
            SectionHeader("학생 정보", color = MaterialTheme.colorScheme.onSurface)
            if (isLoading) {
                Text("로딩 중...", color = Color.Gray)
            } else if (userInfo != null) {
                UserInfoList(userInfo)
            } else {
                Text("정보를 불러오는 데 실패했습니다: $errorMessage", color = Color.Red)
            }

            SectionHeader("링크", color = MaterialTheme.colorScheme.onSurface)
            LinkButton(
                text = "🙋‍♂️ 문의하기",
                url = "https://potent-barnacle-025.notion.site/FAQ-116c07204d29805a8418d9a37bf330a2?pvs=4",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            LinkButton(
                text = "👍 만족도 조사",
                url = "https://www.google.com/",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            LinkButton(
                text = "🪪 개인정보 처리방침",
                url = "https://potent-barnacle-025.notion.site/124c07204d2980ffa767d3a24b3e18b8?pvs=4",
                modifier = Modifier.padding(vertical = 4.dp)
            )

            SectionHeader("설정", color = MaterialTheme.colorScheme.onSurface)
            // NotificationToggle을 감싸는 Box 추가
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
            ) {
                NotificationToggle(
                    isChecked = notificationEnabled, // 초기 상태를 여기서 설정
                    onToggle = { isChecked ->
                        notificationEnabled = isChecked
                        // 알림 수신 여부를 SharedPreferences에 저장
                        with(sharedPreferences.edit()) {
                            putBoolean("isNotificationEnabled", isChecked)
                            apply()
                        }

                        // 알림 예약 또는 취소
                        if (isChecked) {
                            notificationManager.scheduleNotifications(notifications) // 알림 예약
                        } else {
                            notificationManager.cancelAllNotifications() // 알림 취소
                        }
                    }
                )
            }

            Button(
                onClick = {
                    onTabSelected(4)
                    navController.navigate("caution") // CautionView로 이동
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .border(1.dp, Color.Red, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // 배경 색상 설정
            ) {
                Text(text = "⚠️ 계정 삭제 주의사항", color = Color.White) // 텍스트 색상 설정
            }

            // 계정 삭제 버튼
            Button(
                onClick = {
                    showDeleteConfirmationDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .border(1.dp, Color.Red, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // 배경 색상 설정
            ) {
                Text(text = "🗑️ 계정 삭제", color = Color.White) // 텍스트 색상 설정
            }
        }
    }

    // 계정 삭제 확인 다이얼로그
    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text("계정 삭제 확인") },
            text = { Text("정말로 계정을 삭제하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val accessToken = sharedPreferences.getString("access_token", null)

                    if (accessToken != null) {
                        apiService.deleteAccount(accessToken, onSuccess = {
                            // 계정 삭제 후 로그인 상태 업데이트
                            with(sharedPreferences.edit()) {
                                putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                                remove("access_token") // 액세스 토큰 삭제
                                apply()
                            }
                            deleteAccount() // 계정 삭제 후 처리
                            navController.navigate("login") // 로그인 화면으로 이동
                        }, onError = { error ->
                            errorMessage = error // 에러 메시지 설정
                        })
                    } else {
                        errorMessage = "액세스 토큰이 없습니다."
                    }
                    showDeleteConfirmationDialog = false // 다이얼로그 닫기
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmationDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}
@Composable
fun UserInfoList(userInfo: UserInfo?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface), // 다크 모드에 맞는 배경색 적용
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOf(
                Pair("학과", userInfo?.major ?: "입력되지 않음"),
                Pair("학번", userInfo?.studentCode ?: "입력되지 않음"),
                Pair("이름", userInfo?.name ?: "입력되지 않음")
            )) { (label, value) ->
                UserInfoRow(label = label, value = value)

                // Divider 추가
                if (label != "이름") {
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

//@Composable
//fun UserInfoList(userInfo: UserInfo?) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White),
//        shape = RoundedCornerShape(12.dp), // 라운드 모서리
//        border = BorderStroke(1.dp, Color.LightGray) // 테두리
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            items(listOf(
//                Pair("학과", userInfo?.major ?: "입력되지 않음"),
//                Pair("학번", userInfo?.studentCode ?: "입력되지 않음"),
//                Pair("이름", userInfo?.name ?: "입력되지 않음")
//            )) { (label, value) ->
//                UserInfoRow(label = label, value = value)
//
//                // Divider 추가
//                if (label != "이름") {
//                    HorizontalDivider(color = Color.LightGray) // Divider 색상 조정 가능
//                }
//            }
//        }
//    }
//}
@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp) // 높이를 40으로 설정합니다.
            .background(MaterialTheme.colorScheme.surface), // 배경색 다크 모드에 맞춤
        verticalAlignment = Alignment.CenterVertically // 수직 가운데 정렬
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp), // 왼쪽 패딩 추가
            textAlign = TextAlign.Start, // 왼쪽 정렬
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )
        Text(
            text = value,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp), // 오른쪽 패딩 추가
            textAlign = TextAlign.End, // 오른쪽 정렬
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )
    }
}
@Composable
fun LinkButton(text: String, url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray) // 버튼 색상 설정
    ) {
        Text(text = text, color = Color.Blue)
    }
}

@Composable
fun SectionHeader(title: String, color: Color = MaterialTheme.colorScheme.onSurface) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), // 볼드체로 설정
        color = color, // 매개변수로 받은 색상 사용
        modifier = Modifier.padding(top = 14.dp, bottom = 6.dp, start = 5.dp) // SectionHeader 사이의 패딩 추가
    )
}


@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    // Mock NavController 생성
    val navController = rememberNavController()

    MaterialTheme {
        MenuScreen(
            navController = navController,
            onTabSelected = { /* 탭 선택 시 처리 */ }, // 적절한 람다 함수로 수정
            isNotificationEnabled = true,
            deleteAccount = { /* Handle account deletion */ } // 계정 삭제 핸들러
        )
    }
}

