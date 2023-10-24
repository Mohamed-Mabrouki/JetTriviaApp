package com.example.retrofit.network

import com.example.retrofit.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
	@GET("world.json")
	 suspend fun getAllQuestion(): Question
}