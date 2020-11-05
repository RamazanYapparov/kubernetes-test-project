package com.example.pswebapi

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : ReactiveMongoRepository<Payment, ObjectId>