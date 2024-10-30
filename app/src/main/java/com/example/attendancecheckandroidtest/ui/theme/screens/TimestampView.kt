package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.R

// TimestampView 정의
@Composable
fun TimestampView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // 내부 여백 설정
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 정렬
        verticalArrangement = Arrangement.Center // 중앙 정렬
    ) {
        // 캘린더 이미지 표시
        Image(
            painter = painterResource(id = R.drawable.swcuaftimetable), // 캘린더 이미지 사용
            contentDescription = "Calendar Image", // 이미지 설명
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit // 이미지 비율 맞춤
        )
    }
}