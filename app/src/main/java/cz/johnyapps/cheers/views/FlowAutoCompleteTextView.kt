package cz.johnyapps.cheers.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class FlowAutoCompleteTextView: AppCompatAutoCompleteTextView {
    private val _textFlow = MutableSharedFlow<String?>()
    val textFlow: SharedFlow<String?> = _textFlow

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    init {
        addTextChangedListener {
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                _textFlow.emit(it?.toString())
            }
        }
    }
}