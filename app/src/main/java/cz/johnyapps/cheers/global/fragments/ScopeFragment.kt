package cz.johnyapps.cheers.global.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

open class ScopeFragment: Fragment() {
    fun launchWhenStarted(action: suspend () -> Unit) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                action.invoke()
            }
        }
    }

    fun launchWhenStarted(dispatcher: CoroutineDispatcher, action: suspend () -> Unit) {
        lifecycleScope.launch(dispatcher) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                action.invoke()
            }
        }
    }
}