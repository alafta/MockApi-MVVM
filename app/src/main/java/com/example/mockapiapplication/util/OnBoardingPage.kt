package com.example.mockapiapplication.util

import androidx.annotation.DrawableRes
import com.example.mockapiapplication.R

sealed class OnBoardingPage (
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    data object First : OnBoardingPage(
        image = R.drawable.pokeball,
        title = "Hello!",
        description = "This is an application that was built based on test case."
    )

    data object Second : OnBoardingPage(
        image = R.drawable.pokeball,
        title = "About",
        description = "This application was built using Kotlin Jetpack Compose with MVVM architecture."
    )
}