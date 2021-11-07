package com.epam.contest.flatbufferdemo.dto

import com.epam.contest.flatbufferdemo.domain.ComplaintStatus

/** Main data class */
data class Document(
    val id: String,
    val name: String,
    val description: Description,
    val complaint: Complaint
)

data class Description(
    val title: String,
    val department: String,
    val uniqueCode: String
)

data class Complaint(
    val id: String,
    val active: Boolean,
    val status: ComplaintStatus,
    val owner: List<Owner>
)

data class Owner(
    val name: String,
    val surname: String,
    val address: Address?
)

data class Address(
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val building: String,
    val flat: Int
)
