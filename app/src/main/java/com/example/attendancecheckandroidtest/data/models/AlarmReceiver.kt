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
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat // ContextCompat 추가

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // NotificationManager의 CHANNEL_ID 사용
        val channelId = com.example.attendancecheckandroidtest.data.models.NotificationManager.CHANNEL_ID // MyNotificationManager로 변경

        // Notification Channel 생성 (Android 8.0 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH // IMPORTANCE_HIGH는 NotificationChannel의 상수
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel) // NotificationManager 인스턴스에서 호출
        }

        // 알림 빌더
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.sch_stamp)
            .setContentTitle(intent.getStringExtra("title") ?: "알림")
            .setContentText(intent.getStringExtra("message") ?: "메시지 없음")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // API 25 이하에서만 사용

        // 권한 확인
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // 알림 전송
            NotificationManagerCompat.from(context).notify(0, notificationBuilder.build()) // NotificationManagerCompat에서 호출
        } else {
            // 권한 요청 로직 추가 (필요한 경우)
            // 권한 요청을 위한 Intent 등을 처리하는 로직을 여기에 추가
        }
    }
}
