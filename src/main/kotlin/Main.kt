
import com.squareup.moshi.Moshi
import org.apache.log4j.BasicConfigurator
import org.dana.score.controller.athlete
import org.dana.score.model.AthleteService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CORS
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing
import java.io.File

fun main(args: Array<String>) {
    BasicConfigurator.configure()
    val dir = File("db/db")
    Database.connect("jdbc:h2:file:${dir.canonicalFile.absolutePath}", driver = "org.h2.Driver")
    val athleteService = AthleteService()
    athleteService.init()
    val moshi = Moshi.Builder().build()

    val server = embeddedServer(Netty, 8080) {
        install(CORS) {
            anyHost()
        }
        install(Routing) {
            athlete(athleteService, moshi)
        }
    }

    server.start(wait = true)
}
