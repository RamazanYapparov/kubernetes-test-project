package com.example

import com.google.gson.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import java.time.*
import io.ktor.gson.*
import org.bson.types.ObjectId
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine
import java.lang.reflect.Type
import java.time.format.DateTimeFormatter

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.ContentType)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(Instant::class.java, object : JsonDeserializer<Instant> {
                override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Instant {
                    return Instant.parse(json.asJsonPrimitive.asString)
                }
            })
            registerTypeAdapter(Instant::class.java, object : JsonSerializer<Instant> {
                override fun serialize(instant: Instant, type: Type, context: JsonSerializationContext?): JsonElement {
                   return JsonPrimitive(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Europe/Moscow")).format(instant))
                }
            })
            registerTypeAdapter(ObjectId::class.java, object : JsonSerializer<ObjectId> {
                override fun serialize(objectId: ObjectId, p1: Type?, p2: JsonSerializationContext?): JsonElement {
                    return JsonPrimitive(objectId.toHexString())
                }
            })
        }
    }

    install(Koin) {
       modules(
           module() {
               single { KMongo.createClient("mongodb://admin:admin@0.0.0.0:27017").coroutine }
           }
       )
    }

    routing {
        payments()

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        webSocket("/myws/echo") {
            send(Frame.Text("Hi from server"))
            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

