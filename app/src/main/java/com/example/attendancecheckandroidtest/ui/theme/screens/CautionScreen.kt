package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.data.network.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

@Composable
fun CautionScreen(onClose: () -> Unit, onTabSelected: (Int) -> Unit, navController: NavController, deleteAccount: () -> Unit) {
    var isButtonEnabled by remember { mutableStateOf(true) } // 버튼 활성화 상태
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
//    val client = OkHttpClient() // OkHttpClient 인스턴스 생성
//    val apiService = ApiService(client, context)
    val apiService = ApiService(context, client2 = OkHttpClient())
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

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
            text = "계정은 한 번 삭제하면 복구할 수 없습니다.\n계정 삭제 옵션은 개인정보(학과, 학번, 이름)를 잘못 입력하신 경우에만 사용해 주시기 바랍니다.\n로그아웃 기능은 그리고 악용될 소지가 있어 제공하지 않습니다.\n\n관리자와 상의 없이 계정을 삭제하신 경우, 참가가 가능한 시간이 지난 이벤트의 스탬프는 다시 찍어 드릴 수 없습니다.\n따라서 학생 여러분께서는 계정에 문제가 있으실 경우, 계정을 삭제하시기 전에 반드시 관리자에게 문의하시기 바랍니다.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 16.dp), // 제목과 설명 사이의 간격 조정
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // 버튼 간의 공간을 분배
        ) {
            Button(
                onClick = {
                    // 계정 삭제 버튼 클릭 시 로직
                    Log.d("CautionView", "Account deletion button clicked")
                    showDeleteConfirmationDialog = true
                    // 추가적인 삭제 로직 추가
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .weight(1f) // 버튼 너비를 균등하게 설정
                    .padding(end = 8.dp) // 오른쪽 버튼과의 간격
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Text("🗑️ 계정삭제", color = Color.White)
            }

            Button(
                onClick = {
                    Log.d("CautionView", "Contact button clicked")
                    val url = "https://potent-barnacle-025.notion.site/FAQ-116c07204d29805a8418d9a37bf330a2?pvs=4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent) // 외부 브라우저에서 URL 열기
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F8CFF)),
                modifier = Modifier
                    .weight(1f) // 버튼 너비를 균등하게 설정
                    .padding(start = 8.dp) // 왼쪽 버튼과의 간격
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게
            ) {
                Text("🙋‍♂️ 문의하기", color = Color.White) // 텍스트 색상 설정
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF72C6EF) else Color(0xFF26539C)),
            onClick = {
                if (isButtonEnabled) {
                    Log.d("CautionView", "Close button clicked") // 버튼 클릭 로그
                    isButtonEnabled = false // 버튼 비활성화
                    onClose() // 이전 화면으로 돌아가고 -> TODO: 여기에서 navController로 나가는데 문제가 있음
                    onTabSelected(4) // '메뉴' 탭으로 변경

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

    // 계정 삭제 확인 다이얼로그
    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text("계정 삭제 확인") },
            text = { Text("정말로 계정을 삭제하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val accessToken = sharedPreferences.getString("access_token", null)

                    if (accessToken != null) {
                        apiService.deleteAccount(accessToken, onSuccess = {
                            // 계정 삭제 후 로그인 상태 업데이트
                            with(sharedPreferences.edit()) {
                                putBoolean("isLoggedIn", false) // 로그인 상태를 false로 설정
                                remove("access_token") // 액세스 토큰 삭제
                                apply()
                            }
                            deleteAccount() // 계정 삭제 후 처리
                            navController.navigate("login") // 로그인 화면으로 이동
                        }, onError = { error ->
                            errorMessage = error // 에러 메시지 설정
                        })
                    } else {
                        errorMessage = "액세스 토큰이 없습니다."
                    }
                    showDeleteConfirmationDialog = false // 다이얼로그 닫기
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmationDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CautionPreview() {
    CautionScreen(onClose = { /* Handle close action in preview if needed */ }, onTabSelected = { /* Handle tab selection in preview if needed */ }, navController = rememberNavController(), deleteAccount = {})
}
