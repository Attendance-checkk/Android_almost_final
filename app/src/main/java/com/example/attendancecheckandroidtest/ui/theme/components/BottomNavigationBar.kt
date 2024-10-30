package com.example.attendancecheckandroidtest.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.R

// 하단 내비게이션 바를 표시하는 Composable 함수
@Composable
fun BottomNavigationBar(
    onMapClick: () -> Unit, // 지도 클릭 시 호출되는 콜백
    onEventClick: () -> Unit, // 이벤트 클릭 시 호출되는 콜백
    onHomeClick: () -> Unit, // 홈 클릭 시 호출되는 콜백
    onMenuClick: () -> Unit, // 메뉴 클릭 시 호출되는 콜백
    onCalendarClick: () -> Unit, // 캘린더 클릭 시 호출되는 콜백
    onQrScanClick: () -> Unit // QR 스캔 클릭 시 호출될 콜백
) {
    BottomAppBar {
        // QR 스캔 버튼
        IconButton(onClick = { onQrScanClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_qr), contentDescription = "QR") // QR 아이콘
        }
        Spacer(modifier = Modifier.weight(1f)) // 아이콘 사이에 여백 추가

        // 지도 버튼
        IconButton(onClick = { onMapClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_map), contentDescription = "Map") // 지도 아이콘
        }
        Spacer(modifier = Modifier.weight(1f)) // 아이콘 사이에 여백 추가

        // 홈 버튼
        IconButton(onClick = { onHomeClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "Home") // 홈 아이콘
        }
        Spacer(modifier = Modifier.weight(1f)) // 아이콘 사이에 여백 추가

        // 캘린더 버튼
        IconButton(onClick = { onCalendarClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "Calendar") // 캘린더 아이콘
        }
        Spacer(modifier = Modifier.weight(1f)) // 아이콘 사이에 여백 추가

        // 메뉴 버튼
        IconButton(onClick = { onMenuClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu") // 메뉴 아이콘
        }
    }
}
