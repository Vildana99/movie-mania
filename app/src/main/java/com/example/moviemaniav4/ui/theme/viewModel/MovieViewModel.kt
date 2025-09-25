package com.example.moviemaniav4.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemaniav4.models.MovieDetails
import com.example.moviemaniav4.network.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MovieViewModel : ViewModel() {
    private var popularCurrentPage = 1
    private var upcomingCurrentPage = 1
    private var topRatedCurrentPage = 1

    private var popularTotalPages = 1
    private var upcomingTotalPages = 1
    private var topRatedTotalPages = 1

    private val _popularMoviesStateFlow: MutableStateFlow<List<MovieDetails>> =
        MutableStateFlow(emptyList())
    val popularMoviesStateFlow: StateFlow<List<MovieDetails>> = _popularMoviesStateFlow

    private val _upcomingMoviesStateFlow: MutableStateFlow<List<MovieDetails>> =
        MutableStateFlow(emptyList())
    val upcomingMoviesStateFlow: StateFlow<List<MovieDetails>> = _upcomingMoviesStateFlow

    private val _topRatedMoviesStateFlow: MutableStateFlow<List<MovieDetails>> =
        MutableStateFlow(emptyList())
    val topRatedMoviesStateFlow: StateFlow<List<MovieDetails>> = _topRatedMoviesStateFlow

    private val _selectedMovieStateFlow: MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val selectedMovieStateFlow: StateFlow<MovieDetails?> = _selectedMovieStateFlow

    private val _isLoadingNextPage = MutableStateFlow(false)
    val isLoadingNextPage: StateFlow<Boolean> = _isLoadingNextPage

    fun fetchMovies(category: String, page: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieApiService = MovieApiService.provideMoviApi()
            try {
                val movieListDto = movieApiService.getMoviesList(category, page, apiKey)
                val movies = movieListDto.results

                when (category) {
                    "popular" -> {
                        if (page == 1) {
                            popularTotalPages = movieListDto.total_pages
                            _popularMoviesStateFlow.value = movies
                        } else {
                            _popularMoviesStateFlow.value = _popularMoviesStateFlow.value + movies
                        }
                    }

                    "upcoming" -> {
                        if (page == 1) {
                            upcomingTotalPages = movieListDto.total_pages
                            _upcomingMoviesStateFlow.value = movies
                        } else {
                            _upcomingMoviesStateFlow.value = _upcomingMoviesStateFlow.value + movies
                        }
                    }

                    "top_rated" -> {
                        if (page == 1) {
                            topRatedTotalPages = movieListDto.total_pages
                            _topRatedMoviesStateFlow.value = movies
                        } else {
                            _topRatedMoviesStateFlow.value = _topRatedMoviesStateFlow.value + movies
                        }
                    }
                }

                println("Dohvaćena lista filmova za kategoriju $category:")
                movies.forEach { movie ->
                    println("ID: ${movie.id}, Title: ${movie.title}")
                }
            } catch (e: Exception) {
                println("Greška prilikom dohvatanja lista filmova za kategoriju $category: ${e.message}")
            } finally {
                _isLoadingNextPage.value = false
            }
        }
    }

    fun loadNextPage(category: String, apiKey: String) {
        if (!_isLoadingNextPage.value) {
            _isLoadingNextPage.value = true
            when (category) {
                "popular" -> if (popularCurrentPage < popularTotalPages) {
                    popularCurrentPage++
                    fetchMovies(category, popularCurrentPage, apiKey)
                }

                "upcoming" -> if (upcomingCurrentPage < upcomingTotalPages) {
                    upcomingCurrentPage++
                    fetchMovies(category, upcomingCurrentPage, apiKey)
                }

                "top_rated" -> if (topRatedCurrentPage < topRatedTotalPages) {
                    topRatedCurrentPage++
                    fetchMovies(category, topRatedCurrentPage, apiKey)
                }
            }
        }
    }

    fun selectMovie(movie: MovieDetails) {
        _selectedMovieStateFlow.value = movie
    }

    fun getMovieById(id: Int?): MovieDetails? {
        return _popularMoviesStateFlow.value.find { it.id == id }
            ?: _upcomingMoviesStateFlow.value.find { it.id == id }
            ?: _topRatedMoviesStateFlow.value.find { it.id == id }
    }

}