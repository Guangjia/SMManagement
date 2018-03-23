package com.hxmy.sm.network.services

import com.hxmy.sm.model.request.LoginRequest
import com.hxmy.sm.model.response.LoginResponse
import com.hxmy.sm.network.JsonAndXmlConverters
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface LoginService {
    @POST("api/authentication")

    @Headers(*arrayOf("Content-Type: application/json", "session_id:2d9e7004e3a320755d1d554e234573b4"))

    @JsonAndXmlConverters.Json
    fun login(@JsonAndXmlConverters.Json @Body login: LoginRequest): Observable<LoginResponse>
}