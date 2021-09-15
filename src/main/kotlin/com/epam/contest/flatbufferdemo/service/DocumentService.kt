package com.epam.contest.flatbufferdemo.service

import Complaince.Document.Document
import com.epam.contest.flatbufferdemo.domain.toImportantDoc
import com.epam.contest.flatbufferdemo.dto.UsefulDocument
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Simple service layer one more time
 *
 * @see Document
 * */
interface DocumentService {

    /**
     * Get all available documents
     * @return flux of documents
     * */
    fun getAll(): Flux<UsefulDocument>

    /**
     * The main task is validation document and save useful fields
     * @param document common document
     * @return result as boolean
     * */
    fun validateAndSave(document: Document): Mono<Boolean>
}

@ExperimentalStdlibApi
@Service
internal class ClassicDocumentService(
    private val mongoOperations: ReactiveMongoOperations
): DocumentService {

    override fun getAll(): Flux<UsefulDocument> =
        mongoOperations.findAll(UsefulDocument::class.java)


    override fun validateAndSave(document: Document): Mono<Boolean> =
        mongoOperations.save(Mono.just(document.toImportantDoc()))
            .map { it.status }
}
