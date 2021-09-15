package com.epam.contest.flatbufferdemo.flatc

import Complaince.Document.*
import com.google.flatbuffers.FlatBufferBuilder
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class GeneratorTest {

    @Test
    fun `should create new request as flatbuffer`() {
        logger.info("Start generation...")
        // main info
        val fbb = FlatBufferBuilder(0)
        val idOffset = fbb.createString("UUID")
        val nameOffset = fbb.createString("OWN DOCUMENT")

        // description
        val titleOffset = fbb.createString("new one")
        val departmentOffset = fbb.createString("own 13 90")
        val uniqOffset = fbb.createString("uuid-123-dsf-g43r-vfcx")
        val descriptionOffset = Description.createDescription(fbb, titleOffset, departmentOffset, uniqOffset)

        // complaint
        val idComplOffset = fbb.createString("uniq id")
        val active = true
        val status: Short = 1
        // owner description
        val ownerName = fbb.createString("test")
        val surnameName = fbb.createString("testovich")
        // address
        val country = fbb.createString("Russia")
        val countryCode = fbb.createString("RUS")
        val city = fbb.createString("Moscow")
        val street = fbb.createString("Arbat")
        val building = fbb.createString("1")
        val flat: Short = 1
        val addressOffset = Address.createAddress(fbb, country, countryCode, city, street, building, flat)

        val ownerOffsets = Owner.createOwner(fbb, ownerName, surnameName, addressOffset)
        val complaintOffset = Complaint.createComplaint(fbb, idComplOffset, active, status, ownerOffsets)
        val complaints = IntArray(1).apply { this[0] = complaintOffset }

        // create main doc
        val complaintOwnerOffset = Complaint.createOwnerVector(fbb, complaints)
        val off = Document.createDocument(fbb, idOffset, nameOffset, descriptionOffset, complaintOwnerOffset)
        Document.finishDocumentBuffer(fbb, off)
        logger.info("flatc document was created...")


        val newBb = fbb.dataBuffer()
        File("binary.txt").writeBytes(newBb.array())
        logger.info("binary file was saved...")
        val doc = Document.getRootAsDocument(newBb)
        File("document.txt").writeBytes(doc.byteBuffer.array())
        logger.info("end test...")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
