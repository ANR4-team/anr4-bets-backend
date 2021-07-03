package data.adapters

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class UuidAdapter : JsonSerializer<UUID>, JsonDeserializer<UUID> {

    override fun serialize(uuid: UUID, type: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(uuid.toString())
    }

    override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): UUID? {
        return jsonElement.asString.let { UUID.fromString(it) }
    }
}