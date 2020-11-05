package com.example

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.Instant

data class Payment(
    val _id: ObjectId? = null,
    val date: Instant = Instant.now(),
    val amount: BigDecimal = 0.toBigDecimal(),
    val from: String = "",
    val to: String = "",
    val status: Status = Status.NONE,
)

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class Status {
    NONE,
    PENDING,
    DONE,
    DENIED,
    FAILED,
}
