package com.epam.contest.flatbufferdemo.flatc

import Complaince.Document.*
import com.google.flatbuffers.FlatBufferBuilder
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream

class GeneratorTest {

    private fun createDoc(): FlatBufferBuilder {
        logger.info("Start generation...")
        val fbb = FlatBufferBuilder(0)
        val idOffset = fbb.createString("UUID")
        val nameOffset = fbb.createString("OWN DOCUMENT")

        logger.info("Generate main fields...")
        val titleOffset = fbb.createString("new one")
        val departmentOffset = fbb.createString("own 13 90")
        val uniqOffset = fbb.createString("uuid-123-dsf-g43r-vfcx")
        val descriptionOffset = Description.createDescription(fbb, titleOffset, departmentOffset, uniqOffset)

        logger.info("Generate complaints fields...")
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

        val ownerOffset = Owner.createOwner(fbb, ownerName, surnameName, addressOffset)
        val ownersOffset = IntArray(1).apply { this[0] = ownerOffset }
        val complaintOwnerOffset = Complaint.createOwnerVector(fbb, ownersOffset)

        val complaintOffset = Complaint.createComplaint(fbb, idComplOffset, active, status, complaintOwnerOffset)

        logger.info("Generate main doc...")
        val off = Document.createDocument(fbb, idOffset, nameOffset, descriptionOffset, complaintOffset)
        Document.finishDocumentBuffer(fbb, off)
        logger.info("flatc document was created...")

        return fbb
    }

    @Test
    fun `should create new request as flatbuffer`() {
       val fbb = createDoc()
        val newBb = fbb.dataBuffer()
        val file = File("binary.bin")
        file.writeBytes(newBb.array())

        logger.info("binary file was saved...")

        val doc = Document.getRootAsDocument(newBb)
        File("document.uu").writeBytes(doc.byteBuffer.array())

        val f12 = File("doc.uu")
        val fc = FileOutputStream(f12, false).channel
        fc.write(newBb)
        fc.close()

        logger.info("end test...")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
