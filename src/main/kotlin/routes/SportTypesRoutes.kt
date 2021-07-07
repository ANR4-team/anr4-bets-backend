package routes

import data.SportType
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.SportTypeService
import swagger.*
import swagger.methods.get
import utils.respondService

fun Routing.sportTypesRoutes() {

    val sportTypeService by inject<SportTypeService>()

    authenticate {

        get<Routes.SportType>(
            "Get all sport types"
                .responds(
                    okWithListExample<SportType>(),
                    unauthorized(),
                )
        ) { _, _ -> call.respondService(sportTypeService.getAllSportTypes()) }

        get<Routes.SportTypeWithId>(
            "Get sport types by id"
                .responds(
                    okWithExample<SportType>(),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>(),
                )
        ) { route, _ -> call.respondService(sportTypeService.getSportTypeById(route.id)) }
    }
}