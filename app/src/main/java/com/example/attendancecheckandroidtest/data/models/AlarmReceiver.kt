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
import android.util.Log
import androidx.core.content.ContextCompat // ContextCompat 추가
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = com.example.attendancecheckandroidtest.data.models.NotificationManager.CHANNEL_ID

        // Notification Channel 생성 (한 번만 생성하는 것이 좋음)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 빌더
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.sch_stamp)
            .setContentTitle(intent.getStringExtra("title") ?: "알림")
            .setContentText(intent.getStringExtra("message") ?: "메시지 없음")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // 권한 확인
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationId = Random.nextInt(1000) // 고유한 ID 생성
            NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder.build())
        } else {
            // 권한 요청을 위한 Intent 등을 처리하는 로직을 여기에 추가
            Log.e("AlarmReceiver", "Notification permission not granted.")
        }
    }
}
