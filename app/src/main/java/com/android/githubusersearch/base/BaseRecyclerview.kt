package com.android.githubusersearch.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.*
import com.android.githubusersearch.utils.CarouselSnapHelper
import com.android.githubusersearch.utils.ItemSnapHelper

class BaseRecyclerview : RecyclerView {

    private var layout = LayoutManager.HORIZONTAL

    private var span = 1

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    enum class LayoutManager {
        VERTICAL,
        HORIZONTAL,
        GRID,
        STAGGEREDGRIDVERTICAL,
        STAGGEREDGRIDHORIZONTAL
    }

    enum class Snap {
        CAROUSEL,
        ITEM
    }

    fun initRecyclerView(
        adapter: Adapter<*>, layout: LayoutManager = LayoutManager.HORIZONTAL,
        span: Int = 1, isScrollEnabled: Boolean = false, hasFixedSize: Boolean = false,
        reverseLayout: Boolean = false
    ) {
        this.adapter = adapter
        this.layout = layout
        this.span = span
        setHasFixedSize(hasFixedSize)
        isNestedScrollingEnabled = isScrollEnabled
        when (layout) {
            LayoutManager.GRID -> this.layoutManager = GridLayoutManager(context, span)
            LayoutManager.VERTICAL ->
                this.layoutManager = LinearLayoutManager(context, VERTICAL, reverseLayout)
            LayoutManager.HORIZONTAL ->
                this.layoutManager = LinearLayoutManager(context, HORIZONTAL, reverseLayout)
            LayoutManager.STAGGEREDGRIDVERTICAL ->
                this.layoutManager =
                    StaggeredGridLayoutManager(span, StaggeredGridLayoutManager.VERTICAL)
            LayoutManager.STAGGEREDGRIDHORIZONTAL ->
                this.layoutManager =
                    StaggeredGridLayoutManager(span, StaggeredGridLayoutManager.HORIZONTAL)
        }
    }

    fun setHorizontalSnapHelper(snap: Snap) {
        when (snap) {
            Snap.CAROUSEL -> CarouselSnapHelper(this)
            Snap.ITEM -> ItemSnapHelper(this)
        }
    }

    fun setDefaultItemAnimator() {
        itemAnimator = DefaultItemAnimator()
    }
}