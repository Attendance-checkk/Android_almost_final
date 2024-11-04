package com.example.attendancecheckandroidtest.ui.theme.screens


import android.Manifest
import android.R.attr.isLightTheme
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
//import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.data.models.AlarmReceiver
import com.example.attendancecheckandroidtest.data.models.NotificationDataByDate
import com.example.attendancecheckandroidtest.data.models.NotificationManager
import com.example.attendancecheckandroidtest.data.models.UserInfo
import com.example.attendancecheckandroidtest.data.network.ApiService
import com.example.attendancecheckandroidtest.ui.theme.components.NotificationToggle

import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random
import android.provider.Settings

@Composable
fun MenuScreen( navController: NavController,
                deleteAccount: () -> Unit,
                onTabSelected: (Int) -> Unit,
                isNotificationEnabled: Boolean ) {
//    fun MenuScreen(navController: NavController, onTabSelected: (Int) -> Unit,  deleteAccount: () -> Unit) {
    val context = LocalContext.current
    val apiService = ApiService(context, client2 = OkHttpClient())
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var errorMessage by remember { mutableStateOf("") }
    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var notificationManager = NotificationManager(context)

    // 사용자 설정 정보를 가져오는 함수 호출
    LaunchedEffect(Unit) {
        apiService.fetchUserSettingInfo(
            isNotificationEnabled,
            onSuccess = { info ->
                userInfo = info
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

// 알림 권한 상태 체크
    val isNotificationEnabled = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

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
            val accessToken = sharedPreferences.getString("access_token", null)

            SectionHeader("학생 정보", color = MaterialTheme.colorScheme.onSurface)
            if (isLoading) {
                Text("로딩 중...", color = Color.Gray)
            } else if (userInfo != null) {
                UserInfoList(userInfo)
            } else if (errorMessage.isNotEmpty()) {
                // 에러 처리 로직
                if (accessToken != null) {
                    with(sharedPreferences.edit()) {
                        remove("access_token") // 액세스 토큰 삭제
                        apply()
                    }
                } else {
                    errorMessage = "액세스 토큰이 없습니다."
                }
                // Navigate based on error message
            } else {
                Text("정보를 불러오는 데 실패했습니다", color = Color.Red)
            }

            SectionHeader("링크", color = MaterialTheme.colorScheme.onSurface)
            LinkButton(
                text = "🙋‍♂️ 문의하기",
                url = "https://potent-barnacle-025.notion.site/FAQ-116c07204d29805a8418d9a37bf330a2?pvs=4",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            LinkButton(
                text = "👍 만족도 조사",
                url = "https://forms.gle/1PbtPMTjptGnUGTU9",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            LinkButton(
                text = "🪪 개인정보 처리방침",
                url = "https://potent-barnacle-025.notion.site/124c07204d2980ffa767d3a24b3e18b8?pvs=4",
                modifier = Modifier.padding(vertical = 4.dp)
            )

            SectionHeader("설정", color = MaterialTheme.colorScheme.onSurface)
            Button(
                onClick = {
                    // 알림 설정으로 이동하는 로직
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "🔔 알림 설정", color = Color.Blue)
            }

            // 계정 삭제 버튼
            Button(
                onClick = {
                    navController.navigate("caution")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .border(1.dp, Color.Red, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // 배경 색상 설정
            ) {
                Text(text = "🗑️ 계정삭제", color = Color.White) // 텍스트 색상 설정
            }
        }
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
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White)
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

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp), // 높이를 40으로 설정합니다.
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
            maxLines = 1,
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

data class NotificationDataByDate(
    val titleString: String,
    val bodyString: String,
    val date: Date
)

class NotificationManager(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "AttendanceCheckChannel"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Attendance Check Notifications",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for attendance check notifications"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotifications(notifications: List<NotificationDataByDate>) {
        Log.d("NotificationManager", "Scheduling notifications")

        // 기존에 예약된 알림을 취소할지 여부 결정
        cancelAllNotifications()

        for (notification in notifications) {
            try {
                scheduleNotification(
                    title = notification.titleString,
                    message = notification.bodyString,
                    triggerAtMillis = notification.date.time
                )
                Log.d("NotificationManager_Menu", "Scheduled notification: ${notification.titleString} at ${notification.date}") // 추가된 로그
            } catch (e: Exception) {
                Log.e("NotificationManager", "Failed to schedule notification: ${notification.titleString}, Error: ${e.message}")
            }
        }

        Log.d("NotificationManager", "All notifications scheduled successfully.")
    }

    @SuppressLint("MissingPermission")
    private fun scheduleNotification(title: String, message: String, triggerAtMillis: Long) {
        val notificationIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            getNotificationId(),
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)

    }

    private fun getNotificationId(): Int {
        return Random.nextInt(1000) // UUID 사용 고려
    }

    fun cancelAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }

    private fun createDateFromString(dateString: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date()
    }

    fun scheduleNotificationList() {
        val notifications = listOf(
            NotificationDataByDate(
                titleString = "개회식이 곧 시작돼요!",
                bodyString = "개회식에 참여하고 스탬프를 받으세요 ✅",
                date = createDateFromString("2024-11-04 03:31")
            )
        )
        // 권한 확인 후 알림 예약
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            scheduleNotifications(notifications)
        } else {
            // 권한 요청 로직 추가
            // 예: 권한 요청을 위한 Activity로 전환
        }
    }
}

