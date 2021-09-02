package com.epam.contest.flatbufferdemo.web

import com.epam.contest.flatbufferdemo.dto.Document
import com.epam.contest.flatbufferdemo.dto.UsefulDocument
import com.epam.contest.flatbufferdemo.service.DocumentService
import com.epam.contest.flatbufferdemo.web.ClassicDocumentController.Companion.MAIN_URL
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Just simple controller
 *
 * @see Document
 * */
interface DocumentController {

    /**
     * Get all available documents
     * @return flux of documents
     * */
    fun getAll(): Flux<UsefulDocument>

    /**
     * Save active documents
     * @param document
     * @return I wanna create it simple and reusable for this example
     * */
    fun saveDocument(document: Mono<Document>): Mono<Any>
}

@RestController
@RequestMapping(
    MAIN_URL,
    produces = [MediaType.APPLICATION_JSON_VALUE],
    consumes = [MediaType.APPLICATION_JSON_VALUE]
)
internal class ClassicDocumentController(
    private val documentService: DocumentService
): DocumentController {

    @GetMapping
    override fun getAll(): Flux<UsefulDocument> =
        documentService.getAll()

    @PostMapping
    override fun saveDocument(@RequestBody document: Mono<Document>): Mono<Any> =
        documentService.validateAndSave(document)
            .map { status -> ResponseEntity.ok(status) }

    companion object {
        const val MAIN_URL = "document/v1/complain/"
    }
}
