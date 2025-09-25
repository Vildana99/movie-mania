package com.example.moviemaniav4.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviemaniav4.network.MovieApiService
import com.example.moviemaniav4.ui.theme.viewModel.MovieViewModel

@Composable
fun UpcomingMovieScreen(navController: NavController, viewModel: MovieViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val columns = if (screenWidthDp >= 600) 4 else 2
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    )
    Column {
        MovieAppBar(navController)
        val movies by viewModel.upcomingMoviesStateFlow.collectAsState()
        val isLoading by viewModel.isLoadingNextPage.collectAsState()
        LaunchedEffect(movies) {
            if (movies.isEmpty()) {
                viewModel.fetchMovies("upcoming", 1, MovieApiService.API_KEY)
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(movies) { index, movie ->
                MovieItem(movie, onClick = {
                    viewModel.selectMovie(movie)
                    navController.navigate("Movie detail screen?movieId=${movie.id}")
                })
                if (index == movies.size - 1 && !isLoading) {
                    viewModel.loadNextPage("upcoming", MovieApiService.API_KEY)
                }
            }
        }
    }
}




