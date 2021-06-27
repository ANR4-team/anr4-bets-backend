package utils

import com.google.api.core.ApiFutureCallback
import com.google.api.core.ApiFutures
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.UserRecord
import io.ktor.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun Application.setupFirebaseApp() {
    val serviceAccount = if (AppEnv.isTest) {
        this.javaClass.classLoader.getResourceAsStream(AppEnv.firebaseConfig)
    } else {
        AppEnv.firebaseConfig.byteInputStream()
    }
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()
    FirebaseApp.initializeApp(options)
}

suspend fun FirebaseAuth.verifyToken(token: String): FirebaseToken? {
    return suspendCoroutine { continuation ->
        val future = verifyIdTokenAsync(token)
        ApiFutures.addCallback(future, object : ApiFutureCallback<FirebaseToken> {
            override fun onFailure(t: Throwable?) {
                t?.printStackTrace()
                continuation.resume(null)
            }

            override fun onSuccess(result: FirebaseToken?) {
                continuation.resume(result)
            }
        }, Dispatchers.IO.asExecutor())
    }
}

suspend fun FirebaseAuth.getGoogleUser(idToken: String): UserRecord? {
    return suspendCoroutine { continuation ->
        val future = getUserAsync(idToken)
        ApiFutures.addCallback(future, object : ApiFutureCallback<UserRecord> {
            override fun onFailure(t: Throwable?) {
                t?.printStackTrace()
                continuation.resume(null)
            }

            override fun onSuccess(result: UserRecord?) {
                continuation.resume(result)
            }
        }, Dispatchers.IO.asExecutor())
    }
}
