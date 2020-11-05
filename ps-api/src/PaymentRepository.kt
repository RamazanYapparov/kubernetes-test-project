package com.example

import org.litote.kmongo.coroutine.CoroutineClient

class PaymentRepository(private val mongoClient: CoroutineClient) {
    suspend fun getPayments() =
        mongoClient.getDatabase("ps")
            .getCollection<Payment>("payments")
            .find()
            .toList()

    suspend fun addPayment(payment: Payment) =
        mongoClient.getDatabase("ps")
            .getCollection<Payment>("payments")
            .save(payment)
            ?.apply {
                payment.copy(_id = upsertedId?.asObjectId()?.value)
            } ?: payment
}