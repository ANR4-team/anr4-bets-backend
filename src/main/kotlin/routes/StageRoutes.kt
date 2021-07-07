package routes

import data.Stage
import data.StageCreateBody
import data.StageUpdateBody
import data.Tournament
import de.nielsfalk.ktor.swagger.ok
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.StageService
import swagger.*
import swagger.methods.delete
import swagger.methods.get
import swagger.methods.post
import swagger.methods.put
import utils.respondService

fun Routing.stageRoutes() {

    val stageService by inject<StageService>()

    authenticate {

        get<Routes.Stages>(
            "Get stages of tournament"
                .responds(
                    okWithListExample<Stage>(),
                    badRequest(),
                    unauthorized(),
                    notFound<Tournament>(),
                )
        ) { route, _ -> call.respondService(stageService.getAllStages(route.tournamentId)) }

        get<Routes.StagesWithId>(
            "Get stage by ID"
                .responds(
                    okWithExample<Stage>(),
                    badRequest(),
                    unauthorized(),
                    notFound<Stage>(),
                )
        ) { route, _ -> call.respondService(stageService.getStage(route.id)) }

        post<Routes.Stages, StageCreateBody>(
            "Create new stage in tournament"
                .requestBodyExample<StageCreateBody>()
                .responds(
                    okWithExample<Stage>(),
                    badRequest(),
                    unauthorized(),
                    notFound<Tournament>(),
                    forbidden("This user is not allowed to modify this tournament"),
                )
        ) { route, body, user -> call.respondService(stageService.createStage(user.id, route.tournamentId, body)) }

        put<Routes.StagesWithId, StageUpdateBody>(
            "Update stage"
                .requestBodyExample<StageUpdateBody>()
                .responds(
                    okWithExample<Stage>(),
                    badRequest(),
                    unauthorized(),
                    notFound<Stage>(),
                    forbidden("This user is not allowed to modify this tournament"),
                )
        ) { route, body, user -> call.respondService(stageService.updateStage(user.id, route.id, body)) }

        delete<Routes.StagesWithId>(
            "Delete stage of tournament"
                .responds(
                    ok<Unit>(),
                    badRequest(),
                    unauthorized(),
                    notFound<Stage>(),
                    forbidden("This user is not allowed to modify this tournament"),
                )
        ) { route, user -> call.respondService(stageService.deleteStage(user.id, route.id)) }
    }
}
