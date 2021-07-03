package routes

import data.SportType
import data.Tournament
import data.TournamentCreateBody
import data.TournamentUpdateBody
import de.nielsfalk.ktor.swagger.ok
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.TournamentService
import swagger.*
import swagger.methods.delete
import swagger.methods.get
import swagger.methods.post
import swagger.methods.put
import utils.respondService

fun Routing.tournamentRoutes() {

    val tournamentService by inject<TournamentService>()

    authenticate {

        get<Routes.Tournaments>(
            "Get all tournaments"
                .responds(
                    okWithListExample<Tournament>(),
                    unauthorized(),
                )
        ) { _, _ -> call.respondService(tournamentService.getAllTournaments()) }

        get<Routes.TournamentsWithId>(
            "Get tournament by id"
                .responds(
                    okWithExample<Tournament>(),
                    unauthorized(),
                    badRequest(),
                    notFound<Tournament>(),
                )
        ) { route, _ -> call.respondService(tournamentService.getTournamentById(route.id)) }

        post<Routes.Tournaments, TournamentCreateBody>(
            "Create new tournament"
                .requestBodyExample<TournamentCreateBody>()
                .responds(
                    okWithExample<Tournament>(),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>(),
                    conflict("Dates messed up"),
                )
        ) { _, body, user -> call.respondService(tournamentService.create(user, body)) }

        put<Routes.TournamentsWithId, TournamentUpdateBody>(
            "Update tournament"
                .requestBodyExample<TournamentUpdateBody>()
                .responds(
                    okWithExample<Tournament>(),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>("Tournament or sport type not found"),
                    conflict("Dates messed up"),
                )
        ) { route, body, _ -> call.respondService(tournamentService.update(route.id, body)) }

        delete<Routes.TournamentsWithId>(
            "Delete tournament"
                .responds(
                    ok<Unit>(),
                    unauthorized(),
                    notFound<Tournament>(),
                )
        ) { route, _ -> call.respondService(tournamentService.delete(route.id)) }
    }
}