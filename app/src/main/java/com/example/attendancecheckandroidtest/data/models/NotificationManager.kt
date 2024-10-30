package com.example.attendancecheckandroidtest.data.models

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

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
                Log.d("NotificationManager", "Scheduled notification: ${notification.titleString} at ${notification.date}") // 추가된 로그
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

        Log.d("NotificationManager", "Notification scheduled: $title at $triggerAtMillis") // 추가된 로그
    }

    private fun getNotificationId(): Int {
        return Random.nextInt(1000) // UUID 사용 고려
    }

    fun cancelAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }

    // 문자열을 Date 객체로 변환하는 함수
    private fun createDateFromString(dateString: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date()
    }

    // 알림 데이터 리스트를 받아 예약하는 메서드
    fun scheduleNotificationList() {
        val notifications = listOf(
            NotificationDataByDate(
                titleString = "개회식이 곧 시작돼요!",
                bodyString = "개회식에 참여하고 스탬프를 받으세요 ✅",
                date = createDateFromString("2024-11-05 10:20")
            ),
            NotificationDataByDate(
                titleString = "🎮 게임 경진대회가 곧 시작해요!",
                bodyString = "여러분의 숨겨진 게임 실력을 보여주세요 👍",
                date = createDateFromString("2024-11-05 10:50")
            ),
            NotificationDataByDate(
                titleString = "🎮 게임 경진대회가 진행중이에요",
                bodyString = "참여를 안하신 분들은 6126호로!",
                date = createDateFromString("2024-11-05 13:00")
            ),
            NotificationDataByDate(
                titleString = "👨‍🎓 졸업생 토크콘서트가 곧 시작해요!",
                bodyString = "선배님과 즐겁게 이야기해요!",
                date = createDateFromString("2024-11-05 13:50")
            ),
            NotificationDataByDate(
                titleString = "👨‍🎓 졸업생 토크콘서트가 곧 종료돼요!",
                bodyString = "아직 묻고 싶은게 남으셨다면 빠르게 달려가세요 🏃",
                date = createDateFromString("2024-11-05 15:40")
            ),
            NotificationDataByDate(
                titleString = "🎮 게임 경진대회가 진행중이에요",
                bodyString = "오늘 12시까지만 참여가 가능해요",
                date = createDateFromString("2024-11-06 09:30")
            ),
            NotificationDataByDate(
                titleString = "👨‍💻 전문가 특강이 곧 시작해요!",
                bodyString = "사업체 전문가의 이야기들을 들어보세요. 아주 중요한 내용들이 있을지도..?!",
                date = createDateFromString("2024-11-06 09:50")
            ),
            NotificationDataByDate(
                titleString = "🎮 게임 경진대회가 곧 끝나요!",
                bodyString = "게임 경진대회는 이제 더 진행되지 않아요!",
                date = createDateFromString("2024-11-06 11:30")
            ),
            NotificationDataByDate(
                titleString = "👨‍💻 전문가 특강이 곧 끝나요!",
                bodyString = "아직 놓치고 싶지 않다면 대강당으로!",
                date = createDateFromString("2024-11-06 11:40")
            ),
            NotificationDataByDate(
                titleString = "곧 시상식과 함께 폐회식이 진행돼요!",
                bodyString = "마지막까지 함께해요 🥳",
                date = createDateFromString("2024-11-06 14:50")
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