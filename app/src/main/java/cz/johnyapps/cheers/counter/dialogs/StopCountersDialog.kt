package cz.johnyapps.cheers.counter.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.global.dto.Counter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class StopCountersDialog(
    private val counters: List<Counter>
): DialogFragment() {
    private val _stop = MutableSharedFlow<List<Counter>>()
    val stop: SharedFlow<List<Counter>> = _stop

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val count = counters.size

        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(requireContext().resources.getQuantityString(
            R.plurals.dialog_stop_counters_title,
            count,
            if (count == 1) counters[0].beverage.name else count.toString()
        ))
            .setPositiveButton(R.string.stop) { _, _ ->
                lifecycleScope.launch { _stop.emit(counters) }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }

        return builder.create()
    }

    companion object {
        const val TAG = "StopCountersDialog"
    }
}