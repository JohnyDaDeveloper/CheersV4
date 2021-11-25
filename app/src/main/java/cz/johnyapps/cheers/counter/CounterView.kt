package cz.johnyapps.cheers.counter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.counter.dto.CounterEntity
import cz.johnyapps.cheers.databinding.ViewCounterBinding
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.dto.Entry
import cz.johnyapps.cheers.global.utils.clicks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CounterView: LinearLayoutCompat {
    private val counter = MutableStateFlow<CounterEntity?>(null)
    private val count = MutableStateFlow<List<Entry>>(ArrayList())
    private val jobList = ArrayList<Job>()

    private val _counterUpdate = MutableSharedFlow<Counter>()
    val counterUpdate: SharedFlow<Counter> = _counterUpdate

    private val _height = MutableStateFlow(0)
    val height: StateFlow<Int> = _height

    private var binding: ViewCounterBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_counter,
        this,
        true
    )

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _height.value = h
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val lifecycleOwner = findViewTreeLifecycleOwner() as LifecycleOwner

        jobList.add(
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    counter.collect {
                        binding.counter = it

                        if (it == null) {
                            count.emit(ArrayList())
                        } else {
                            it.entries.collect { list ->
                                count.emit(list)
                            }
                        }
                    }
                }
            }
        )

        jobList.add(
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.plusButton.clicks().collect {
                        counter.value?.increaseCount()
                    }
                }
            }
        )

        jobList.add(
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.minusButton.clicks().collect {
                        counter.value?.decreaseCount()
                    }
                }
            }
        )

        jobList.add(
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    count.collect {
                        binding.counterEntries = it
                    }
                }
            }
        )

        jobList.add(
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    count.drop(1).collect {
                        val counter = this@CounterView.counter.value

                        counter?.let {
                            _counterUpdate.emit(counter.toGlobalDto())
                        }
                    }
                }
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        jobList.forEach {
            it.cancel()
        }
    }

    suspend fun setCounter(counter: Counter) {
        this.counter.emit(CounterEntity(counter))
    }
}