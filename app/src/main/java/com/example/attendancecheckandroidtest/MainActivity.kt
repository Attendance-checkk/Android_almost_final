package com.example.attendancecheckandroidtest

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.attendancecheckandroidtest.data.models.NotificationManager
import com.example.attendancecheckandroidtest.ui.theme.screens.*

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var isDialogVisible by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (isFirstLaunch()) {
//            clearAppData()
//        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach { (permission, isGranted) ->
                Log.d("PermissionRequest", "Permission: $permission, Granted: $isGranted")
                if (permission == Manifest.permission.POST_NOTIFICATIONS) {
                    if (isGranted) {
                        Log.d("PermissionRequest", "Notification permission granted.")
                    } else {
                        Log.e("PermissionRequest", "Notification permission not granted.")
                    }
                }
            }
        }

        handleAppData()

        checkAndRequestPermissions()

        setContent {
            MyApp()
        }
    }

    private fun handleAppData() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // 앱 설치 여부를 확인하는 플래그
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // 첫 실행일 경우 데이터 삭제
            clearAppData()
            with(sharedPreferences.edit()) {
                putBoolean("isFirstRun", false) // 첫 실행 플래그 업데이트
                apply()
            }
        }
    }
//
//    private fun isFirstLaunch(): Boolean {
//        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
//        // 첫 실행인지 체크하고, 첫 실행이라면 상태를 업데이트
//        return sharedPreferences.getBoolean("isFirstLaunch", true).also {
//            if (it) {
//                sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
//            }
//        }
//    }

//    private fun clearAppData() {
//        val packageName = packageName
//        val pm = this.packageManager
//        val appDataDir = this.getExternalFilesDir(null)?.parentFile
//
//        // 앱 데이터 디렉토리 삭제
//        if (appDataDir != null && appDataDir.exists()) {
//            appDataDir.deleteRecursively()
//        }
//
//        // SharedPreferences 초기화
//        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
//        sharedPreferences.edit().clear().apply()
//    }

    private fun clearAppData() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // 데이터 삭제 전 상태 확인
        val previousData = sharedPreferences.all // 모든 데이터 가져오기
        Log.d("ClearAppData", "Previous data before clearing: $previousData")

        // 모든 데이터 삭제
        sharedPreferences.edit().clear().apply()

        // 데이터 삭제 후 상태 확인
        val currentData = sharedPreferences.all // 삭제 후 상태 확인
        Log.d("ClearAppData", "Current data after clearing: $currentData")
    }

    private fun checkAndRequestPermissions() {
        val notificationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)

        if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
            requestNotificationPermissions()
        } else {
            scheduleNotifications()
        }
    }

    private fun requestNotificationPermissions() {
        val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        Log.d("PermissionRequest", "Requesting permissions: ${permissions.joinToString()}")
        requestPermissionLauncher.launch(permissions)
    }

    private fun scheduleNotifications() {
        val notificationManager = NotificationManager(this)
        notificationManager.scheduleNotificationListFromApi()
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        val sharedPreferences = LocalContext.current.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = remember { mutableStateOf(sharedPreferences.getBoolean("isLoggedIn", false)) }
        var selectedTabIndex by remember { mutableIntStateOf(2) }
        var isTimelineView by remember { mutableStateOf(false) }

        var isNotificationEnabled by remember {
            mutableStateOf(sharedPreferences.getBoolean("isNotificationEnabled", false))
        }

        LaunchedEffect(isLoggedIn.value) {
            with(sharedPreferences.edit()) {
                putBoolean("isLoggedIn", isLoggedIn.value)
                apply()
            }
        }
        if (isDialogVisible) {
            ShowPermissionExplanationDialog { granted ->
                if (granted) {
                    requestNotificationPermissions()
                    with(sharedPreferences.edit()) {
                        putBoolean("isNotificationEnabled", true)
                        apply()
                    }
                }
                isDialogVisible = false
            }
        }

        // 현재 다크 모드 여부를 확인
        val isDarkTheme = isSystemInDarkTheme()

        MaterialTheme(
            colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            NavHost(navController, startDestination = if (isLoggedIn.value) "main" else "login") {
                composable("login") {
                    LoginView(navController, isLoggedIn, onError = {}) }
                composable("main") {
                    MainView(
                        navController,
                        selectedTabIndex,
                        isTimelineView,
                        onTabSelected = { newIndex -> selectedTabIndex = newIndex },
                        onTimelineViewChange = { newValue -> isTimelineView = newValue },
                        refreshEvents = { /* Refresh events logic */ },
                        isNotificationEnabled = isNotificationEnabled
                    )
                    BackHandler {
                        // 메인 화면에서 뒤로가기를 눌렀을 때 앱 종료
                        finish() // 현재 Activity 종료
                    }
                }
                composable("eventDetail/{eventJson}") { backStackEntry ->
                    val eventJson = backStackEntry.arguments?.getString("eventJson") ?: ""
                    EventDetailScreen(eventJson = eventJson, onClose = { navController.popBackStack() })
                }
                composable("timelineEventDetail/{eventJson}") { backStackEntry ->
                    val eventJson = backStackEntry.arguments?.getString("eventJson") ?: ""
                    TimelineEventDetailScreen(eventJson = eventJson, onClose = { Log.d("onClose", "onClose")
                        navController.popBackStack() }, onTabSelected = { Log.d("onTabSelected", "onTabSelected")
                        selectedTabIndex = it }, isTimelineView = isTimelineView,
                        refreshEvents = { Log.d("refreshEvents", "refreshEvents") })
                }
                composable("caution") {
                    CautionScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("Duplicate") {
                    DuplicateScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("DeleteByAdmin") {
                    DeleteByAdminScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("RequestAPIOver") {
                    RequestAPIOverScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("TryLoginFreq") {
                    TryLoginFreqScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("menu") {
                    MenuScreen(navController = navController, onTabSelected = { selectedTabIndex = it }, isNotificationEnabled = isNotificationEnabled, deleteAccount = {  })
                }
                composable("qr") { QRScreen(navController) }
                composable("mapDetail") { MapDetailScreen(navController) }
            }
        }
    }

    @Composable
    private fun ShowPermissionExplanationDialog(onRequestPermission: (Boolean) -> Unit) {
        AlertDialog(
            onDismissRequest = { onRequestPermission(false) },
            title = { Text("권한 요청") },
            text = {
                Column {
                    Text("알림을 표시하기 위해 '알림' 권한이 필요합니다.")
                    Text("설정에서 권한을 부여해주세요.")
                }
            },
            confirmButton = {
                Button(onClick = {
                    onRequestPermission(true) // 허용 버튼 클릭 시
                }) {
                    Text("허용")
                }
            },
            dismissButton = {
                Button(onClick = { onRequestPermission(false) }) {
                    Text("거부")
                }
            }
        )
    }
}
