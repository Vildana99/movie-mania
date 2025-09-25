package com.example.moviemaniav4.network

import com.example.moviemaniav4.models.MovieList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface MovieApiService {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieList

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "6e02c3939e12c7b189ab43440ed771b0"

        fun provideMoviApi(): MovieApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApiService::class.java)
        }
    }
}