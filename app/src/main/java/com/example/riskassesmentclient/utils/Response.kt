package com.example.riskassesmentclient.utils

import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * This functions is used to simplify work with Kotlin coroutines
 */

typealias Response<K> = Deferred<Result<K, Exception>>

fun launchUI(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = GlobalScope.launch(context, start, block)


inline fun <T : Any> asyncR(crossinline callback: () -> T): Response<T> =
    GlobalScope.async {
        Result.of {
            callback.invoke()
        }
    }

suspend inline fun <V : Any> Response<V>.awaitFold(
    success: (V) -> Unit,
    failure: (Exception) -> Unit
) = this.await().fold(success, failure)


