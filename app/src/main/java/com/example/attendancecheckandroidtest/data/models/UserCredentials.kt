package com.example.attendancecheckandroidtest.data.models

// 사용자 정보와 JWT 토큰을 포함하는 데이터 모델 클래스
data class UserCredentials(
    val userInfo: UserInfo, // 사용자 정보
    val accessToken: String, // 액세스 토큰
    val refreshToken: String // 리프레시 토큰
)
