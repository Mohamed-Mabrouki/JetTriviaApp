package com.example.retrofit.di

import com.example.retrofit.network.QuestionApi
import com.example.retrofit.repository.QuestionRepository
import com.example.retrofit.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Singleton
	@Provides
	fun provideQuestionRepository(api : QuestionApi)=QuestionRepository(api)


	@Singleton
	@Provides
	fun provideQuestionApi():QuestionApi{
		val gson = GsonBuilder().setLenient().create()

		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
			.create(QuestionApi::class.java)
	}
}