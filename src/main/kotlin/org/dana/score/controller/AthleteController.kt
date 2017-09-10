package org.dana.score.controller

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.dana.score.model.Athlete
import org.dana.score.model.AthleteService
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.HttpHeaders
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.*

fun Route.athlete(athleteService: AthleteService, moshi: Moshi) {
    route("/athlete") {
        get("/") {
            // list athletes
            val athletes = athleteService.getAllAthletes()
            val type = Types.newParameterizedType(List::class.java, Athlete::class.java)
            val athleteListSerializer = moshi.adapter<List<Athlete>>(type)
            call.respondText(athleteListSerializer.toJson(athletes), ContentType.Application.Json)
        }
        get("/{id}") {
            // athlete detail
            val athlete = athleteService.getAthlete(call.parameters["id"]!!.toInt())
            val athleteSerializer = moshi.adapter(Athlete::class.java)
            call.respondText(athleteSerializer.toJson(athlete), ContentType.Application.Json)
        }
        put("/{id}") {
            val athleteSerializer = moshi.adapter(Athlete::class.java)
            val id = athleteSerializer.fromJson(call.request.receive(String::class))?.let {
                athleteService.editAthlete(it)
            }
            call.response.headers.append(HttpHeaders.Location, "/athlete/$id")
            call.respond(HttpStatusCode.SeeOther)
        }
        post("/") {
            val athleteSerializer = moshi.adapter(Athlete::class.java)
            val id = athleteSerializer.fromJson(call.request.receive(String::class))?.let {
                athleteService.createAthlete(it)
            }
            call.response.headers.append(HttpHeaders.Location, "/athlete/$id")
            call.respond(HttpStatusCode.SeeOther)
        }
        delete("/{id}") {
            call.parameters["id"]?.toInt()?.let { athleteService.deleteAthlete(it) }
            call.respond(HttpStatusCode.OK)
        }
    }
}

