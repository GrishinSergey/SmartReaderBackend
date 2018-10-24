package com.sagrishin.smartreader.core.data.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object VoiceOvers : IntIdTable("voice_overs") {

    val pathToVoiceOverFile = varchar("path_to_voice_over_file", 255)
    val book = integer("book") references Books.bookId

}


class VoiceOverEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<VoiceOverEntity>(VoiceOvers)

    var pathToVoiceOverFile by VoiceOvers.pathToVoiceOverFile
    var book by VoiceOvers.book

}


data class DatabaseVoiceOver(val pathToVoiceOverFile: String = "")