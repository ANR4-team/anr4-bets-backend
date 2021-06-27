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
import utils.getBody
import utils.getUser

@ContextDsl
inline fun <reified R : Any, reified B : Any> Route.postNoAuth(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, B) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, B>(HttpMethod.Post)
    }

    return post<R> inner@ {
        val body = getBody<B>() ?: return@inner
        block(it, body)
    }
}

@ContextDsl
inline fun <reified R : Any, reified B : Any> Route.post(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, B, User) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, B>(HttpMethod.Post)
    }

    return post<R> inner@ {
        val user = getUser() ?: return@inner
        val body = getBody<B>() ?: return@inner
        block(it, body, user)
    }
}

@ContextDsl
inline fun <reified R : Any> Route.postNoBody(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, User) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, Unit>(HttpMethod.Post)
    }

    return post<R> inner@ {
        val user = getUser() ?: return@inner
        block(it, user)
    }
}