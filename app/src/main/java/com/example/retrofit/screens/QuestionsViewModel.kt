package com.example.retrofit.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.data.DataOrException
import com.example.retrofit.model.QuestionItem
import com.example.retrofit.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository : QuestionRepository)
	:ViewModel() {
	val data : MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))
	init {
		getAllQuestions()
	}

	private fun getAllQuestions(){
		viewModelScope.launch { data.value.loading=true
		data.value=repository.getAllQuestions()
			if(data.value.data.toString().isNotEmpty()){
				data.value.loading=false
			}
		}

	}

}