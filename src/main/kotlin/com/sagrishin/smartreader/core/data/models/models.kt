package com.sagrishin.smartreader.core.data.models

data class DatabaseGenre(val genreId: Int = -1,
                         val genre: String = "",
                         val link: String = "")


data class DatabaseBook(val bookId: Int = -1,
                        val title: String = "",
                        val author: String = "",
                        val pathToCover: String = "",
                        val pathToFile: String = "",
                        val rate: Int = -1,
                        val countPages: Int = -1,
                        val genre: DatabaseGenre = DatabaseGenre())


data class DatabaseVoiceOver(val pathToVoiceOverFile: String = "")


data class DatabaseUser(val userId: Int = -1,
                        val userName: String = "",
                        val userEmail: String = "",
                        val libraries: List<DatabaseLibrary> = emptyList())


data class DatabaseLibrary(val libraryId: Int = -1,
                           val libraryName: String = "")
