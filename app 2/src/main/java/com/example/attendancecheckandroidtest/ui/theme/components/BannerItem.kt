package com.example.attendancecheckandroidtest.ui.theme.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.attendancecheckandroidtest.data.models.Banner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.border

// 배너 아이템을 표시하는 Composable 함수
@Composable
fun BannerItem(banner: Banner, onBannerClick: (String) -> Unit) {
    val context = LocalContext.current // Composable 내에서 Context를 가져옴

    Box(
        modifier = Modifier
            .fillMaxWidth() // 너비를 최대한 채움
            .height(120.dp) // 높이 설정
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White) // 배경색 흰색
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp)) // 테두리 추가
            .clickable {
                Log.d("BannerItem", "Banner clicked: ${banner.bannerURL}") // 배너 클릭 로그
                onBannerClick(banner.bannerURL) // 클릭 시 URL 전달
            },
    ) {
        // 배너 이미지 리소스 ID 가져오기
        val resId = context.resources.getIdentifier(banner.bannerImageName, "drawable", context.packageName)
        if (resId != 0) {
            // 이미지가 존재하는 경우
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize() // Box 크기에 맞춰 이미지 크기 조정
            )
        } else {
            // 이미지가 존재하지 않는 경우
            Log.e("BannerItem", "Image not found for: ${banner.bannerImageName}")
            Text(
                text = "이미지를 찾을 수 없습니다.", // 오류 메시지 표시
                modifier = Modifier.fillMaxSize(),
                color = Color.Red,
                textAlign = TextAlign.Center // 텍스트 중앙 정렬
            )
        }
    }
}

// Preview 추가
@Preview(showBackground = true)
@Composable
fun BannerItemPreview() {
    // 더미 배너 객체 생성
    val dummyBanner = Banner(
        bannerTitle = "더미 배너",
        bannerImageName = "swcuaf_banner_1", // 실제 drawable 리소스 이름으로 변경
        bannerURL = "https://example.com" // 더미 URL
    )

    // BannerItem 호출
    BannerItem(banner = dummyBanner) { url ->
        Log.d("BannerItemPreview", "Banner clicked: $url") // 클릭 시 로그
    }
}
