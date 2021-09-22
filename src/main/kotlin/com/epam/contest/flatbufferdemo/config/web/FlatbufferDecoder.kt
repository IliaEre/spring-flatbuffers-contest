package com.epam.contest.flatbufferdemo.config.web

import Complaince.Document.Document
import com.google.flatbuffers.Table
import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.codec.Decoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FlatbufferDecoder: FlatbuffersCodecSupport(), Decoder<Table> {

    override fun canDecode(elementType: ResolvableType, mimeType: MimeType?): Boolean =
        Table::class.java.isAssignableFrom(elementType.toClass()) && supportsMimeType(mimeType)

    override fun decode(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Flux<Table> =
        Flux.from(inputStream)
            .map { decode(it, elementType, mimeType, hints) }

    override fun decodeToMono(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Mono<Table> =
        DataBufferUtils.join(inputStream)
            .map { decode(it, elementType, mimeType, hints) }

    override fun decode(
        buffer: DataBuffer,
        targetType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Table {
        val bytes = ByteArray(buffer.readableByteCount())
        buffer.read(bytes)
        DataBufferUtils.release(buffer)
        val buff = java.nio.ByteBuffer.wrap(bytes)
        return Document.getRootAsDocument(buff)
    }

    fun decodeAsOldWay(
        buffer: DataBuffer,
        targetType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Table = Document.getRootAsDocument(buffer.asByteBuffer())

    override fun getDecodableMimeTypes(): MutableList<MimeType> = MIME_TYPES
}