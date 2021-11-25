package cz.johnyapps.cheers.global.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
fun View.clicks() = callbackFlow {
    setOnClickListener {
        trySend(it)
    }

    awaitClose { setOnClickListener(null)}
}

@ExperimentalCoroutinesApi
fun View.clicks(scope: CoroutineScope) = callbackFlow {
    setOnClickListener {
        scope.launch {
            send(it)
        }
    }

    awaitClose { setOnClickListener(null) }
}

@ExperimentalCoroutinesApi
fun View.longClicks(scope: CoroutineScope) = callbackFlow {
    setOnLongClickListener { v ->
        scope.launch {
            send(v)
        }

        true
    }

    awaitClose { setOnLongClickListener(null) }
}

