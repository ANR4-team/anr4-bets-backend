package di

import org.koin.dsl.module
import repository.*

val repositoryModule = module {

    single { UserRepository(database = get()) }

    single { SportTypeRepository(database = get()) }

    single { ParticipantRepository(database = get()) }

    single { TournamentRepository(database = get()) }

    single { StageRepository(database = get()) }
}