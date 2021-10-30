package com.vishnu.testapplication.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener


class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs), OnOffsetChangedListener {

    private var canRefresh = true
    private var appbarExtended = true

    var scrollUpChild: View? = null

    override fun canChildScrollUp() =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()

//    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
//        if (isEnabled) {
//            // Dispatch up to the nested parent
//            dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dxConsumed, null)
//        } else {
//            dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null)
//        }
//    }
//
//    fun setCanRefresh(enabled: Boolean) {
//        mCanRefresh = enabled
//        isEnabled = mAppbarExtended && mCanRefresh
//    }
//
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        appbarExtended = verticalOffset == 0
        isEnabled = appbarExtended && canRefresh
    }
}