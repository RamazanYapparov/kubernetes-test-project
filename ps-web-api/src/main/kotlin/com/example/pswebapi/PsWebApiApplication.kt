package com.example.pswebapi

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer


@SpringBootApplication
class PsWebApiApplication

fun main(args: Array<String>) {
    runApplication<PsWebApiApplication>(*args)
}

@Configuration
class MongoConfiguration: AbstractReactiveMongoConfiguration() {
    @Value("\${spring.data.mongodb.host}")
    lateinit var host: String
    @Value("\${spring.data.mongodb.port}")
    lateinit var port: String

    override fun getDatabaseName() = "pm"

    override fun reactiveMongoClient(): MongoClient = MongoClients.create("mongodb://${host}:${port}")
}

@Configuration
class CorsGlobalConfiguration : WebFluxConfigurer {
    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600)
    }
}