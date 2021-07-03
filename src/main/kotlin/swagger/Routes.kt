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
    class SportType

    @Group("sport-types")
    @Location("/sport-types/{id}")
    class SportTypeWithId(val id: UUID)

    @Group("participants")
    @Location("/participants")
    class Participant

    @Group("participants")
    @Location("/participants/{id}")
    class ParticipantWithId(val id: Int)

    @Group("tournaments")
    @Location("/tournaments")
    class Tournaments

    @Group("tournaments")
    @Location("/tournaments/{id}")
    class TournamentsWithId(val id: Int)
}
