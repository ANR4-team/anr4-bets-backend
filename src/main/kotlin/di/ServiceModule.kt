package di

import org.koin.dsl.module
import service.*

val serviceModule = module {

    single { UserService(userRepository = get()) }

    single { SportTypeService(sportTypeRepository = get()) }

    single { ParticipantService(participantRepository = get(), sportTypeRepository = get()) }

    single { TournamentService(tournamentRepository = get(), sportTypeRepository = get()) }

    single { StageService(stageRepository = get(), tournamentRepository = get()) }
}