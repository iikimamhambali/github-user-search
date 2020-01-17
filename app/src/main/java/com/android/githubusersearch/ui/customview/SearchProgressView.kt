package com.android.githubusersearch.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.android.githubusersearch.R
import kotlinx.android.synthetic.main.layout_progress_search.view.*

class SearchProgressView : LinearLayout {

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrSet: AttributeSet?) : super(context, attrSet) {
        initView()
    }

    constructor(context: Context?, attrSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attrSet, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(
            R.layout.layout_progress_search,
            this, false)
        addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun startShimmer() {
        sflSearch?.startShimmer()
    }

    fun stopShimmer() {
        sflSearch?.stopShimmer()
    }

}