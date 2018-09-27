package me.echeung.moemoekyun.client.api.service

import me.echeung.moemoekyun.client.api.ErrorHandlingAdapter
import me.echeung.moemoekyun.client.api.response.BaseResponse
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RequestsService {
    @POST("requests/{id}")
    fun request(@Header("Authorization") token: String, @Header("library") library: String, @Path("id") id: String): ErrorHandlingAdapter.WrappedCall<BaseResponse>
}