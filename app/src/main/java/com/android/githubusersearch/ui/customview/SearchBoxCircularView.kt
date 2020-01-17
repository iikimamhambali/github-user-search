package com.android.githubusersearch.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.githubusersearch.R
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_search_box_circular.view.*
import java.util.concurrent.TimeUnit

class SearchBoxCircularView : LinearLayout {

    private val textViewDisposable = CompositeDisposable()

    private var assetSearch: Drawable? = null
    private var assetClear: Drawable? = null
    private var assetClearDisable: Drawable? = null
    private val queryInput = MutableLiveData<String>()

    private var minCount = DEFAULT_MIN_COUNT

    private lateinit var searchBoxCircularView: View

    val ivSearch by lazy {
        searchBoxCircularView.ivSearch
    }

    val ivClear by lazy {
        searchBoxCircularView.ivClear
    }

    companion object {
        private const val DEFAULT_MIN_COUNT = 3
        private const val QUERY_DEBOUNCE_TIME = 500L
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    fun getQueryInput(): LiveData<String> = queryInput

    fun setMinCount(count: Int) {
        this.minCount = count
    }

    fun setHintLabel(label: String) {
        et_query_search?.hint = label
    }

    private fun initView(context: Context, attrs: AttributeSet? = null) {
        searchBoxCircularView = LayoutInflater.from(context)
            .inflate(R.layout.layout_search_box_circular, this, false)
        addView(searchBoxCircularView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        with(searchBoxCircularView) {
            et_query_search?.imeOptions = EditorInfo.IME_ACTION_DONE
            ivClear?.setOnClickListener { et_query_search?.setText("") }
        }
        textViewDisposable.addAll(
            RxTextView.textChanges(et_query_search)
                .skipInitialValue()
                .debounce(QUERY_DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    when {
                        it.isEmpty() -> {
                            queryInput.postValue("")
                            assetClearDisable?.let { drawable -> ivClear.setImageDrawable(drawable) }
                        }
                        it.isNotEmpty() -> {
                            queryInput.postValue(it.toString())
                            assetClear?.let { drawable -> ivClear.setImageDrawable(drawable) }
                        }
                        else -> assetClear?.let { drawable -> ivClear.setImageDrawable(drawable) }
                    }
                }
                .subscribe())
        attrs?.let { setAttributes(it) }
    }

    private fun setAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBoxCircularView)
        try {
            assetSearch = typedArray.getDrawable(R.styleable.SearchBoxCircularView_sbc_asset_search)
            assetClear = typedArray.getDrawable(R.styleable.SearchBoxCircularView_sbc_asset_clear)
            assetClearDisable =
                typedArray.getDrawable(R.styleable.SearchBoxCircularView_sbc_asset_clear_disable)

            ivSearch.apply {
                assetSearch?.let {
                    setImageDrawable(it)
                }
            }
            ivClear.apply {
                assetClearDisable?.let {
                    setImageDrawable(it)
                }
            }
        } finally {
            typedArray.recycle()
        }
    }

    fun setAssetSearch(@DrawableRes res: Int) {
        assetSearch = context.getDrawable(res)
        assetSearch?.let { ivSearch.setImageDrawable(it) }
    }

    fun setAssetClear(@DrawableRes res: Int) {
        assetClear = context.getDrawable(res)
        if (et_query_search.text.isNotEmpty()) ivClear.setImageDrawable(assetClear)
    }

    fun setAssetClearDisable(@DrawableRes res: Int) {
        assetClearDisable = context.getDrawable(res)
        if (et_query_search.text.isEmpty()) ivClear.setImageDrawable(assetClearDisable)
    }

    override fun onDetachedFromWindow() {
        textViewDisposable.clear()
        super.onDetachedFromWindow()
    }

}