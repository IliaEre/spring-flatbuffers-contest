package com.epam.contest.flatbufferdemo.domain

import com.epam.contest.flatbufferdemo.dto.Address
import Complaince.Document.Address as FbAddress
import Complaince.Document.Owner as FbOwner
import com.epam.contest.flatbufferdemo.dto.Complaint
import com.epam.contest.flatbufferdemo.dto.Owner
import com.epam.contest.flatbufferdemo.dto.UsefulDocument

@ExperimentalStdlibApi
fun Complaince.Document.Document.toImportantDoc() =
    UsefulDocument(
        this.name(),
        Complaint(
            id = this.complaint().id(),
            active = this.complaint().active(),
            status = ComplaintStatus.findByCode(this.complaint().status()),
            owner = parseOwners(this.complaint().ownerVector())
        )
    )

@ExperimentalStdlibApi
fun parseOwners(vector: FbOwner.Vector): List<Owner> =
    buildList {
        for (index in 0 until vector.length()) {
            add(
                Owner(
                    name = vector[index].name(),
                    surname = vector[index].surname(),
                    address = vector[index].address().parseAddress()
                )
            )
        }
    }

fun FbAddress.parseAddress() = Address(
    this.country(), this.countryCode(), this.city(), this.street(), this.building(), this.flat().toInt()
)
