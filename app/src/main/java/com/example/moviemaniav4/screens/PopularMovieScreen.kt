package com.example.moviemaniav4.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import com.example.moviemaniav4.models.MovieDetails
import com.example.moviemaniav4.network.MovieApiService
import com.example.moviemaniav4.ui.theme.viewModel.MovieViewModel

@Composable
fun PopularMovieScreen(navController: NavController, viewModel: MovieViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val columns = if (screenWidthDp < 600) 2 else 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    )

    Column {
        MovieAppBar(navController)
        val movies by viewModel.popularMoviesStateFlow.collectAsState()
        val isLoading by viewModel.isLoadingNextPage.collectAsState()
        LaunchedEffect(movies) {
            if (movies.isEmpty()) {
                viewModel.fetchMovies("popular", 1, MovieApiService.API_KEY)
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
                    viewModel.loadNextPage("popular", MovieApiService.API_KEY)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieDetails, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        modifier = modifier
            .padding(8.dp)
            .size(250.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                maxLines = 2, // Prikaz do dva reda teksta
                overflow = TextOverflow.Ellipsis // Dodavanje tri tačke ako je tekst predugačak
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rating: ${movie.voteAverage}",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
        }
    }
}


