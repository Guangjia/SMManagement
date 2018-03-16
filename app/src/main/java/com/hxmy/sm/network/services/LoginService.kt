package com.hxmy.sm.network.services

import com.hxmy.sm.model.response.UserResponse
import com.hxmy.sm.network.JsonAndXmlConverters
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * @author a488606
 * @since 3/14/18
 */

interface LoginService {
    @FormUrlEncoded
    @POST("./")
    @JsonAndXmlConverters.Json
    fun login(@Field("userName") userName: String, @Field("password") password: String): Observable<UserResponse>
}