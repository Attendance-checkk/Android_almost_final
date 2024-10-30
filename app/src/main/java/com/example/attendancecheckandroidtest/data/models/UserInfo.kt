package com.example.attendancecheckandroidtest.data.models

// 사용자 정보 데이터 모델 클래스
data class UserInfo(
    val id: Int, // 사용자 ID
    val studentCode: String, // 학생 코드
    val major: String, // 전공
    val name: String, // 이름
    val participantCount: Int, // 참가자 수
    val createdAt: String, // 생성 날짜
    val deletedAt: String? // 삭제 날짜 (nullable)
)