package com.example.pswebapi

import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.kotlin.core.publisher.toMono

@RestController
class PaymentController(private val paymentRepository: PaymentRepository) {

    @GetMapping
    fun healthcheck() = "OK".toMono()

    @GetMapping("payments")
    fun getPayments() = paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))

    @PostMapping("payments")
    fun addPayment(@RequestBody payment: Payment) = paymentRepository.save(payment)
}
