package com.epam.contest.flatbufferdemo.domain

import com.epam.contest.flatbufferdemo.dto.Document
import com.epam.contest.flatbufferdemo.dto.UsefulDocument

fun Document.toImportantDoc() = UsefulDocument(this.name, this.complaint)
