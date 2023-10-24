package com.example.retrofit.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.retrofit.component.Questions

@Composable
fun TriviaHome(viewModel : QuestionsViewModel = hiltViewModel()) {
	Questions(viewModel)
}