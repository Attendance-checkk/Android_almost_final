package com.example.attendancecheckandroidtest.ui.theme.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendancecheckandroidtest.R
//
//@Composable
//fun MapScreen(navController: NavController) {
//    val imageRes = R.drawable.sch_map
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "지도",
//            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(top = 20.dp),
//            color = Color.Black
//        )
//
//        Text(
//            text = "🔎 이미지를 클릭해보세요!",
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.padding(top = 8.dp),
//            color = Color.Black
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = imageRes),
//                contentDescription = "지도",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .background(Color.Gray, shape = RoundedCornerShape(16.dp))
//                    .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
//                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
//                    .clickable {
//                        navController.navigate("mapDetail") // "mapDetail"로 이동
//                    },
//                contentScale = ContentScale.Fit
//            )
//        }
//    }
//}
@Composable
fun MapScreen(navController: NavController) {
    val imageRes = R.drawable.sch_map

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "지도",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 20.dp),
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Text(
            text = "🔎 이미지를 클릭해보세요!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface // 다크 모드에 맞는 텍스트 색상
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "지도",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("mapDetail")
                    },
                contentScale = ContentScale.Fit
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen(navController = rememberNavController())
}
