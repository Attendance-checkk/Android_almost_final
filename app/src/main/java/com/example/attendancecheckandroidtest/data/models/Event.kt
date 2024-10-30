package com.example.attendancecheckandroidtest.data.models

// 참가자 데이터 모델 클래스
data class Participant(
    val id: Int,
    val createdAt: String,
    val userId: Int,
    val eventCode: String
)

// 이벤트 데이터 모델 클래스
data class Event(
    val eventCode: String, // 이벤트 코드
    val eventName: String, // 이벤트 이름
    val description: String, // 이벤트 설명
    val location: String, // 이벤트 장소
    val eventStartTime: String, // 이벤트 시작 시간
    val eventEndTime: String, // 이벤트 종료 시간
    val createdAt: String, // 생성 시간
    val participants: List<Participant>? // 참가자 리스트 (옵셔널)
)
