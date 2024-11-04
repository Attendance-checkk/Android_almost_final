
package com.example.attendancecheckandroidtest.ui.theme.screens

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.attendancecheckandroidtest.R
import com.example.attendancecheckandroidtest.data.network.ApiService
import okhttp3.OkHttpClient
import androidx.compose.ui.platform.LocalFocusManager
import androidx.activity.compose.BackHandler // 추가된 import
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavController, isLoggedIn: MutableState<Boolean>, onError: (String) -> Unit) {
    var studentNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedDepartment by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") } // 비밀번호 상태 변수
    var confirmPassword by remember { mutableStateOf("") } // 비밀번호 재입력 상태 변수 추가
    var errorMessage by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    // LocalContext 가져오기
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val apiService = ApiService(context, client2 = OkHttpClient())

    var showAlertDialog by remember { mutableStateOf(false) }
    var alertDialogTitle by remember { mutableStateOf("") }
    var alertDialogMessage by remember { mutableStateOf("") }

    // BackHandler 추가
    BackHandler {
        // 아무 동작도 하지 않음으로써 뒤로가기 버튼을 비활성화
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus() // 화면 터치 시 키보드 내림
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            text = "👋 환영합니다!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 40.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Image(
            painter = painterResource(id = R.drawable.sch_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
        )

        // 학과 목록 정의
        val departments = listOf("컴퓨터소프트웨어공학과", "정보보호학과", "의료IT공학과", "AI·빅데이터학과", "사물인터넷학과", "메타버스&게임학과")
        var expanded by remember { mutableStateOf(false) }

        Text(
            "학과 선택",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

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
                    .padding(top = 5.dp)
                    .height(70.dp),
                label = { Text("학과를 선택해주세요") },
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

        Text(
            "학번 입력",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 20.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        // 학번 입력 텍스트 필드 (숫자만 입력 가능)
        OutlinedTextField(
            value = studentNumber,
            onValueChange = {
                // 숫자만 입력하도록 제한
                if (it.all { char -> char.isDigit() }) {
                    studentNumber = it
                }
            },
            label = { Text("학번 입력") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 5.dp)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage.isNotEmpty() && studentNumber.isNotEmpty(),
            singleLine = true
        )

        Text(
            "이름 입력",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 20.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        // 이름 입력 텍스트 필드 (스페이스바 방지)
        OutlinedTextField(
            value = name,
            onValueChange = {
                // 스페이스바가 포함되지 않도록 제한
                if (!it.contains(" ")) {
                    name = it
                }
            },
            label = { Text("이름 입력") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 5.dp)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            singleLine = true
        )

        Text(
            "비밀번호",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 20.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호를 입력해주세요") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 5.dp)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii), // 아스키 키보드 설정
            isError = errorMessage.isNotEmpty() && password.isNotEmpty(),
            singleLine = true
        )

        Text(
            "대•소•특수문자/숫자 포함 8자리 이상 작성해주세요!",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 5.dp),
            color = MaterialTheme.colorScheme.onSurface
        )


        Text(
            "비밀번호 재확인",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp)
                .padding(top = 20.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("비밀번호를 재입력해주세요") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 5.dp)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii), // 아스키 키보드 설정
            isError = errorMessage.isNotEmpty() && confirmPassword.isNotEmpty(),
            singleLine = true
        )
        // 오류 메시지 표시
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
            )
        }

        // 로그인 버튼
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26539C)),
            onClick = {
                val validationResult = validateInputs(
                    studentNumber,
                    name,
                    selectedDepartment,
                    password,
                    confirmPassword // 비밀번호 재입력 추가
                )

                when {
                    studentNumber.isEmpty() -> {
                        onError("학번을 입력해주세요.")
                    }
                    studentNumber.length != 8 -> {
                        onError("학번 8자리를 입력하세요.")
                    }
                    name.isEmpty() -> {
                        onError("이름을 입력해주세요.")
                    }
                    selectedDepartment.isEmpty() -> {
                        onError("학과를 선택해주세요.")
                    }
                    password.isEmpty() -> {
                        onError("비밀번호를 입력해주세요.")
                    }
                    else -> {
                        if (validationResult.first) {
                            alertDialogTitle = "입력된 정보 확인"
                            alertDialogMessage = "입력한 정보가 맞으신가요?\n입력 정보가 다를 시 불이익이 있을 수 있습니다!"
                            showConfirmationDialog = true
                        } else {
                            alertDialogTitle = "입력 오류"
                            alertDialogMessage = validationResult.second
                            showAlertDialog = true
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Text("로그인",
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center // 중앙 정렬
        ) {
            // 문의하기 버튼
            PlainTextButton(
                text = "도움이 필요하신가요?",
                onClick = {
                    val url = "https://potent-barnacle-025.notion.site/FAQ-116c07204d29805a8418d9a37bf330a2?pvs=4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent) // 외부 브라우저에서 URL 열기
                },
                textColor = Color(0xFF26539C)
            )
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text(alertDialogTitle) },
            text = { Text(alertDialogMessage) },
            confirmButton = {
                Button(onClick = {
                    showConfirmationDialog = false
                    performLogin(
                        studentNumber,
                        name,
                        selectedDepartment,
                        password,
                        navController,
                        sharedPreferences,
                        apiService,
                        onError = { errorMessage = it },
                        onSuccess = {
                            alertDialogTitle = "로그인 성공"
                            alertDialogMessage = String.format("안녕하세요! %s님!", name)
                            showAlertDialog = true
                        },
                        onClearInputs = {
                            studentNumber = ""
                            name = ""
                            selectedDepartment = ""
                            password = ""
                            confirmPassword = ""
                            errorMessage = ""
                        }
                    )

                    alertDialogTitle = "로그인 성공"
                    alertDialogMessage = String.format("안녕하세요! %s님!", name)
                    showAlertDialog = true
                }) {
                    Text("로그인")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    // AlertDialog 표시
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text(alertDialogTitle) },
            text = { Text(alertDialogMessage) },
            confirmButton = {
                Button(onClick = {
                    showAlertDialog = false
                    if (alertDialogTitle == "로그인 성공") {
                        navController.navigate("main")
                    }
                }) {
                    Text("확인")
                }
            }
        )
    }
}

// 나머지 함수는 동일하게 유지

private fun validateInputs(
    studentNumber: String,
    name: String,
    selectedDepartment: String,
    password: String,
    confirmPassword: String // 비밀번호 재입력 추가
): Pair<Boolean, String> {
    // 학과 유효성 검사
    if (selectedDepartment.isEmpty()) {
        return Pair(false, "학과를 선택해주세요.")
    }

    // 학번 유효성 검사
    if (studentNumber.isEmpty()) {
        return Pair(false, "학번을 입력해주세요.")
    }
    if (studentNumber.length != 8) {
        return Pair(false, "학번은 8자리여야 합니다.")
    }
    if (!studentNumber.all { it.isDigit() }) {
        return Pair(false, "학번은 숫자만 포함해야 합니다.")
    }
    if (!studentNumber.startsWith("201") && !studentNumber.startsWith("2020") &&
        !studentNumber.startsWith("2021") && !studentNumber.startsWith("2022") &&
        !studentNumber.startsWith("2023") &&         !studentNumber.startsWith("2024")) {
        return Pair(false, "학번은 201, 2020, 2021, 2022, 2023, 또는 2024로 시작해야 합니다.")
    }

    // 이름 유효성 검사
    if (name.isEmpty()) {
        return Pair(false, "이름을 입력해주세요.")
    }
    if (name.any { it.isDigit() }) {
        return Pair(false, "이름에 숫자가 포함될 수 없습니다.")
    }
    if (name.any { !it.isLetter() && !it.isWhitespace() }) {
        return Pair(false, "이름에 특수문자가 포함될 수 없습니다.")
    }

    // 비밀번호 유효성 검사
    if (password.isEmpty()) {
        return Pair(false, "비밀번호를 입력해주세요.")
    }
    if (password != confirmPassword) { // 비밀번호와 재입력된 비밀번호 비교
        return Pair(false, "비밀번호가 일치하지 않습니다.")
    }
    if (!isValidPassword(password)) {
        return Pair(false, "비밀번호는 대문자, 소문자, 특수문자를 포함하고 8자리 이상이어야 합니다.")
    }

    return Pair(true, "")
}

private fun isValidPassword(password: String): Boolean {
    val upperCaseRegex = Regex("[A-Z]")
    val lowerCaseRegex = Regex("[a-z]")
    val specialCharRegex = Regex("[!@#$%^&*(),.?\":{}|<>]")
    return password.length >= 8 &&
            upperCaseRegex.containsMatchIn(password) &&
            lowerCaseRegex.containsMatchIn(password) &&
            specialCharRegex.containsMatchIn(password)
}

private fun performLogin(
    studentNumber: String,
    name: String,
    selectedDepartment: String,
    password: String,
    navController: NavController,
    sharedPreferences: SharedPreferences,
    apiService: ApiService,
    onError: (String) -> Unit,
    onSuccess: () -> Unit,
    onClearInputs: () -> Unit
) {
    // API를 통해 로그인
    apiService.login(
        studentNumber,
        name,
        selectedDepartment,
        password,
        onSuccess = { accessToken, refreshToken ->
            // 로그인 성공 시 처리
            sharedPreferences.edit().apply {
                putBoolean("isLoggedIn", true)
                putString("userId", studentNumber)
                putString("userName", name)
                putString("department", selectedDepartment)
                putString("access_token", accessToken)
                putString("refresh_token", refreshToken)
                apply()
            }
            onSuccess()
            onClearInputs()
        },
        onError = { error ->
            onError(error) // 에러 메시지 설정
        }
    )
}

@Composable
fun PlainTextButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.Blue
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth() // 버튼이 가능한 최대 너비를 차지하도록 설정
            .padding(16.dp) // 텍스트 주변에 패딩 추가
            .clickable(onClick = onClick) // 클릭 가능한 영역 설정
            .wrapContentSize(Alignment.Center), // 텍스트를 중앙에 정렬
        color = textColor, // 텍스트 색상 설정
        textAlign = TextAlign.Center
    )
}
