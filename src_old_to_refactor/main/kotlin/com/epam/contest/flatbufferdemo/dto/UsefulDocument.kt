package com.epam.contest.flatbufferdemo.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("some_useful_document")
data class UsefulDocument(
    @field:Id val id: String?,
    val name: String,
    val complaint: Complaint,
    val timeStamp: LocalDateTime,
    val status: Boolean
) {
    constructor(name: String, complaint: Complaint) : this(null, name, complaint, LocalDateTime.now(), true)
}
