import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.create

fun DependencyHandler.ktor(name: String) = create("io.ktor", name, Versions.Ktor)

fun DependencyHandler.koin(name: String) = create("org.koin", name, Versions.Koin)

fun DependencyHandler.exposed(name: String) = create("org.jetbrains.exposed", name, Versions.Exposed)
