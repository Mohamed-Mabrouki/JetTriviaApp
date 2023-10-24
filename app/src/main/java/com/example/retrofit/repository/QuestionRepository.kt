package com.example.retrofit.repository

import android.util.Log
import com.example.retrofit.data.DataOrException
import com.example.retrofit.model.Question
import com.example.retrofit.model.QuestionItem
import com.example.retrofit.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api : QuestionApi) {
	private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
	suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
		try {
				dataOrException.loading=true
				dataOrException.data=api.getAllQuestion()
			if((dataOrException.data as Question).isNotEmpty()){
				dataOrException.loading=false
			}



		}catch (exception:Exception){
			dataOrException.e=exception


		}
		return dataOrException
	}


}