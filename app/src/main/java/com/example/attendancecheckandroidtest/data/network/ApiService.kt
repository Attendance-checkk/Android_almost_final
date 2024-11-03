
package com.example.attendancecheckandroidtest.data.network


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.attendancecheckandroidtest.data.models.Event
import com.example.attendancecheckandroidtest.data.models.Participant
import com.example.attendancecheckandroidtest.data.models.UserInfo
import com.google.android.datatransport.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


// API 서비스 클래스: 네트워크 요청을 처리
class ApiService(private val context: Context,private val client2: OkHttpClient) {
//class ApiService(private val context: Context,private val client: OkHttpClient) {

    private val client: OkHttpClient by lazy {
        // 모든 SSL 인증서를 신뢰하도록 설정
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }

        OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // 모든 호스트 이름 검증을 무시
            .build()
    }

    fun fetchUserSettingInfo(
        onSuccess: (UserInfo) -> Unit,
        onError: (String) -> Unit // 오류 처리를 위한 콜백 추가
    ) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null)
        Log.d("QR_SCAN", "Access Token: $accessToken")

        val url = "https://univting.cc:9999/user/setting/info"
        // GET 요청 생성
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Authorization", "$accessToken")
            .build()

        // 비동기 네트워크 호출
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FetchUserSettingInfo", "Request failed: ${e.message}")
                onError("네트워크 요청 실패: ${e.message}") // 에러 콜백 호출
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("FetchUserSettingInfo", "Response body: $responseBody") // 서버 응답 로그

                    if (responseBody != null) {
                        try {
                            val jsonArray = JSONArray(responseBody) // JSON 배열로 변환
                            if (jsonArray.length() > 0) {
                                val jsonObject = jsonArray.getJSONObject(0) // 첫 번째 객체 추출
                                val userInfo = UserInfo(
                                    id = jsonObject.getInt("id"),
                                    studentCode = jsonObject.getString("student_code"),
                                    major = jsonObject.getString("major"),
                                    name = jsonObject.getString("name"),
                                    participantCount = jsonObject.getInt("participant_count"),
                                    createdAt = jsonObject.getString("createdAt"),
                                    deletedAt = jsonObject.optString("deletedAt", null)
                                )

                                Log.d("FetchUserSettingInfo", "User Info: $userInfo")

                                // UI 업데이트는 메인 스레드에서 수행
                                CoroutineScope(Dispatchers.Main).launch {
                                    onSuccess(userInfo)
                                }
                            } else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    onError("사용자 정보가 없습니다.") // 배열이 비어있을 경우
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("FetchUserSettingInfo", "JSON parsing error: ${e.message}")
                            CoroutineScope(Dispatchers.Main).launch {
                                onError("JSON 파싱 오류: ${e.message}") // 오류 메시지 전달
                            }
                        }
                    } else {
                        Log.e("FetchUserSettingInfo", "Response body is null")
                        CoroutineScope(Dispatchers.Main).launch {
                            onError("서버 응답이 null입니다.") // 오류 메시지 전달
                        }
                    }
                } else {
                    Log.e("FetchUserSettingInfo", "Error: ${response.code} - ${response.message}")
                    CoroutineScope(Dispatchers.Main).launch {
                        onError("서버 오류: ${response.code} - ${response.message}") // 오류 메시지 전달
                    }
                }
            }
        })
    }

    fun login(
        studentCode: String,
        name: String,
        major: String,
        password: String, // 비밀번호 추가
        onSuccess: (String, String) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "https://univting.cc:9999/user/login"
        val json = """{"student_code":"$studentCode","name":"$name","major":"$major","password":"$password"}""" // JSON 형식

        Log.d("Login Request", json) // 요청 본문 로그

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull() // 미디어 타입 설정
        val requestBody = json.toRequestBody(mediaType) // 요청 본문 생성

        // POST 요청 생성
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // 비동기 네트워크 호출
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // 요청 실패 시 오류 출력
                CoroutineScope(Dispatchers.Main).launch {
                    onError("로그인 요청에 실패했습니다") // 에러 메시지 포함
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("Login Response", responseBody ?: "Response body is null")

                    // 성공적인 응답 처리
                    val jsonResponse = JSONObject(responseBody ?: "{}")
                    val tokenObject = jsonResponse.optJSONObject("token") // token 객체를 가져옴
                    val accessToken = tokenObject?.optString("access_token", null) // access_token을 가져옴
                    val refreshToken = tokenObject?.optString("refresh_token", null) // refresh_token을 가져옴


                    if (accessToken != null && refreshToken != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            onSuccess(accessToken, refreshToken) // 로그인 성공 시 콜백 호출
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            onError("로그인 실패: ${jsonResponse.optString("error", "Unknown error")}")
                        }
                    }
                } else {
                    // 오류 응답 처리
                    val errorResponse = response.body?.string()
                    Log.e("Login Error", errorResponse ?: "Error response is null")

                    val jsonResponse = JSONObject(errorResponse ?: "{}")
                    val message = jsonResponse.optString("message", "알 수 없는 오류입니다.")

                    CoroutineScope(Dispatchers.Main).launch {
                        onError(message) // 오류 메시지 전달
                    }
                }
            }
        })
    }

    fun sendQrCodeToServer(qrCode: String, callback: (Int, String) -> Unit) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null) ?: ""

        val mediaType = "application/json".toMediaType()
        val body = "{\r\n    \"event_code\" : \"$qrCode\"\r\n}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://univting.cc:9999/user/attendance")
            .post(body)
            .addHeader("Authorization", accessToken)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(-1, "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                val statusCode = response.code
                val message = when (statusCode) {
                    200 -> "Success"
                    401 -> "이미 등록된 코드입니다."
                    402 -> "코드 형식이 맞지 않습니다."
                    else -> "알 수 없는 오류가 발생했습니다"
                }
                callback(statusCode, message)
            }
        })
    }

    fun fetchEventList(
        context: Context,
        onSuccess: (List<Event>) -> Unit,
        onError: (String) -> Unit // 오류 메시지를 처리하는 콜백 추가
    ) {
        Log.d("FETCH_EVENT_LIST", "Starting to fetch event list...")
        val url = "https://univting.cc:9999/user/event/list"
        val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null) ?: ""

        // GET 요청 생성
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Authorization", accessToken ?: "")
            .build()

        // 비동기 네트워크 호출
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FETCH_EVENT_LIST", "Request failed: ${e.message}")
                CoroutineScope(Dispatchers.Main).launch {
                    onError("네트워크 요청 실패: ${e.message}") // 오류 메시지 전달
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("FETCH_EVENT_LIST", "Response body: $responseBody")

                    // JSON 파싱
                    try {
                        val jsonArray = JSONArray(responseBody)
                        val fetchedEvents = mutableListOf<Event>()

                        for (i in 0 until jsonArray.length()) {
                            val eventObject = jsonArray.getJSONObject(i)
                            val eventName = eventObject.getString("event_name")
                            val eventCode = eventObject.getString("event_code")
                            val description = eventObject.getString("description")
                            val location = eventObject.getString("location")
                            val eventStartTime = eventObject.getString("event_start_time")
                            val eventEndTime = eventObject.getString("event_end_time")
                            val createdAt = eventObject.getString("createdAt")
                            val participantsArray = eventObject.optJSONArray("participants")

                            // participants 배열을 리스트로 변환
                            val participants = mutableListOf<Participant>()
                            if (participantsArray != null) {
                                for (j in 0 until participantsArray.length()) {
                                    val participantObject = participantsArray.getJSONObject(j)
                                    val participant = Participant(
                                        id = participantObject.getInt("id"),
                                        createdAt = participantObject.getString("createdAt"),
                                        userId = participantObject.getInt("user_id"),
                                        eventCode = participantObject.getString("event_code")
                                    )
                                    participants.add(participant)
                                }
                            }

                            // 이벤트 객체 생성 후 목록에 추가
                            fetchedEvents.add(
                                Event(
                                    eventCode,
                                    eventName,
                                    description,
                                    location,
                                    eventStartTime,
                                    eventEndTime,
                                    createdAt,
                                    participants
                                )
                            )
                        }

                        CoroutineScope(Dispatchers.Main).launch {
                            onSuccess(fetchedEvents) // 성공적으로 가져온 이벤트 목록 전달
                        }
                        Log.d("FETCH_EVENT_LIST", "Events updated: $fetchedEvents")
                    } catch (e: Exception) {
                        Log.e("FETCH_EVENT_LIST", "JSON parsing error: ${e.message}")
                        CoroutineScope(Dispatchers.Main).launch {
                            onError("JSON 파싱 오류: ${e.message}") // 오류 메시지 전달
                        }
                    }
                } else {
                    Log.e("FETCH_EVENT_LIST", "Error: ${response.code} - ${response.message}")
                    CoroutineScope(Dispatchers.Main).launch {
                        onError("서버 오류: ${response.code} - ${response.message}") // 오류 메시지 전달
                    }
                }
            }
        })
    }

    fun deleteAccount(accessToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val url = "https://univting.cc:9999/user"
        val mediaType = "text/plain".toMediaType() // 미디어 타입 설정
        val body = "".toRequestBody(mediaType) // 요청 본문 생성

        // DELETE 요청 생성
        val request = Request.Builder()
            .url(url)
            .method("DELETE", body)
            .addHeader("Authorization", accessToken) // Authorization 헤더 추가
            .build()

        // 비동기 네트워크 호출
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // 요청 실패 시 오류 출력
                onError("계정 삭제 요청에 실패했습니다.") // 에러 콜백 호출
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // 성공적으로 계정 삭제
                    CoroutineScope(Dispatchers.Main).launch {
                        onSuccess() // 성공 시 콜백 호출
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        onError("Error: ${response.code} - ${response.message}") // 응답 오류 메시지 전달
                    }
                }
            }
        })
    }
}
