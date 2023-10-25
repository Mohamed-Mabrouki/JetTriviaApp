package com.example.retrofit.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofit.model.QuestionItem
import com.example.retrofit.screens.QuestionsViewModel
import com.example.retrofit.util.AppColors

@Composable
fun Questions(viewModel : QuestionsViewModel) {
	val questions = viewModel.data.value.data?.toMutableList()
	val questionIndex = remember {
		mutableStateOf(0)
	}
	if (viewModel.data.value.loading == true) {
		CircularProgressIndicator()
		Log.d("Loading", "Loading")
	} else {
		val question = try {
			questions?.get(questionIndex.value)
		} catch (ex : Exception) {
			null
		}
		if (questions != null) {
			QuestionDisplay(
				questionCount = questions.size,
				question = question!!,
				questionIndex = questionIndex,
				viewModel = viewModel,
			){
				questionIndex.value += 1
			}
		}


	}
}
//@Preview
@Composable
fun QuestionDisplay(
	questionCount:Int,
	question : QuestionItem,
	questionIndex : MutableState<Int>,
	viewModel : QuestionsViewModel,
	onNextClicked : (Int) -> Unit = {}
) {
	val choicesState = remember(question) {
		question.choices.toMutableList()
	}
	val answerState = remember(question) {
		mutableStateOf<Int?>(null)
	}
	val correctAnswerState = remember(question) {
		mutableStateOf<Boolean?>(null)
	}
	val updateAnswer : (Int) -> Unit = remember(question) {
		{
			answerState.value = it
			correctAnswerState.value = choicesState[it] == question.answer
		}
	}
	val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 10f)
	Surface(
		color = AppColors.mDarkPurple, modifier = Modifier
			.fillMaxSize()
			.padding(4.dp)
	) {
		Column(
			modifier = Modifier.padding(12.dp),
			verticalArrangement = Arrangement.Top,
			horizontalAlignment = Alignment.Start
		) {
			Questiontracker(counter = questionIndex.value, outOf = questionCount )
			DrawDottedLine(pathEffect)
			Column {
				Text(
					text = question.question,
					fontSize = 17.sp,
					fontWeight = FontWeight.Bold,
					lineHeight = 22.sp,
					color = AppColors.mOffWhite,
					modifier = Modifier
						.padding(6.dp)
						.align(alignment = Alignment.Start)
						.fillMaxHeight(0.3f),
				)

				choicesState.forEachIndexed { index, answerText ->
					Row(
						modifier = Modifier
							.padding(3.dp)
							.fillMaxWidth()
							.height(45.dp)
							.border(
								width = 4.dp, brush = Brush.linearGradient(
									colors = listOf(AppColors.mLightBlue, AppColors.mLightBlue)
								), shape = RoundedCornerShape(15.dp)
							)
							.clip(
								RoundedCornerShape(
									topEndPercent = 50,
									bottomEndPercent = 50,
									topStartPercent = 50,
									bottomStartPercent = 50
								)
							)
							.background(Color.Transparent),
						verticalAlignment = Alignment.CenterVertically
					) {
						RadioButton(
							selected = answerState.value == index,
							onClick = {
								updateAnswer(index)
							},
							modifier = Modifier.padding(start = 16.dp),
							colors = RadioButtonDefaults.colors(
								selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
									Color.Green


								} else {
									Color.Red
								}
							)
						)
						val annotatedString = buildAnnotatedString {
							withStyle(
								SpanStyle(
									fontSize = 17.sp,
									fontWeight = FontWeight.Light,
									color = if (correctAnswerState.value == true && index == answerState.value) {
										Color.Green


									} else if (correctAnswerState.value == false && index == answerState.value) {
										Color.Red
									} else {
										AppColors.mOffWhite
									}
								)
							) {
								append(answerText)
							}
						}
						Text(text = annotatedString)
					}
				}
				Button(
					onClick = { onNextClicked(questionIndex.value) },
					modifier = Modifier
						.padding(3.dp)
						.align(alignment = Alignment.CenterHorizontally),
					shape = RoundedCornerShape(34.dp),
					colors = ButtonDefaults.buttonColors(containerColor = AppColors.mLightBlue)
				) {
					Text(
						text = "Next",
						modifier = Modifier.padding(4.dp),
						color = AppColors.mOffWhite,
						fontSize = 17.sp
					)
				}
			}
		}
	}
}
//@Preview
@Composable
fun Questiontracker(counter : Int = 10, outOf : Int = 100) {
	Text(text = buildAnnotatedString {
		withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
			withStyle(
				style = SpanStyle(
					color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp
				)
			) {
				append("Question $counter/")
				withStyle(
					style = SpanStyle(
						color = AppColors.mLightGray,
						fontWeight = FontWeight.Light,
						fontSize = 14.sp
					)
				) { append("$outOf") }
			}
		}
	}, modifier = Modifier.padding(20.dp))

}
@Composable
fun DrawDottedLine(pathEffect : PathEffect) {
	Canvas(
		modifier = Modifier
			.fillMaxWidth()
			.height(1.dp)
	) {
		drawLine(
			color = AppColors.mLightGray,
			start = Offset(0f, 0f),
			end = Offset(size.width, 0f),
			pathEffect = pathEffect
		)
	}
}

