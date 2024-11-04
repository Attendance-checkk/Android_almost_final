package com.example.attendancecheckandroidtest.data.models

// 배너 데이터 모델 클래스
data class Banner(
    val bannerTitle: String, // 배너 제목
    val bannerImageName: String, // 배너 이미지 파일 이름
    val bannerURL: String // 배너 클릭 시 이동할 URL
)
