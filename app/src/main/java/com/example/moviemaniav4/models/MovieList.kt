package com.example.moviemaniav4.models

data class MovieList(
    val page:Int,
    val results:List<MovieDetails>,
    val total_pages:Int,
    val total_results:Int
)