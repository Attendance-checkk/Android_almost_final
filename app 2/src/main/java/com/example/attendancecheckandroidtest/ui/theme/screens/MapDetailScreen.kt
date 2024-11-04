//package com.example.attendancecheckandroidtest.ui.theme.screens
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.gestures.detectTransformGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.attendancecheckandroidtest.R
//
//@Composable
//fun MapDetailScreen(navController: NavController) {
//    val imageRes = R.drawable.sch_map
//
//    var scale by remember { mutableFloatStateOf(1f) }
//    var offsetX by remember { mutableFloatStateOf(0f) }
//    var offsetY by remember { mutableFloatStateOf(0f) }
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize().background(Color.Gray)
//    ) {
//        Image(
//            painter = painterResource(id = imageRes),
//            contentDescription = "지도 상세",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
//                .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
//                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onDoubleTap = {
//                            scale = if (scale > 1.0) 1.0f else 1.7f
//                            offsetX = 0f
//                            offsetY = 0f
//                        }
//                    )
//                }
//                .pointerInput(Unit) {
//                    detectTransformGestures { _, pan, zoom, _ ->
//                        scale *= zoom
//                        scale = scale.coerceIn(1f, 1.7f)
//
//                        offsetX += pan.x * scale
//                        offsetY += pan.y * scale
//
//                        val maxOffsetX = (size.width * scale - size.width) / 2f
//                        val maxOffsetY = (size.height * scale - size.height) / 2f
//
//                        offsetX = offsetX.coerceIn(-maxOffsetX, maxOffsetX)
//                        offsetY = offsetY.coerceIn(-maxOffsetY, maxOffsetY)
//                    }
//                }
//                .graphicsLayer(
//                    scaleX = scale,
//                    scaleY = scale,
//                    translationX = offsetX,
//                    translationY = offsetY,
//                ),
//            contentScale = ContentScale.Fit
//        )
//
//        // 닫기 버튼을 아래쪽에 배치
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Button(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Text("닫기")
//            }
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun MapDetailScreenPreview() {
//    MapDetailScreen(navController = rememberNavController())
//}

package com.example.attendancecheckandroidtest.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MapDetailScreen(navController: NavController) {
    val imageRes = R.drawable.sch_map

    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    // 버튼 활성화 상태
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else Color.Gray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > 1.0) 1.0f else 1.7f
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    scale = scale.coerceIn(1f, 1.7f)

                    offsetX += pan.x * scale

                    // offsetY는 항상 0으로 고정
                    offsetY = 0f

                    // 최대 오프셋 계산
                    val maxOffsetX = (size.width * scale - size.width) / 2f

                    offsetX = offsetX.coerceIn(-maxOffsetX, maxOffsetX)
                }
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX,
                translationY = offsetY,
            )
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "지도 상세",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )

        // 닫기 버튼을 아래쪽에 배치
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C)),
                onClick = {
                    if (isButtonEnabled) {
                        Log.d("MapDetailScreen", "Close button clicked") // 버튼 클릭 로그
                        isButtonEnabled = false // 버튼 비활성화

                        // 비동기 네비게이션 처리
                        coroutineScope.launch {
                            navController.popBackStack() // 이전 화면으로 돌아가고

                            // 일정 시간 후 버튼 재활성화
                            delay(10000) // 10초 대기
                            isButtonEnabled = true // 버튼 활성화
                            Log.d("MapDetailScreen", "Close button re-enabled") // 버튼 재활성화 로그
                        }
                    } else {
                        Log.d("MapDetailScreen", "Close button was already disabled") // 버튼 비활성화 상태 로그
                    }
                },
                enabled = isButtonEnabled, // 버튼 활성화 상태 설정
                modifier = Modifier.padding(16.dp)
            ) {
                Text("닫기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapDetailScreenPreview() {
    MapDetailScreen(navController = rememberNavController())
}
