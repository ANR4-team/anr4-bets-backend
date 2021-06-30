package di

import org.koin.dsl.module
import repository.*

val repositoryModule = module {

    single { UserRepository(database = get()) }

    single { SportTypeRepository(database = get()) }
}