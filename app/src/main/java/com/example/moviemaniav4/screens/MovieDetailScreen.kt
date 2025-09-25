package com.example.moviemaniav4.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import coil.compose.rememberAsyncImagePainter
import com.example.moviemaniav4.models.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movie: MovieDetails, onBack: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie Details",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isTablet) 28.sp else 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        shareMovie(context, movie)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Gray
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title,
                    style = TextStyle(fontSize = if (isTablet) 34.sp else 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row{
                Text(
                    text = "Rating: ",
                    style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp, fontWeight = FontWeight.Bold)
                )
                    Text(
                        text = "${movie.voteAverage}",
                        style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Language: ",
                        style = TextStyle(
                            fontSize = if (isTablet) 26.sp else 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "${movie.originalLanguage}",
                        style = TextStyle(
                            fontSize = if (isTablet) 26.sp else 20.sp,

                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Popularity: ",
                        style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp), fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${movie.popularity}",
                        style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp)
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Overview:",
                    style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.overview,
                    style = TextStyle(fontSize = if (isTablet) 26.sp else 20.sp)
                )
            }
        }
    }
}

fun shareMovie(context: Context, movie: MovieDetails) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, movie.title)
        putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.title}\n\nRating: ${movie.voteAverage}\n\nOverview: ${movie.overview}")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share movie via"))
}