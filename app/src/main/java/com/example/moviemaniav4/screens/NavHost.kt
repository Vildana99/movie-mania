package com.example.moviemaniav4.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviemaniav4.ui.theme.viewModel.MovieViewModel

@Composable
fun Navigation(viewModel: MovieViewModel, context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Welcome screen") {
        composable("Welcome screen") {
            WelcomeScreen(navController = navController, context = context)
        }
        composable("Popular movie screen") {
            PopularMovieScreen(navController = navController, viewModel = viewModel)
        }
        composable("Upcoming movie screen") {
            UpcomingMovieScreen(navController = navController, viewModel = viewModel)
        }
        composable("Top rated movie screen") {
            TopRatedMovieScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "Movie detail screen?movieId={movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            val movie = viewModel.getMovieById(movieId)
            movie?.let {
                MovieDetailScreen(movie = it, onBack = { navController.popBackStack() })
            }
        }
    }
}
