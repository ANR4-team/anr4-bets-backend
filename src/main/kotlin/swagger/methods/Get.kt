@file:Suppress("EXPERIMENTAL_API_USAGE")

package swagger.methods

import data.auth.User
import de.nielsfalk.ktor.swagger.Metadata
import de.nielsfalk.ktor.swagger.swaggerUi
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.routing.Route
import io.ktor.routing.application
import io.ktor.util.pipeline.*
import utils.getUser

@ContextDsl
inline fun <reified R : Any> Route.get(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, User) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, Unit>(HttpMethod.Get)
    }

    return get<R> inner@ {
        val user = getUser() ?: return@inner
        block(it, user)
    }
}

@ContextDsl
inline fun <reified R : Any> Route.getNoAuth(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, Unit>(HttpMethod.Get)
    }

    return get<R> inner@ {
        block(it)
    }
}