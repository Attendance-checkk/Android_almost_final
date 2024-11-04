package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import okhttp3.OkHttpClient

@Composable
fun TryLoginFreqScreen(onClose: () -> Unit, onTabSelected: (Int) -> Unit, navController: NavController, deleteAccount: () -> Unit) {
    var isButtonEnabled by remember { mutableStateOf(true) } // 버튼 활성화 상태
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    //DuplicateScreen
    val context = LocalContext.current
//    val client = OkHttpClient() // OkHttpClient 인스턴스 생성
//    val apiService = ApiService(client, context)
    val apiService = ApiService(context, client2 = OkHttpClient())
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)


    Column(
        modifier = Modifier
            .fillMaxSize() // 전체 화면을 채우도록 수정
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // 위아래로 공간을 분배
    ) {
        Text(
            text = "⚠️ 로그인 회수 초과",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Text(
            text = "로그인 회수가 초과 되었습니다.\n잠시 후 다시 사용 가능합니다.",
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
                    //
                    navController.navigate("map")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .weight(1f) // 버튼 너비를 균등하게 설정ㅌ
                    .padding(end = 8.dp) // 오른쪽 버튼과의 간격
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Text("◀️ 돌아가기", color = Color.White)
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
    }
}

@Preview(showBackground = true)
@Composable
fun TryLoginFreqScreenPreview() {
    TryLoginFreqScreen(onClose = { /* Handle close action in preview if needed */ }, onTabSelected = { /* Handle tab selection in preview if needed */ }, navController = rememberNavController(), deleteAccount = {})
}