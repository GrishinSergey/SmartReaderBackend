package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.database.BookEntity
import com.sagrishin.smartreader.core.data.models.DatabaseBook
import com.sagrishin.smartreader.core.data.models.DatabaseGenre

fun bookDatabaseToEntityConverter(resultRow: BookEntity, genre: DatabaseGenre): DatabaseBook {
    return DatabaseBook(
            resultRow.id.value,
            resultRow.title,
            resultRow.author,
            resultRow.pathToCover,
            resultRow.pathToFile,
            resultRow.rate,
            resultRow.countPages,
            genre
    )
}
