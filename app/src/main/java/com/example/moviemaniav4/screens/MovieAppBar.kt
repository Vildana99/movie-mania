package com.example.moviemaniav4.screens

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


object MovieScreen {
    var currentScreen by mutableStateOf(Screen.Popular)

    enum class Screen {
        Popular,
        Upcoming,
        TopRated
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(navController: NavController) {
    val title = when (MovieScreen.currentScreen) {
        MovieScreen.Screen.Popular -> "Popular"
        MovieScreen.Screen.Upcoming -> "Upcoming"
        MovieScreen.Screen.TopRated -> "Top Rated"
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = {
                MovieScreen.currentScreen = MovieScreen.Screen.Popular
                navController.navigate("Popular movie screen")
            }) {
                Icon(
                    Icons.Filled.Movie,
                    contentDescription = "Popular Movies",
                    tint = if (MovieScreen.currentScreen == MovieScreen.Screen.Popular) Color.White else Color.Gray
                )
            }
            IconButton(onClick = {
                MovieScreen.currentScreen = MovieScreen.Screen.Upcoming
                navController.navigate("Upcoming movie screen")
            }) {
                Icon(
                    Icons.Filled.Upcoming,
                    contentDescription = "Upcoming Movies",
                    tint = if (MovieScreen.currentScreen == MovieScreen.Screen.Upcoming) Color.White else Color.Gray
                )
            }
            IconButton(onClick = {
                MovieScreen.currentScreen = MovieScreen.Screen.TopRated
                navController.navigate("Top rated movie screen")
            }) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Top Rated Movies",
                    tint = if (MovieScreen.currentScreen == MovieScreen.Screen.TopRated) Color.White else Color.Gray
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black 
        )
    )
}
