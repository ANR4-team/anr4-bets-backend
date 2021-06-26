package di

import org.koin.dsl.module
import service.*

val serviceModule = module {

    single { UserService(userRepository = get()) }
}