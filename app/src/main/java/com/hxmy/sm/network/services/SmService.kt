package com.hxmy.sm.network.services

import com.hxmy.sm.model.request.SmRequest
import com.hxmy.sm.model.response.SmResponse
import com.hxmy.sm.network.JsonAndXmlConverters
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface SmService {
    @POST("api/uploadMsg")
    @Headers(*arrayOf("Content-Type: application/json", "session_id:5c0303d8b50cb3bc704faf961059be4a"))
    @JsonAndXmlConverters.Json
    fun submitSm(@JsonAndXmlConverters.Json @Body smRequest: SmRequest): Observable<SmResponse>
}