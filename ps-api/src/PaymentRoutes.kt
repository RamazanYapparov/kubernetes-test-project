package com.example

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineClient

fun Route.payments() {
    val mongoClient: CoroutineClient by inject()
    val paymentRepository = PaymentRepository(mongoClient)

    route("/payments") {
        get {
            call.respond(paymentRepository.getPayments())
        }

        post {
            val payment = call.receive<Payment>()
            paymentRepository.addPayment(payment)
                .let { call.respond(HttpStatusCode.Accepted, it) }
        }
    }
}