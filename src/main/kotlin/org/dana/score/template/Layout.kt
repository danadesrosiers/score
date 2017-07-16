package org.dana.score.template

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.kotlinx.css.*
import java.awt.DisplayMode

val jQuerySource = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"

fun layout(block: FlowContent.() -> Unit) = createHTML().
html {
    head {
        title("Athletes")
        script(type = "text/javascript", src = jQuerySource)
        script(type = "text/javascript", src = "js/score.js")
        style {
            +StyleContainer().invoke {
                table.or.td.or.th {
                    border("1px solid black")
                    border.collapse("collapse")
                    padding("5px")
                }
                form {
                    nested.span {
                        display("inline-block")
                        width("100px")
                        padding("5px")
                    }

                }
            }.toString()
        }
    }
    body {
        block()
    }
}


