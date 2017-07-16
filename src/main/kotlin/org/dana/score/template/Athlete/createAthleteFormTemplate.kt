package org.dana.score.template.Athlete

import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun createAthleteFormTemplate() = createHTML().
div("form") {
    h2 { +"Create Athlete" }
    form (method = FormMethod.post, action = "athlete") {
        div {
            span { +"Name:" }
            input (type = InputType.text, name = "name")
        }
        div {
            span { +"Club:" }
            input (type = InputType.text, name = "club")
        }
        div {
            span { +"Level:" }
            input (type = InputType.number, name = "level")
        }
        div {
            input (type = InputType.submit)
        }
    }
}