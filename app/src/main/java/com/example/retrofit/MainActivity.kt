package com.example.retrofit
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.retrofit.model.Question
import com.example.retrofit.screens.QuestionsViewModel
import com.example.retrofit.screens.TriviaHome
import com.example.retrofit.ui.theme.RetrofitTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RetrofitTheme { // A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
				) {
					TriviaHome()
				}
			}
		}
	}
}

