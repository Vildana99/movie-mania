package com.example.moviemaniav4

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.moviemaniav4.network.MovieApiService
import com.example.moviemaniav4.screens.Navigation
import com.example.moviemaniav4.ui.theme.viewModel.MovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp(viewModel = viewModel, context = applicationContext)
        }
        Log.d("MainActivity", "Fetching movies for the first page")
        viewModel.fetchMovies("popular", 1, MovieApiService.API_KEY)
    }
}

@ExperimentalCoroutinesApi
@Composable
fun MovieApp(viewModel: MovieViewModel, context: Context) {
    Navigation(viewModel = viewModel, context = context)
}
