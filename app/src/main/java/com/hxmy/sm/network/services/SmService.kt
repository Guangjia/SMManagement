package com.hxmy.sm.network.services

import com.hxmy.sm.model.request.SmRequest
import com.hxmy.sm.model.response.SmResponse
import com.hxmy.sm.network.JsonAndXmlConverters
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author a488606
 * @since 3/14/18
 */

interface SmService {
    @POST("./")
    @JsonAndXmlConverters.Json
    fun submitSm(@JsonAndXmlConverters.Json @Body smRequest: SmRequest): Observable<SmResponse>
}