package com.example.bmicounter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmicounter.components.topBar
import com.example.bmicounter.ui.theme.BmiCounterTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BmiHomescreen(
    viewModel: BmiViewModel = viewModel(),
    modifier: Modifier = Modifier
        .fillMaxSize(),

    ) {
    val bmiUiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { topBar() },
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .verticalScroll(rememberScrollState())
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Height (M)",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        animatedCounter(counter = bmiUiState.height)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.addHeight()
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_24),
                                    contentDescription = "",
                                )
                            }
                            IconButton(
                                onClick = {
                                    viewModel.deductHeight()
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_horizontal_rule_24),
                                    contentDescription = "",

                                    )
                            }
                        }
                    }
                },

                )
            Card(
                modifier = Modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Weight (kg)",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        animatedCounter(counter = bmiUiState.weight)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.addAge()
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_24),
                                    contentDescription = "",
                                )
                            }
                            IconButton(
                                onClick = {
                                    viewModel.reduceAge()
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_horizontal_rule_24),
                                    contentDescription = "",

                                    )
                            }
                        }
                    }
                },

                )
            Card(
                modifier = Modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your BMI",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        AnimatedContent(
                            targetState = bmiUiState.bmi,
                            transitionSpec = {
                                slideInVertically { it } with slideOutVertically { it }
                            },
                            label = "",
                        ) { bmi ->
                            Text(
                                text = bmiUiState.bmi.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                softWrap = false,
                                color = Color.Red,
                                fontSize = 50.sp,

                                )
                        }
                    }
                }

            )
            Button(
                onClick =
                {
                    viewModel.calculateBMI()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),

            ) {
                Text(text = "Calculate BMI")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun animatedCounter(
    counter: Float,
    modifier: Modifier = Modifier,
) {
    var oldCount by remember {
        mutableStateOf(counter)
    }
    SideEffect {
        oldCount = counter
    }
    Row(modifier = modifier) {
        val countString = counter.toString()
        val oldCountString = oldCount.toString()

        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
//                    slideInVertically { it }
                    scaleIn(animationSpec = tween(durationMillis = 500)) with slideOutVertically { it }
                },
                label = "",
            ) { char ->
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = false,
                    color = Color.Blue,
                    fontSize = 50.sp

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun bmiPreview() {
    BmiCounterTheme {
        BmiHomescreen(viewModel = BmiViewModel())
    }
//
}
