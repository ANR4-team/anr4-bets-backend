package routes

import data.Participant
import data.ParticipantCreateBody
import data.ParticipantUpdateBody
import data.SportType
import de.nielsfalk.ktor.swagger.ok
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.ParticipantService
import swagger.*
import swagger.methods.delete
import swagger.methods.get
import swagger.methods.post
import swagger.methods.put
import utils.respondService

fun Routing.participantRoutes() {

    val participantService by inject<ParticipantService>()

    authenticate {

        get<Routes.Participant>(
            "Get all participants"
                .responds(
                    okWithListExample<Participant>(),
                    unauthorized(),
                )
        ) { _, _ -> call.respondService(participantService.getAllParticipants()) }

        get<Routes.ParticipantWithId>(
            "Get participant by id"
                .responds(
                    okWithExample<Participant>(),
                    unauthorized(),
                    badRequest(),
                    notFound<Participant>(),
                )
        ) { route, _ -> call.respondService(participantService.getParticipantById(route.id)) }

        post<Routes.Participant, ParticipantCreateBody>(
            "Create new participant"
                .requestBodyExample<ParticipantCreateBody>()
                .responds(
                    okWithExample<Participant>(),
                    unauthorized(),
                    badRequest(),
                    notFound<SportType>(),
                )
        ) { _, body, _ -> call.respondService(participantService.createParticipant(body)) }

        put<Routes.ParticipantWithId, ParticipantUpdateBody>(
            "Update participant"
                .requestBodyExample<ParticipantUpdateBody>()
                .responds(
                    okWithExample<Participant>(),
                    unauthorized(),
                    badRequest(),
                    notFound<Unit>("Participant or sport type"),
                )
        ) { route, body, _ -> call.respondService(participantService.updateParticipant(route.id, body)) }

        delete<Routes.ParticipantWithId>(
            "Delete participant"
                .responds(
                    ok<Unit>(),
                    unauthorized(),
                    notFound<Participant>(),
                )
        ) { route, _ -> call.respondService(participantService.deleteParticipant(route.id)) }
    }
}