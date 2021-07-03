import annotations.CustomField
import annotations.IntField
import annotations.SampleModel
import annotations.StringField
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.tools.Diagnostic
import kotlin.reflect.KClass

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(SampleProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class SampleProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(SampleModel::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "NO DIR")
            return false
        }
        val annotatedClasses = roundEnv.getElementsAnnotatedWith(SampleModel::class.java)
        if (annotatedClasses.isEmpty()) {
            return false
        }
        val fileSpec = processSamples(annotatedClasses)
        val file = File(generatedSourcesRoot).also { it.mkdir() }
        fileSpec.writeTo(file)
        return true
    }

    private fun processSamples(annotations: Set<Element>): FileSpec {
        return FileSpec.builder("data", "SwaggerSamples.kt")
            .apply {
                annotations.forEach { modelElement ->
                    val fields = modelElement.enclosedElements
                        .filter { it.kind == ElementKind.FIELD }
                        .mapNotNull { element ->
                            element.getIntField()
                                ?: element.getStringField()
                                ?: element.getCustomField()
                        }
                    addFunction(
                        FunSpec.builder("get${modelElement.simpleName}Sample")
                            .returns(ParameterizedTypeName.get(Map::class, String::class, Any::class))
                            .apply {
                                if (fields.isEmpty()) {
                                    addStatement("return mapOf()")
                                } else {
                                    addCode("return mapOf(\n")
                                    fields.forEach { addCode(it) }
                                    addCode(")")
                                }
                            }
                            .build()
                    )
                }
                addFunction(annotations.createMapperForModels())
                println(annotations)
            }
            .build()
    }

    private fun Set<Element>.createMapperForModels(): FunSpec {
        return FunSpec.builder("getExampleModel")
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(TypeVariableName.invoke("T").reified(true))
            .returns(ParameterizedTypeName.get(Map::class, String::class, Any::class))
            .addCode("return when (T::class.java) {\n")
            .apply {
                forEach {
                    addCode("\t%T::class.java -> get${it.simpleName}Sample()\n", it.asType())
                }
            }
            .addCode("""
                    else -> throw kotlin.Exception("Cannot find sample model for this type")
                }
            """.trimIndent())
            .build()
    }

    private fun Element.getIntField(): CodeBlock? {
        val intField = getAnnotationsByType(IntField::class.java).firstOrNull() ?: return null
        return CodeBlock.of("\t\"$simpleName\" to ${intField.value},\n")
    }

    private fun Element.getStringField(): CodeBlock? {
        val stringField = getAnnotationsByType(StringField::class.java).firstOrNull() ?: return null
        return CodeBlock.of("\t\"$simpleName\" to \"${stringField.value}\",\n")
    }

    private fun Element.getCustomField(): CodeBlock? {
        if (getAnnotationsByType(CustomField::class.java).firstOrNull() == null) {
            return null
        }
        val type = getAnnotationClassValue<CustomField> { valueType }
            .asTypeName().toString()
            .substringAfterLast(".")
        return CodeBlock.of("\t\"$simpleName\" to get${type}Sample(),\n")
    }

    private inline fun <reified T : Annotation> Element.getAnnotationClassValue(block: T.() -> KClass<*>) = try {
        getAnnotation(T::class.java).block()
        throw Exception("E")
    } catch (e: MirroredTypeException) {
        e.typeMirror
    }
}