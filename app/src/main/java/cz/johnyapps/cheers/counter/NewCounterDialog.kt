package cz.johnyapps.cheers.counter

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.johnyapps.cheers.Logger
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.DialogNewCounterBinding
import cz.johnyapps.cheers.dto.Beverage
import cz.johnyapps.cheers.dto.Counter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class NewCounterDialog: DialogFragment() {
    private lateinit var binding: DialogNewCounterBinding
    private val _newCounter: MutableSharedFlow<Counter> = MutableSharedFlow()
    val newCounter: SharedFlow<Counter> = _newCounter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewCounterBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_new_counter_title)
            .setPositiveButton(R.string.create, null)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setView(binding.root)
            .create()

        dialog.setOnShowListener {
            val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                createAndEmitCounter()
            }
        }

        return dialog
    }

    private fun createAndEmitCounter() {
        val name = binding.beverageNameEditText.text.toString()
        var alcoholStr = binding.alcoholEditText.text.toString()
        var volumeStr = binding.volumeEditText.text.toString()
        var alcohol = 0F
        var volume = 0F


        if (alcoholStr.isEmpty()) {
            alcoholStr = "0"
        }

        if (volumeStr.isEmpty()) {
            volumeStr = "0"
        }

        try {
            alcohol = alcoholStr.toFloat()
            volume = volumeStr.toFloat()
        } catch (e: Exception) {
            Logger.w(TAG, "createAndEmitCounter: Failed to parse alcohol ($alcoholStr) or volume ($volumeStr)", e)
        }

        alcohol /= 100

        if (name.isNotEmpty() && alcohol >= 0 && volume > 0) {
            Logger.d(TAG, "createAndEmitCounter: New counter '$name' created")

            lifecycleScope.launch {
                _newCounter.emit(Counter(Beverage(name, Color.RED, Color.BLACK), alcohol, volume))
            }

            dismiss()
        } else {
            Logger.d(TAG, "createAndEmitCounter: Counter creation failed")

            when {
                name.isEmpty() -> {
                    Toast.makeText(requireContext(), R.string.dialog_new_counter_empty_name, Toast.LENGTH_LONG).show()
                }
                volume <= 0 -> {
                    Toast.makeText(requireContext(), R.string.dialog_new_counter_volume_zero, Toast.LENGTH_LONG).show()
                }
                alcohol <= 0 -> {
                    Toast.makeText(requireContext(), R.string.dialog_new_counter_alcohol_zero, Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(requireContext(), R.string.dialog_new_counter_general_error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "NewCounterDialog"
    }
}