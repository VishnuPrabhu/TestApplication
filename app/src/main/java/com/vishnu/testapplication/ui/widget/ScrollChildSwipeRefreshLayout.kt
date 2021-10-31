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

    fun setCanRefresh(enabled: Boolean) {
        canRefresh = enabled
        isEnabled = appbarExtended && canRefresh
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        appbarExtended = verticalOffset == 0
        isEnabled = appbarExtended && canRefresh
    }
}