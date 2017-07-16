package org.dana.score.controller

import org.dana.score.model.AthleteService
import org.dana.score.model.toAthlete
import org.dana.score.template.Athlete.athleteDetailTemplate
import org.dana.score.template.Athlete.athleteListTemplate
import org.dana.score.template.Athlete.createAthleteFormTemplate
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.receive
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.HttpHeaders
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.*
import org.jetbrains.ktor.util.ValuesMap

fun Route.athlete(athleteService: AthleteService) {
    route("/athlete") {
        get("/") {
            // list athletes
            val athletes = athleteService.getAllAthletes()
            call.respondText(athleteListTemplate(athletes), ContentType.Text.Html)
        }
        get("/{id}") {
            // athlete detail
            val athlete = athleteService.getAthlete(call.parameters["id"]!!.toInt())
            call.respondText(athleteDetailTemplate(athlete), ContentType.Text.Html)
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
        get("/createForm") {
            call.respondText(createAthleteFormTemplate(), ContentType.Text.Html)
        }
    }
}

