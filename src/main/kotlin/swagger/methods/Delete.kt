@file:Suppress("EXPERIMENTAL_API_USAGE")

package swagger.methods

import data.auth.User
import de.nielsfalk.ktor.swagger.Metadata
import de.nielsfalk.ktor.swagger.swaggerUi
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import utils.getBody
import utils.getUser

@ContextDsl
inline fun <reified R : Any, reified B : Any> Route.delete(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, B, User) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, B>(HttpMethod.Delete)
    }
    return delete<R> inner@ {
        val user = getUser() ?: return@inner
        val body = getBody<B>() ?: return@inner
        block(it, body, user)
    }
}

@ContextDsl
inline fun <reified R : Any> Route.delete(
    metadata: Metadata,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(R, User) -> Unit
): Route {
    application.swaggerUi.apply {
        metadata.apply<R, Unit>(HttpMethod.Delete)
    }
    return delete<R> inner@ {
        val user = getUser() ?: return@inner
        block(it, user)
    }
}