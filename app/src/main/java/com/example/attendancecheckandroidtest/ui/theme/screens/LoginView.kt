package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.network.ApiService
import okhttp3.OkHttpClient
import androidx.compose.ui.platform.LocalFocusManager


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginView(navController: NavController, isLoggedIn: MutableState<Boolean>) {
//    var userId by remember { mutableStateOf("") }
//    var userName by remember { mutableStateOf("") }
//    var department by remember { mutableStateOf("") }
//    var errorMessage by remember { mutableStateOf("") }
//
//    // LocalContext 가져오기
//    val context = LocalContext.current
//    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
//    val client = OkHttpClient() // OkHttpClient 인스턴스 생성
//    val apiService = ApiService(client, context)
//
//    // 저장된 값 불러오기
//    userId = sharedPreferences.getString("userId", "") ?: ""
//    userName = sharedPreferences.getString("userName", "") ?: ""
//    department = sharedPreferences.getString("department", "") ?: "학과를 선택하세요"
//
//    val focusManager = LocalFocusManager.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(30.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(onTap = {
//                    focusManager.clearFocus() // 화면 터치 시 키보드 내림
//        })
//    },
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "👋 환영합니다!",
//            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(bottom = 40.dp),
//            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
//        )
//
//        Image(
//            painter = painterResource(id = R.drawable.sch_logo),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(15.dp))
//        )
//
//        // 학과 목록 정의
//        val departments =
//            listOf("컴퓨터소프트웨어공학과", "정보보호학과", "의료IT공학과", "AI·빅데이터학과", "사물인터넷학과", "메타버스&게임학과")
//        var selectedDepartment by remember { mutableStateOf(department) }
//        var expanded by remember { mutableStateOf(false) }
//
//        // 학과 선택 드롭다운 메뉴
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = { expanded = !expanded }
//        ) {
//            OutlinedTextField(
//                value = selectedDepartment,
//                onValueChange = {},
//                readOnly = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .menuAnchor()
//                    .padding(top = 50.dp)
//                    .height(62.dp),
//                label = { Text("학과") },
//                shape = RoundedCornerShape(12.dp),
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
//            )
//
//            // 드롭다운 메뉴
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                departments.forEach { department ->
//                    DropdownMenuItem(
//                        text = { Text(department) },
//                        onClick = {
//                            selectedDepartment = department
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
//
//        // 학번 입력 필드
//        var studentNumber by remember { mutableStateOf("") } // 초기값 설정
//        // 이름 입력 필드
//        var name by remember { mutableStateOf("") } // 초기값 설정
//
//        // 학번 입력 텍스트 필드
//        OutlinedTextField(
//            value = studentNumber,
//            onValueChange = { studentNumber = it },
//            label = { Text("학번 입력") },
//            shape = RoundedCornerShape(12.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 20.dp)
//                .height(62.dp),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            isError = errorMessage.isNotEmpty() && studentNumber.isNotEmpty(),
//            singleLine = true
//        )
//
//        // 이름 입력 텍스트 필드
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("이름 입력") },
//            shape = RoundedCornerShape(12.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 2.dp)
//                .height(62.dp),
//            singleLine = true // 줄바꿈 방지
//        )
//
//        // 오류 메시지 표시
//        if (errorMessage.isNotEmpty()) {
//            Text(
//                text = errorMessage,
//                color = Color.Red,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//
//        // 로그인 버튼
//        Button(
//            onClick = {
//                // 입력 검증
//                when {
//                    studentNumber.isEmpty() -> {
//                        errorMessage = "학번을 입력해주세요."
//                    }
//
//                    studentNumber.length != 8 -> {
//                        errorMessage = "학번 8자리를 입력하세요."
//                    }
//
//                    name.isEmpty() -> {
//                        errorMessage = "이름을 입력해주세요."
//                    }
//
//                    selectedDepartment == "학과를 선택하세요" -> {
//                        errorMessage = "학과를 선택해주세요."
//                    }
//
//                    else -> {
//                        // API를 통해 로그인
//                        apiService.login(
//                            studentNumber,
//                            name,
//                            selectedDepartment,
//                            onSuccess = { accessToken, refreshToken ->
//                                isLoggedIn.value = true
//                                sharedPreferences.edit().apply {
//                                    putBoolean("isLoggedIn", true)
//                                    putString("userId", studentNumber)
//                                    putString("userName", name)
//                                    putString("department", selectedDepartment)
//                                    putString("access_token", accessToken)
//                                    putString("refresh_token", refreshToken)
//                                    apply()
//                                }
//                                isLoggedIn.value = true
//                                navController.navigate("main")
//                            },
//                            onError = { error ->
//                                errorMessage = error // 에러 메시지 설정
//                            }
//                        )
//                    }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 30.dp)
//        ) {
//            Text("로그인")
//        }
//    }
//}
//
import androidx.activity.compose.BackHandler // 추가된 import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavController, isLoggedIn: MutableState<Boolean>) {
    var userId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // LocalContext 가져오기
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val client = OkHttpClient() // OkHttpClient 인스턴스 생성
    val apiService = ApiService(client, context)

    // 저장된 값 불러오기
    userId = sharedPreferences.getString("userId", "") ?: ""
    userName = sharedPreferences.getString("userName", "") ?: ""
    department = sharedPreferences.getString("department", "") ?: "학과를 선택하세요"

    // BackHandler 추가
    BackHandler {
        // 아무 동작도 하지 않음으로써 뒤로가기 버튼을 비활성화
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus() // 화면 터치 시 키보드 내림
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "👋 환영합니다!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 40.dp),
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Image(
            painter = painterResource(id = R.drawable.sch_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
        )

        // 학과 목록 정의
        val departments =
            listOf("컴퓨터소프트웨어공학과", "정보보호학과", "의료IT공학과", "AI·빅데이터학과", "사물인터넷학과", "메타버스&게임학과")
        var selectedDepartment by remember { mutableStateOf(department) }
        var expanded by remember { mutableStateOf(false) }

        // 학과 선택 드롭다운 메뉴
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedDepartment,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .padding(top = 50.dp)
                    .height(62.dp),
                label = { Text("학과") },
                shape = RoundedCornerShape(12.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            // 드롭다운 메뉴
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                departments.forEach { department ->
                    DropdownMenuItem(
                        text = { Text(department) },
                        onClick = {
                            selectedDepartment = department
                            expanded = false
                        }
                    )
                }
            }
        }

        // 학번 입력 필드
        var studentNumber by remember { mutableStateOf("") } // 초기값 설정
        // 이름 입력 필드
        var name by remember { mutableStateOf("") } // 초기값 설정

        // 학번 입력 텍스트 필드
        OutlinedTextField(
            value = studentNumber,
            onValueChange = { studentNumber = it },
            label = { Text("학번 입력") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(62.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage.isNotEmpty() && studentNumber.isNotEmpty(),
            singleLine = true
        )

        // 이름 입력 텍스트 필드
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름 입력") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .height(62.dp),
            singleLine = true // 줄바꿈 방지
        )

        // 오류 메시지 표시
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 로그인 버튼
        Button(
            onClick = {
                // 입력 검증
                when {
                    studentNumber.isEmpty() -> {
                        errorMessage = "학번을 입력해주세요."
                    }

                    studentNumber.length != 8 -> {
                        errorMessage = "학번 8자리를 입력하세요."
                    }

                    name.isEmpty() -> {
                        errorMessage = "이름을 입력해주세요."
                    }

                    selectedDepartment == "학과를 선택하세요" -> {
                        errorMessage = "학과를 선택해주세요."
                    }

                    else -> {
                        // API를 통해 로그인
                        apiService.login(
                            studentNumber,
                            name,
                            selectedDepartment,
                            onSuccess = { accessToken, refreshToken ->
                                isLoggedIn.value = true
                                sharedPreferences.edit().apply {
                                    putBoolean("isLoggedIn", true)
                                    putString("userId", studentNumber)
                                    putString("userName", name)
                                    putString("department", selectedDepartment)
                                    putString("access_token", accessToken)
                                    putString("refresh_token", refreshToken)
                                    apply()
                                }
                                isLoggedIn.value = true
                                navController.navigate("main")
                            },
                            onError = { error ->
                                errorMessage = error // 에러 메시지 설정
                            }
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            Text("로그인")
        }
    }
}
