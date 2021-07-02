package swagger

import data.getExampleModel
import de.nielsfalk.ktor.swagger.*

inline fun <reified T> String.requestBodyExample(): Metadata {
    return examples(example(T::class.java.simpleName, getExampleModel<T>()))
}

inline fun <reified T> okWithExample() = ok<T>(example(T::class.java.simpleName, getExampleModel<T>()))

inline fun <reified T> okWithListExample() = ok<T>(example(T::class.java.simpleName, listOf(getExampleModel<T>())))