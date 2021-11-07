package com.epam.contest.flatbufferdemo.domain

enum class ComplaintStatus(val code: Short) {
    OK(0), NEW(1), RESERVED(2), CANCELLED(3);

    companion object {
        fun findByCode(code: Short) =
            values().find { it.code == code } ?: OK
    }
}
