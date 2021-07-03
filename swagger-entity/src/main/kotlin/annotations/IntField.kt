package annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class IntField(
    val value: Int,
)
