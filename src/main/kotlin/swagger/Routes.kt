package swagger

import de.nielsfalk.ktor.swagger.version.shared.Group
import io.ktor.locations.*
import java.util.*

@Suppress("EXPERIMENTAL_API_USAGE")
object Routes {

    @Group("user")
    @Location("/login")
    object Login

    @Group("user")
    @Location("/user")
    object User

    @Group("sport-types")
    @Location("/sport-types")
    class SportType {

        @Group("sport-types")
        @Location("/sport-types/{id}")
        data class WithId(val parent: SportType, val id: UUID)
    }
}
