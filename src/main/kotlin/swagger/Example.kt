package swagger

import de.nielsfalk.ktor.swagger.*

inline fun <reified T> String.sample(exampleValue: Map<String, Any>): Metadata =
    examples(example(T::class.java.simpleName, exampleValue))

inline fun <reified T> okWithExample(exampleValue: Map<String, Any>): HttpCodeResponse =
    ok<T>(example(T::class.java.simpleName, exampleValue))

inline fun <reified T> okWithListExample(exampleValue: Map<String, Any>): HttpCodeResponse =
    ok<T>(example(T::class.java.simpleName, listOf(exampleValue)))