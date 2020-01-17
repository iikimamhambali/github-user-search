package com.android.githubusersearch.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class CarouselSnapHelper(private val recyclerView: RecyclerView) : LinearSnapHelper() {

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    init {
        attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val pos = recyclerView.getChildAdapterPosition(targetView)
        // If first or last view, the default implementation works.
        if (pos == 0 || pos == recyclerView.layoutManager!!.itemCount - 1) {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView)
        }
        // Force centering in the view without its decorations.
        // targetCenter is the location of the center of the view we want to center.
        val targetCenter = targetView.left + targetView.width / 2
        // Distance is the number of pixels to move the target so that its center
        // lines up with the center of the RecyclerView (mRecycler.getWidth() / 2)
        val distance = targetCenter - recyclerView.width / 2
        return intArrayOf(distance, 0)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return if (layoutManager is LinearLayoutManager) {
            getHorizontalHelper(layoutManager)?.let { orientationHelper ->
                if (layoutManager.canScrollHorizontally()) {
                    getStartView(layoutManager, orientationHelper)
                } else {
                    getStartView(layoutManager, orientationHelper)
                }
            }
        } else super.findSnapView(layoutManager)

    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {

        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()

            val isLastItem = layoutManager
                .findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null
            }

            val child = layoutManager.findViewByPosition(firstChild)

            return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 && helper.getDecoratedEnd(
                    child
                ) > 0
            ) {
                child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }
}