package utils.logger

fun Tagged.log(message: String) {
    println("$tag: $message")
}

fun Tagged.logError(message: String) {
    System.err.println("$tag: $message")
}