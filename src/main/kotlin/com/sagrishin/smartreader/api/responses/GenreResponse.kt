package com.sagrishin.smartreader.api.responses

import com.sagrishin.smartreader.models.repositories.models.Genre

data class GenreResponse(
        val genres: List<String>,
        val status: Int
) {

    companion object Builder {

        fun convert(genres: List<Genre>, status: Int): GenreResponse {
            return GenreResponse(genres.map { it.genre }, status)
        }

    }

}