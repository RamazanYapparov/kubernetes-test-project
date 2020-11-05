package com.example.pswebapi

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant

@Document
data class Payment(
        @Id val id: String? = null,
        val date: Instant = Instant.now(),
        val amount: BigDecimal = 0.toBigDecimal(),
        val from: String = "",
        val to: String = "",
        val status: Status = Status.NONE
)

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class Status {
    NONE,
    PENDING,
    DONE,
    DENIED,
    FAILED,
}
