package org.dana.score.model

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AthleteService {
    fun init () {
        transaction {
            if (Athletes.exists()) {
                SchemaUtils.drop(Athletes)
            }
            SchemaUtils.create(Athletes)
            Athletes.insert {
                it[name] = "Beckett"
                it[club] = "CCG"
                it[level] = 10
            }
            Athletes.insert {
                it[name] = "Tyler"
                it[club] = "CCG"
                it[level] = 8
            }
        }
    }

    fun getAthlete(id: Int) : Athlete = transaction {
        Athletes.select { Athletes.id.eq(id) }.single().toAthlete()
    }

    fun getAllAthletes() = transaction {
        Athletes.selectAll().map { it.toAthlete() }
    }

    fun createAthlete(athlete: Athlete) = transaction {
        Athletes.insertAndGetId {
            it[name] = athlete.name
            it[club] = athlete.club
            it[level] = athlete.level
        }
    }

    fun deleteAthlete(id: Int) = transaction {
        Athletes.deleteWhere { Athletes.id eq id }
    }

    fun editAthlete(athlete: Athlete) = transaction {
        Athletes.update({ Athletes.id eq athlete.id }) {
            it[name] = athlete.name
            it[club] = athlete.club
            it[level] = athlete.level
        }
    }
}

fun ResultRow.toAthlete() = Athlete(this[Athletes.name],
                                    this[Athletes.club],
                                    this[Athletes.level],
                                    this[Athletes.id].value)

object Athletes : IntIdTable() {
    val name = varchar("name", 100)
    val club = varchar("club", 100)
    val level = integer("level")
}

data class Athlete (val name: String, val club: String, val level: Int, val id: Int? = null)