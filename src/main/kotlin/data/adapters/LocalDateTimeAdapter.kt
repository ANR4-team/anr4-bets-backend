package data.adapters

import com.google.gson.*
import io.ktor.util.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private val datePattern = "yyyy-MM-dd'T'HH:mm:ssZ"

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime? {
        return json?.asJsonPrimitive?.asString?.let {
            SimpleDateFormat(datePattern, Locale.getDefault()).parse(it).toLocalDateTime()
        }
    }

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        return ZonedDateTime.of(src, ZoneId.systemDefault())?.format(dateTimeFormatter)?.let { JsonPrimitive(it) }
    }
}