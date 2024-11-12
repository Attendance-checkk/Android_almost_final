package com.example.attendancecheckandroidtest.data.models

import android.app.NotificationChannel
import android.app.NotificationManager // 시스템 NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat // NotificationManagerCompat 추가
import com.example.attendancecheckandroidtest.R
import android.Manifest
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat // ContextCompat 추가
import com.example.attendancecheckandroidtest.MainActivity
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = com.example.attendancecheckandroidtest.data.models.NotificationManager.CHANNEL_ID

        // Notification Channel 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 클릭 시 MainActivity로 이동할 인텐트 생성
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 새로운 태스크로 시작
        }

        // 알림 빌더
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.sch_stamp)
            .setContentTitle(intent.getStringExtra("title") ?: "알림")
            .setContentText(intent.getStringExtra("message") ?: "메시지 없음")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("message") ?: "메시지 없음"))
            .setAutoCancel(true) // 클릭 후 알림 자동 제거

        // 권한 확인
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationId = Random.nextInt(1000) // 고유한 ID 생성
            NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder.build())
        } else {
            Log.e("AlarmReceiver", "Notification permission not granted.")
        }

        // 로그인 상태 확인 후 MainActivity로 이동
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            context.startActivity(mainIntent)
        } else {
            Log.d("AlarmReceiver", "User is not logged in")
        }
    }
}
