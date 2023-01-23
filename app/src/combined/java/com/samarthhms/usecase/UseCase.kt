package com.samarthhms.usecase

import com.samarthhms.domain.LoginResponse
import kotlinx.coroutines.*
import kotlin.reflect.KFunction1

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Type

    @OptIn(DelicateCoroutinesApi::class)
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Type) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }
    class None
}