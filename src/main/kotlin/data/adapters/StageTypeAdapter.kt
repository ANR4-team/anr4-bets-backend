package data.adapters

import com.google.gson.*
import data.StageType
import utils.lowercase
import java.lang.reflect.Type

class StageTypeAdapter : JsonSerializer<StageType>, JsonDeserializer<StageType> {

    override fun serialize(stageType: StageType, type: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(stageType.name.lowercase())
    }

    override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): StageType {
        return when (jsonElement.asString) {
            "custom" -> StageType.CUSTOM
            "play_off" -> StageType.PLAY_OFF
            "league" -> StageType.LEAGUE
            else -> throw IllegalArgumentException("Stage type cannot be ${jsonElement.asString}")
        }
    }
}
