package swagger

import de.nielsfalk.ktor.swagger.version.shared.Group
import io.ktor.locations.*

@Suppress("EXPERIMENTAL_API_USAGE")
object Routes {

    @Group("user")
    @Location("/login")
    object Login

    @Group("user")
    @Location("/user")
    object User
}
