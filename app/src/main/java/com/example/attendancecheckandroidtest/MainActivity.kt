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

        checkAndRequestPermissions()

        setContent {
            MyApp()
        }
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
                composable("RequestAPIOver") { //430
                    RequestAPIOverScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("TryLoginFreq") {    //429
                    TryLoginFreqScreen(
                        onClose = { navController.popBackStack() },
                        onTabSelected = { selectedTabIndex = it },
                        navController = navController,
                        deleteAccount = { /* TODO: 여기서 계정 삭제 */ }
                    )
                }
                composable("TokenOver") {   //401
                    TokenOverScreen(
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
