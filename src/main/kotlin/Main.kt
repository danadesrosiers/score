
import org.apache.log4j.BasicConfigurator
import org.dana.score.controller.athlete
import org.dana.score.controller.home
import org.dana.score.model.AthleteService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing
import java.io.File

fun main(args: Array<String>) {
    BasicConfigurator.configure()
    val dir = File("target/db")
    Database.connect("jdbc:h2:file:${dir.canonicalFile.absolutePath}", driver = "org.h2.Driver")
    val athleteService = AthleteService()
    athleteService.init()
    val server = embeddedServer(Netty, 8080) {
        install(Routing) {
            home()
            athlete(athleteService)
        }
    }

    server.start(wait = true)
}
