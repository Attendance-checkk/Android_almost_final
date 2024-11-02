package com.example.attendancecheckandroidtest.ui.theme.screens

import android.Manifest
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.attendancecheckandroidtest.data.network.ApiService
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient

@Composable
fun QRScreen(navController: NavController) {
    val context = LocalContext.current
//    val apiService = remember { ApiService(OkHttpClient(), context) }
    val apiService = remember { ApiService(context, OkHttpClient()) }
    var qrCodeValue by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var alertTitle by remember { mutableStateOf("") }
    var alertBody by remember { mutableStateOf("") }
    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    var isCameraActive by remember { mutableStateOf(true) } // 기본적으로 카메라 활성화 상태

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isCameraPermissionGranted = isGranted
        if (!isGranted) {
            showAlert = true
            alertTitle = "권한 필요"
            alertBody = "카메라 권한이 필요합니다."
        }
    }

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (isProcessing) {
        LaunchedEffect(qrCodeValue) {
            delay(1000) // 1초 대기
            isProcessing = false // 처리 완료 후 상태 변경
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QR코드 인식",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 20.dp),
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface, // 다크 모드에 맞는 텍스트 색상
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(30.dp)
                .clip(RoundedCornerShape(15.dp))
                .let { modifier ->
                    if (isCameraPermissionGranted && isCameraActive) {
                        modifier.border(BorderStroke(2.dp, Color.Blue), shape = RoundedCornerShape(5.dp))
                    } else {
                        modifier // 카메라가 꺼져 있을 때는 border 없앰
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (isCameraPermissionGranted && isCameraActive) {
                CameraPreview(
                    onBarcodeScanned = { barcode ->
                        if (isProcessing) return@CameraPreview // 이미 처리 중인 경우 무시

                        qrCodeValue = barcode
                        isProcessing = true

                        apiService.sendQrCodeToServer(qrCodeValue) { statusCode, message ->
                            isProcessing = false
                            when (statusCode) {
                                200 -> {
                                    alertTitle = "인식이 완료되었습니다!"
                                    alertBody = "스탬프를 찍었어요! 🥳"
                                }
                                401 -> {
                                    alertTitle = "이미 인식한 코드입니다."
                                    alertBody = "인식한 코드는 다시 인식할 수 없어요 🥲"
                                }
                                402 -> {
                                    alertTitle = "코드 형식이 맞지 않습니다!"
                                    alertBody = "코드가 이상한 것 같아요 🤔"
                                }
                                else -> {
                                    alertTitle = "알 수 없는 오류가 발생했습니다"
                                    alertBody = "관리자에게 문의해주세요..!"
                                }
                            }
                            showAlert = true
                            isCameraActive = false // QR 코드 인식 후 카메라 종료
                        }
                    }
                )
            } else if (!isCameraActive) {
                Text("다시 QR코드를 인식하시려면 버튼을 눌러주세요!", textAlign = TextAlign.Center)
            } else {
                Text("카메라 권한을 요청 중입니다...")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!isCameraActive) {
                    isCameraActive = true // 카메라 활성화
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = if (isCameraActive) "QR코드 인식 중..." else "QR코드 인식 시작")
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text(alertTitle) },
            text = { Text(alertBody) },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun CameraPreview(onBarcodeScanned: (String) -> Unit) {
    val localContext = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(localContext) }
    val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        },
        update = { previewView ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(localContext)) { imageProxy ->
                    processImageProxy(barcodeScanner, imageProxy, onBarcodeScanned)
                }

                val lifecycleOwner = localContext as? LifecycleOwner
                lifecycleOwner?.let {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(it, cameraSelector, preview, imageAnalysis)
                }

            }, ContextCompat.getMainExecutor(localContext))
        }
    )
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(barcodeScanner: BarcodeScanner, imageProxy: ImageProxy, onBarcodeScanned: (String) -> Unit) {
    try {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    // QR 코드가 인식되면 추가 인식을 방지
                    if (barcodes.isNotEmpty()) {
                        val barcode = barcodes[0] // 첫 번째 QR 코드만 가져옴
                        Log.d("QRScreen", "QR Code Scanned: ${barcode.rawValue}")
                        onBarcodeScanned(barcode.rawValue ?: "")

                        // imageProxy를 닫음
                        imageProxy.close()
                    } else {
                        imageProxy.close() // QR 코드가 없으면 닫기
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QRScreen", "Barcode scanning failed: ${e.message}")
                    imageProxy.close()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            Log.e("QRScreen", "Media image is null")
            imageProxy.close()
        }
    } catch (e: Exception) {
        Log.e("QRScreen", "Error processing image proxy: ${e.message}")
        imageProxy.close()
    }
}