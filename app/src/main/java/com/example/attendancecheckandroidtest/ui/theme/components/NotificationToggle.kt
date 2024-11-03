//package com.example.attendancecheckandroidtest.ui.theme.components
//
//import android.Manifest
//import android.app.Activity
//import android.content.pm.PackageManager
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Switch
//import androidx.compose.material3.SwitchDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.attendancecheckandroidtest.data.models.NotificationDataByDate
//import com.example.attendancecheckandroidtest.data.models.NotificationManager
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//
//@Composable
//fun NotificationToggle(isChecked: Boolean, onToggle: (Boolean) -> Unit) {
//    val context = LocalContext.current
//    val notificationManager = NotificationManager(context)
//    var showDialog by remember { mutableStateOf(false) }
//    var showPermissionDialog by remember { mutableStateOf(false) }
//
//    val notifications = listOf(
//        NotificationDataByDate("개회식이 곧 시작돼요!", "개회식에 참여하고 스탬프를 받으세요 ✅", createDateFromString("2024-11-04 03:10")),
//        NotificationDataByDate("🎮 게임 경진대회가 곧 시작해요!", "여러분의 숨겨진 게임 실력을 보여주세요 👍", createDateFromString("2024-11-05 10:50")),
//        NotificationDataByDate("🎮 게임 경진대회가 진행중이에요", "참여를 안하신 분들은 6126호로!", createDateFromString("2024-11-05 13:00")),
//        NotificationDataByDate("👨‍🎓 졸업생 토크콘서트가 곧 시작해요!", "선배님과 즐겁게 이야기해요!", createDateFromString("2024-11-05 13:50")),
//        NotificationDataByDate("👨‍🎓 졸업생 토크콘서트가 곧 종료돼요!", "아직 묻고 싶은게 남으셨다면 빠르게 달려가세요 🏃", createDateFromString("2024-11-05 15:40")),
//        NotificationDataByDate("🎮 게임 경진대회가 진행중이에요", "오늘 12시까지만 참여가 가능해요", createDateFromString("2024-11-06 09:30")),
//        NotificationDataByDate("👨‍💻 전문가 특강이 곧 시작해요!", "사업체 전문가의 이야기들을 들어보세요. 아주 중요한 내용들이 있을지도..?!", createDateFromString("2024-11-06 09:50")),
//        NotificationDataByDate("🎮 게임 경진대회가 곧 끝나요!", "게임 경진대회는 이제 더 진행되지 않아요!", createDateFromString("2024-11-06 11:30")),
//        NotificationDataByDate("👨‍💻 전문가 특강이 곧 끝나요!", "아직 놓치고 싶지 않다면 대강당으로!", createDateFromString("2024-11-06 11:40")),
//        NotificationDataByDate("곧 시상식과 함께 폐회식이 진행돼요!", "마지막까지 함께해요 🥳", createDateFromString("2024-11-06 14:50"))
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth() // 너비를 가득 채움
//            .clip(RoundedCornerShape(12.dp)) // 둥근 모서리 먼저 적용
//            .background(MaterialTheme.colorScheme.surface) // 배경색 설정
//            .padding(horizontal = 16.dp, vertical = 4.dp) // 내부 패딩 추가
//    ) {
//        Text(text = "🔔 알림 수신",
//            modifier = Modifier.weight(1f),
//            color = MaterialTheme.colorScheme.onSurface) // 텍스트 색상 적용
//        Switch(
//            checked = isChecked,
//            onCheckedChange = { newValue ->
//                if (newValue) {
//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//                        onToggle(true)
//                        notificationManager.scheduleNotifications(notifications)
//                    } else {
//                        showPermissionDialog = true
//                    }
//                } else {
//                    showDialog = true
//                }
//            },
//            colors = SwitchDefaults.colors(
//                checkedTrackColor = if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C),
//                uncheckedTrackColor = Color.Gray, // 체크 해제 상태의 배경색
//                checkedThumbColor = Color.White, // 체크 상태의 thumb 색상
//                uncheckedThumbColor = Color.White // 체크 해제 상태의 thumb 색상
//            )
//        )
//    }
//
//
//    // 권한 요청 다이얼로그 표시
//    if (showPermissionDialog) {
//        AlertDialog(
//            onDismissRequest = { showPermissionDialog = false },
//            title = { Text("권한 요청") },
//            text = { Text("알림을 사용하기 위해 권한이 필요합니다.", color = MaterialTheme.colorScheme.onSurface) },
//            confirmButton = {
//                TextButton(onClick = {
//                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
//                    showPermissionDialog = false
//                }) {
//                    Text("권한 요청")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showPermissionDialog = false }) {
//                    Text("취소")
//                }
//            }
//        )
//    }
//
//    // 다이얼로그 표시
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text("정말 끄시겠습니까?", color = MaterialTheme.colorScheme.onSurface) },
//            text = { Text("알림을 비활성화하시면 공지를 놓치실 수 있어요!", color = MaterialTheme.colorScheme.onSurface) },
//            confirmButton = {
//                TextButton(onClick = {
//                    onToggle(false)
//                    notificationManager.cancelAllNotifications()
//                    showDialog = false
//                }) {
//                    Text("확인")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDialog = false }) {
//                    Text("취소")
//                }
//            }
//        )
//    }
//}
//
//
//fun createDateFromString(dateString: String, format: String = "yyyy-MM-dd HH:mm"): Date {
//    val formatter = SimpleDateFormat(format, Locale.getDefault())
//    return formatter.parse(dateString) ?: Date() // 변환 실패 시 현재 날짜 반환
//}
//
//@Preview(showBackground = true)
//@Composable
//fun NotificationTogglePreview() {
//    var isChecked by remember { mutableStateOf(true) } // 기본값 설정
//    NotificationToggle(isChecked = isChecked, onToggle = { isChecked = it })
//}
//
////
////package com.example.attendancecheckandroidtest.ui.theme.components
////
////import android.content.Context
////import android.content.Intent
////import android.provider.Settings
////import androidx.compose.foundation.background
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material3.Button
////import androidx.compose.material3.ButtonDefaults
////import androidx.compose.material3.MaterialTheme
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.unit.dp
////
////@Composable
////fun NotificationSettingButton() {
////    val context = LocalContext.current
////
////    Row(
////        verticalAlignment = Alignment.CenterVertically,
////        modifier = Modifier
////            .fillMaxWidth()
////            .clip(RoundedCornerShape(12.dp))
////            .background(MaterialTheme.colorScheme.surface)
////            .padding(horizontal = 16.dp, vertical = 8.dp)
////    ) {
////        Button(
////            onClick = {
////                // 알림 설정 페이지로 이동
////                context.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
////                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
////                })
////            },
////            modifier = Modifier.fillMaxWidth(),
////            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
////        ) {
////            Text(text = "알림 설정 페이지로 이동", color = Color.Black)
////        }
////    }
////}

package com.example.attendancecheckandroidtest.ui.theme.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.attendancecheckandroidtest.data.models.NotificationDataByDate
import com.example.attendancecheckandroidtest.data.models.NotificationManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
//fun NotificationToggle(isChecked: Boolean, onToggle: (Boolean) -> Unit) {
fun NotificationToggle() {
    val context = LocalContext.current
    val notificationManager = NotificationManager(context)
    var showDialog by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val notifications = listOf(
        NotificationDataByDate("개회식이 곧 시작돼요!", "개회식에 참여하고 스탬프를 받으세요 ✅", createDateFromString("2024-11-04 03:10")),
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

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth() // 너비를 가득 채움
//            .clip(RoundedCornerShape(12.dp)) // 둥근 모서리 먼저 적용
//            .background(MaterialTheme.colorScheme.surface) // 배경색 설정
//            .padding(horizontal = 16.dp, vertical = 4.dp) // 내부 패딩 추가
//    ) {
//        Text(text = "🔔 알림 수신",
//            modifier = Modifier.weight(1f),
//            color = MaterialTheme.colorScheme.onSurface) // 텍스트 색상 적용
//        Switch(
//            checked = isChecked,
//            onCheckedChange = { newValue ->
//                if (newValue) {
//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//                        onToggle(true)
//                        notificationManager.scheduleNotifications(notifications)
//                    } else {
//                        showPermissionDialog = true
//                    }
//                } else {
//                    showDialog = true
//                }
//            },
//            colors = SwitchDefaults.colors(
//                checkedTrackColor = if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C),
//                uncheckedTrackColor = Color.Gray, // 체크 해제 상태의 배경색
//                checkedThumbColor = Color.White, // 체크 상태의 thumb 색상
//                uncheckedThumbColor = Color.White // 체크 해제 상태의 thumb 색상
//            )
//        )
//    }
    // 알림 권한 요청
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        showPermissionDialog = true
    } else {
        // 알림 예약
        notificationManager.scheduleNotifications(notifications)
    }
    // 권한 요청 다이얼로그 표시
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("권한 요청") },
            text = { Text("알림을 사용하기 위해 권한이 필요합니다.", color = MaterialTheme.colorScheme.onSurface) },
            confirmButton = {
                TextButton(onClick = {
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                    showPermissionDialog = false
                }) {
                    Text("권한 요청")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    // 다이얼로그 표시
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("정말 끄시겠습니까?", color = MaterialTheme.colorScheme.onSurface) },
            text = { Text("알림을 비활성화하시면 공지를 놓치실 수 있어요!", color = MaterialTheme.colorScheme.onSurface) },
            confirmButton = {
                TextButton(onClick = {
//                    onToggle(false)
                    notificationManager.cancelAllNotifications()
                    showDialog = false
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

fun createDateFromString(dateString: String, format: String = "yyyy-MM-dd HH:mm"): Date {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.parse(dateString) ?: Date() // 변환 실패 시 현재 날짜 반환
}