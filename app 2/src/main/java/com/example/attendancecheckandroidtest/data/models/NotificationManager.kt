package com.example.attendancecheckandroidtest.data.models

import android.R.attr.title
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
import com.example.attendancecheckandroidtest.data.network.ApiService
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

//
//class NotificationManager(private val context: Context) {
//
//    companion object {
//        const val CHANNEL_ID = "AttendanceCheckChannel"
//    }
//
//    init {
//        createNotificationChannel()
//    }
//
//    private fun createNotificationChannel() {
//        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Attendance Check Notifications",
//                android.app.NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                description = "Channel for attendance check notifications"
//            }
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun scheduleNotifications(notifications: List<NotificationDataByDate>) {
//        Log.d("NotificationManager", "Scheduling notifications")
//        cancelAllNotifications() // 기존 알림 취소
//
//        for (notification in notifications) {
//            try {
//                scheduleNotification(
//                    title = notification.titleString,
//                    message = notification.bodyString,
//                    triggerAtMillis = notification.date.time
//                )
//                Log.d("NotificationManager", "Scheduled notification: ${notification.titleString} at ${notification.date}")
//            } catch (e: Exception) {
//                Log.e("NotificationManager", "Failed to schedule notification: ${notification.titleString}, Error: ${e.message}")
//            }
//        }
//
//        // 예약된 알림 목록 로그 출력
//        Log.d("NotificationManager", "All notifications scheduled successfully.")
//        notifications.forEach { notification ->
//            Log.d("NotificationManager", "Notification scheduled: Title: ${notification.titleString}, Time: ${notification.date}")
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun scheduleNotification(title: String, message: String, triggerAtMillis: Long) {
//        val notificationIntent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("title", title)
//            putExtra("message", message)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            getNotificationId(),
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
//
//        Log.d("NotificationManager", "Notification scheduled: $title at $triggerAtMillis")
//    }
//
//    private fun getNotificationId(): Int {
//        return Random.nextInt(1000) // UUID 사용 고려
//    }
//
//    fun cancelAllNotifications() {
//        NotificationManagerCompat.from(context).cancelAll()
//    }
//
//    // 문자열을 Date 객체로 변환하는 함수
//    private fun createDateFromString(dateString: String): Date {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // UTC로 설정
//        return dateFormat.parse(dateString) ?: Date()
//    }
//
//    // API에서 이벤트를 가져와 알림을 예약하는 메서드
//    fun scheduleNotificationListFromApi() {
//        val client2 = OkHttpClient.Builder().build()
//        val apiService = ApiService(context, client2)
//
//        apiService.fetchEventList(context, { events ->
//            val notifications = events.mapNotNull { event ->
//                val eventStartTime = createDateFromString(event.eventStartTime)
//                val notificationTime = Date(eventStartTime.time - 20 * 60 * 1000) // 20분 전
//
//                if (notificationTime.after(Date())) {
//                    NotificationDataByDate(
//                        titleString = event.eventName,
//                        bodyString = event.description,
//                        date = notificationTime
//                    )
//                } else {
//                    null
//                }
//            }
//
//            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
//                scheduleNotifications(notifications)
//            } else {
//                // 권한 요청 로직 추가
//            }
//        }, { errorMessage ->
//            Log.e("NotificationManager", "Error fetching events: $errorMessage")
//        })
//    }
//}



//
//class NotificationManager(private val context: Context) {
//
//    companion object {
//        const val CHANNEL_ID = "AttendanceCheckChannel"
//    }
//
//    init {
//        createNotificationChannel()
//    }
//
//    private fun createNotificationChannel() {
//        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Attendance Check Notifications",
//                android.app.NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                description = "Channel for attendance check notifications"
//            }
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun scheduleNotifications(notifications: List<NotificationDataByDate>) {
//        Log.d("NotificationManager", "Scheduling notifications")
//        cancelAllNotifications() // 기존 알림 취소
//
//        for (notification in notifications) {
//            try {
//                scheduleNotification(
//                    title = notification.titleString,
//                    message = notification.bodyString,
//                    triggerAtMillis = notification.date.time
//                )
//                Log.d("NotificationManager_log", "Scheduled notification: ${notification.titleString} at ${notification.date}")
//            } catch (e: Exception) {
//                Log.e("NotificationManager", "Failed to schedule notification: ${notification.titleString}, Error: ${e.message}")
//            }
//        }
//
//        // 예약된 알림 목록 로그 출력
//        Log.d("NotificationManager_All_Success", "All notifications scheduled successfully.")
//        notifications.forEach { notification ->
//            Log.d("NotificationManager_Success", "Notification scheduled: Title: ${notification.titleString}, Time: ${notification.date}")
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun scheduleNotification(title: String, message: String, triggerAtMillis: Long) {
//        val notificationIntent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("title", title)
//            putExtra("message", message)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            getNotificationId(),
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
//
//        Log.d("NotificationManager", "Notification scheduled: $title at $triggerAtMillis")
//    }
//
//    private fun getNotificationId(): Int {
//        return Random.nextInt(1000) // UUID 사용 고려
//    }
//
//    fun cancelAllNotifications() {
//        NotificationManagerCompat.from(context).cancelAll()
//    }
//
//    // 문자열을 Date 객체로 변환하는 함수
//    private fun createDateFromString(dateString: String): Date {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // UTC로 설정
//        return dateFormat.parse(dateString) ?: Date()
//    }
//
//    fun scheduleNotificationListFromApi() {
//        val client2 = OkHttpClient.Builder().build()
//        val apiService = ApiService(context, client2)
//
//        apiService.fetchEventList(context, { events ->
//            val notifications = events.mapNotNull { event ->
//                val eventStartTime = createDateFromString(event.eventStartTime)
//                val notificationTime = Date(eventStartTime.time - 20 * 60 * 1000) // 20분 전
//
//                if (notificationTime.after(Date())) {
//                    NotificationDataByDate(
//                        titleString = event.eventName,
//                        bodyString = "${event.description}\n위치: ${event.location}\n시작 시간: ${formatEventStartTime(event.eventStartTime)}", // 포맷된 시작 시간 포함
//                        date = notificationTime
//                    )
//                } else {
//                    null
//                }
//            }
//
//            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
//                scheduleNotifications(notifications)
//            } else {
//                // 권한 요청 로직 추가
//            }
//        }, { errorMessage ->
//            Log.e("NotificationManager", "Error fetching events: $errorMessage")
//        })
//    }
//
//    private fun formatEventStartTime(dateString: String): String {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        // 입력 시간대는 UTC로 설정
//        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
//
//        // 한국 시간대로 변환하기 위한 출력 포맷
//        val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.getDefault())
//        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정
//
//        return try {
//            val date = inputFormat.parse(dateString)
//            outputFormat.format(date ?: Date())
//        } catch (e: Exception) {
//            Log.e("DateParsing", "Failed to format date: ${e.message}")
//            dateString // 변환 실패 시 원본 문자열 반환
//        }
//    }
//
//
//}

//
//
//class NotificationManager(private val context: Context) {
//
//    companion object {
//        const val CHANNEL_ID = "AttendanceCheckChannel"
//    }
//
//    init {
//        createNotificationChannel()
//    }
//
//    private fun createNotificationChannel() {
//        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Attendance Check Notifications",
//                android.app.NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                description = "Channel for attendance check notifications"
//            }
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun scheduleNotifications(notifications: List<NotificationDataByDate>) {
//        Log.d("NotificationManager", "Scheduling notifications")
//        cancelAllNotifications() // 기존 알림 취소
//
//        for (notification in notifications) {
//            try {
//                scheduleNotification(
//                    title = notification.titleString,
//                    message = notification.bodyString,
//                    triggerAtMillis = notification.date.time
//                )
//                Log.d("NotificationManager_log", "Scheduled notification: ${notification.titleString} at ${notification.date}")
//            } catch (e: Exception) {
//                Log.e("NotificationManager", "Failed to schedule notification: ${notification.titleString}, Error: ${e.message}")
//            }
//        }
//
//        Log.d("NotificationManager_All_Success", "All notifications scheduled successfully.")
//        notifications.forEach { notification ->
//            Log.d("NotificationManager_Success", "Notification scheduled: Title: ${notification.titleString}, Time: ${notification.date}")
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun scheduleNotification(title: String, message: String, triggerAtMillis: Long) {
//        val notificationIntent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("title", title)
//            putExtra("message", message)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            getNotificationId(),
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
//
//        Log.d("NotificationManager", "Notification scheduled: $title at $triggerAtMillis")
//    }
//
//    private fun getNotificationId(): Int {
//        return Random.nextInt(1000) // UUID 사용 고려
//    }
//
//    fun cancelAllNotifications() {
//        NotificationManagerCompat.from(context).cancelAll()
//    }
//
//    private fun createDateFromString(dateString: String): Date {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // UTC로 설정
//        return dateFormat.parse(dateString) ?: Date()
//    }
//
//    fun scheduleNotificationListFromApi() {
//        val client2 = OkHttpClient.Builder().build()
//        val apiService = ApiService(context, client2)
//
//        apiService.fetchEventList(context, { events ->
//            val notifications = events.mapNotNull { event ->
//                val eventStartTime = createDateFromString(event.eventStartTime)
//                val notificationTime = Date(eventStartTime.time - 20 * 60 * 1000) // 20분 전
//
//                if (notificationTime.after(Date())) {
//                    NotificationDataByDate(
//                        titleString = event.eventName,
//                        bodyString = "${event.description}\n위치: ${event.location}\n시작 시간: ${formatEventStartTime(event.eventStartTime)}", // 포맷된 시작 시간 포함
//                        date = notificationTime
//                    )
//                } else {
//                    null
//                }
//            }
//
//            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
//                scheduleNotifications(notifications)
//            } else {
//                // 권한 요청 로직 추가
//            }
//        }, { errorMessage ->
//            Log.e("NotificationManager", "Error fetching events: $errorMessage")
//        })
//    }
//
//    private fun formatEventStartTime(dateString: String): String {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
//
//        val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.getDefault())
//        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정
//
//        return try {
//            val date = inputFormat.parse(dateString)
//            outputFormat.format(date ?: Date())
//        } catch (e: Exception) {
//            Log.e("DateParsing", "Failed to format date: ${e.message}")
//            dateString // 변환 실패 시 원본 문자열 반환
//        }
//    }
//}

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
        cancelAllNotifications() // 기존 알림 취소

        val scheduledNotificationIds = mutableSetOf<Int>() // 예약된 알림 ID 저장

        for (notification in notifications) {
            val notificationId = notification.titleString.hashCode() // 제목 해시값을 ID로 사용

            if (!scheduledNotificationIds.contains(notificationId)) {
                try {
                    scheduleNotification(
                        title = notification.titleString,
                        message = notification.bodyString,
                        triggerAtMillis = notification.date.time,
                        notificationId = notificationId // ID 전달
                    )
                    scheduledNotificationIds.add(notificationId) // ID 추가
                    Log.d("NotificationManager_log", "Scheduled notification: ${notification.titleString} at ${notification.date}")
                } catch (e: Exception) {
                    Log.e("NotificationManager", "Failed to schedule notification: ${notification.titleString}, Error: ${e.message}")
                }
            } else {
                Log.d("NotificationManager", "Duplicate notification skipped: ${notification.titleString}")
            }
        }

        Log.d("NotificationManager_All_Success", "All notifications scheduled successfully.")
        notifications.forEach { notification ->
            Log.d("NotificationManager_Success", "Notification scheduled: Title: ${notification.titleString}, Time: ${notification.date}")
        }
    }

    @SuppressLint("MissingPermission")
    private fun scheduleNotification(title: String, message: String, triggerAtMillis: Long, notificationId: Int) {
        val notificationIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId, // 고유한 ID 사용
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)

        Log.d("NotificationManager", "Notification scheduled: $title at $triggerAtMillis")
    }

    fun cancelAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }

    private fun createDateFromString(dateString: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // UTC로 설정
        return dateFormat.parse(dateString) ?: Date()
    }

    fun scheduleNotificationListFromApi() {
        val client2 = OkHttpClient.Builder().build()
        val apiService = ApiService(context, client2)

        apiService.fetchEventList(context, { events ->
            val notifications = events.mapNotNull { event ->
                val eventStartTime = createDateFromString(event.eventStartTime)
                val notificationTime = Date(eventStartTime.time - 20 * 60 * 1000) // 20분 전

                if (notificationTime.after(Date())) {
                    NotificationDataByDate(
                        titleString = event.eventName,
                        bodyString = "${event.description}\n위치: ${event.location}\n시작 시간: ${formatEventStartTime(event.eventStartTime)}", // 포맷된 시작 시간 포함
                        date = notificationTime
                    )
                } else {
                    null
                }
            }

            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                scheduleNotifications(notifications)
            } else {
                // 권한 요청 로직 추가
            }
        }, { errorMessage ->
            Log.e("NotificationManager", "Error fetching events: $errorMessage")
        })
    }

    private fun formatEventStartTime(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정

        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            Log.e("DateParsing", "Failed to format date: ${e.message}")
            dateString // 변환 실패 시 원본 문자열 반환
        }
    }
}
