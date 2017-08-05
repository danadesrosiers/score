package org.dana.score.controller

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.dana.score.model.Athlete
import org.dana.score.model.AthleteService
import org.dana.score.model.toAthlete
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.receive
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.HttpHeaders
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.*
import org.jetbrains.ktor.util.ValuesMap

fun Route.athlete(athleteService: AthleteService, moshi: Moshi) {
    route("/athlete") {
        get("/") {
            // list athletes
            val athletes = athleteService.getAllAthletes()
            val type = Types.newParameterizedType(List::class.java, Athlete::class.java)
            val athleteListSerializer = moshi.adapter<List<Athlete>>(type)
            call.respondText(athleteListSerializer.toJson(athletes), ContentType.Text.Html)
        }
        get("/{id}") {
            // athlete detail
            val athlete = athleteService.getAthlete(call.parameters["id"]!!.toInt())
            val athleteSerializer = moshi.adapter(Athlete::class.java)
            call.respondText(athleteSerializer.toJson(athlete), ContentType.Application.Json)
        }
        post("/") {
            athleteService.createAthlete(call.request.receive<ValuesMap>().toAthlete())
            call.respondRedirect("/athlete")
        }
        delete("/{id}") {
            athleteService.deleteAthlete(call.parameters["id"]!!.toInt())
            call.response.headers.append(HttpHeaders.Location, "/athlete")
            call.respond(HttpStatusCode.SeeOther)
        }
    }
}

