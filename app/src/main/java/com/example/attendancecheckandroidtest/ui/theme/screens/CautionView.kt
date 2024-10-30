//package com.example.attendancecheckandroidtest.ui.theme.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun CautionView(onClose: () -> Unit, onTabSelected: (Int) -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize() // 전체 화면을 채우도록 수정
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween // 위아래로 공간을 분배
//    ) {
//        Text(
//            text = "계정 삭제 주의사항",
//            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
//            textAlign = TextAlign.Start,
//            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
//        )
//
//        Text(
//            text = "계정을 한 번 삭제하면 복구할 수 없습니다.\n계정 삭제 옵션은 개인정보(학과, 학번, 이름)를 잘못 입력하신 경우에만 사용해주시기 바랍니다.",
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Start,
//            modifier = Modifier.padding(top = 16.dp), // 제목과 설명 사이의 간격 조정
//            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
//        )
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // 닫기 버튼을 아래쪽에 배치
//        Button(
//            onClick = {
//                onClose() // 이전 화면으로 돌아가고
//                onTabSelected(4) // '일정' 탭으로 변경
//            },
//            modifier = Modifier.padding(bottom = 16.dp)
//        ) {
//            Text("닫기")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CautionPreview() {
//    CautionView(onClose = { /* Handle close action in preview if needed */ }, onTabSelected = { /* Handle tab selection in preview if needed */ })
//}

package com.example.attendancecheckandroidtest.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CautionView(onClose: () -> Unit, onTabSelected: (Int) -> Unit) {
    var isButtonEnabled by remember { mutableStateOf(true) } // 버튼 활성화 상태
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성

    Column(
        modifier = Modifier
            .fillMaxSize() // 전체 화면을 채우도록 수정
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // 위아래로 공간을 분배
    ) {
        Text(
            text = "계정 삭제 주의사항",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Text(
            text = "계정을 한 번 삭제하면 복구할 수 없습니다.\n계정 삭제 옵션은 개인정보(학과, 학번, 이름)를 잘못 입력하신 경우에만 사용해주시기 바랍니다.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 16.dp), // 제목과 설명 사이의 간격 조정
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (isButtonEnabled) {
                    Log.d("CautionView", "Button clicked") // 버튼 클릭 로그
                    isButtonEnabled = false // 버튼 비활성화
                    onClose() // 이전 화면으로 돌아가고
                    onTabSelected(4) // '일정' 탭으로 변경

                    // 일정 시간 후 버튼 재활성화
                    coroutineScope.launch {
                        delay(10000) // 10초 대기
                        isButtonEnabled = true // 버튼 활성화
                        Log.d("CautionView", "Button re-enabled") // 버튼 재활성화 로그
                    }
                } else {
                    Log.d("CautionView", "Button was already disabled") // 버튼 비활성화 상태 로그
                }
            },
            enabled = isButtonEnabled, // 버튼 활성화 상태 설정
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("닫기")
        }
    }
    }

@Preview(showBackground = true)
@Composable
fun CautionPreview() {
    CautionView(onClose = { /* Handle close action in preview if needed */ }, onTabSelected = { /* Handle tab selection in preview if needed */ })
}
