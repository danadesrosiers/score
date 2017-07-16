package org.dana.score.template.Athlete

import kotlinx.html.*
import org.dana.score.model.Athlete
import org.dana.score.template.layout

fun athleteListTemplate(athletes: List<Athlete>) = layout {
    h1 { +"Athletes" }
    athleteTable(athletes)
    div {
        button(classes = "create-athlete-button") { +"Create Athlete" }
    }
    div ("athlete-form-container")
}

fun FlowContent.athleteTable(athletes: List<Athlete>) {
    table("athleteTable") {
        tr {
            th { +"Name" }
            th { +"Club" }
            th { +"Level" }
        }
        athletes.forEach {
            tr {
                td { +it.name }
                td { +it.club }
                td { +"${it.level}" }
                td {
                    a("javascript:void(0)", classes = "delete-athlete") {
                        id = "delete-${it.id}"
                        +"delete"
                    }
                }
            }
        }
    }
}


