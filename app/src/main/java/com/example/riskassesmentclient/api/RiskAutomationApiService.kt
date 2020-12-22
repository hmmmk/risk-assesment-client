package com.example.riskassesmentclient.api

import com.example.riskassesmentclient.api.models.Company
import com.example.riskassesmentclient.api.models.SignUpResponse
import com.example.riskassesmentclient.api.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Nick Vasko on 21.12.2020
 * https://github.com/hmmmk
 */
interface RiskAutomationApiService {

    @POST("register")
    fun register(
        @Query("login") login: String,
        @Query("password") password: String
    ): Call<SignUpResponse>

    @GET("register")
    fun getUsers(): Call<List<User>>

    @POST("login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Call<SignUpResponse>

    @POST("logout")
    fun logout(): Call<ResponseBody>

    @POST("company")
    fun createCompany(@Body company: Company): Call<Company>

    @GET("company")
    fun getCompany(@Query("company_id") companyId: Int): Call<List<Company>>

    @DELETE("company")
    fun deleteCompany(@Query("company_id") companyId: Int): Call<ResponseBody>
}