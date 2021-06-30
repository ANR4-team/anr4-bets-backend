package routes

import data.SportType
import data.SportTypeBody
import de.nielsfalk.ktor.swagger.ok
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.SportTypeService
import swagger.*
import swagger.methods.delete
import swagger.methods.get
import swagger.methods.post
import swagger.methods.put
import utils.respondService

fun Routing.sportTypesRoutes() {

    val sportTypeService by inject<SportTypeService>()

    authenticate {

        get<Routes.SportType>(
            "Get all sport types"
                .responds(
                    okWithListExample<SportType>(SportType.example()),
                    unauthorized(),
                )
        ) { _, _ -> call.respondService(sportTypeService.getAllSportTypes()) }

        post<Routes.SportType, SportTypeBody>(
            "Create new sport type"
                .sample<SportTypeBody>(SportTypeBody.example())
                .responds(
                    okWithExample<SportType>(SportType.example()),
                    unauthorized(),
                    badRequest(),
                    conflict(),
                )
        ) { _, (name), _ -> call.respondService(sportTypeService.createSportType(name)) }

        put<Routes.SportType.WithId, SportTypeBody>(
            "Rename sport type"
                .sample<SportTypeBody>(SportTypeBody.example())
                .responds(
                    okWithExample<SportType>(SportType.example()),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>(),
                    conflict(),
                )
        ) { route, (name), _ -> call.respondService(sportTypeService.updateSportType(route.id, name)) }

        delete<Routes.SportType.WithId>(
            "Delete sport type"
                .responds(
                    ok<Unit>(),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>(),
                )
        ) { route, _ -> call.respondService(sportTypeService.deleteSportType(route.id)) }
    }
}