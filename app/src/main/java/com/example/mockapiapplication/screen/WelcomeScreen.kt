package com.example.mockapiapplication.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mockapiapplication.nav.Screen
import com.example.mockapiapplication.util.OnBoardingPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second
    )

    val pagerState = rememberPagerState()
    Surface(
        color = androidx.compose.material3.MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
                count = 2,
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                pagerState = pagerState
            )
            StartButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        }
    }
}


@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun StartButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth(),
            visible = pagerState.currentPage ==1
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = "Get Started")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

