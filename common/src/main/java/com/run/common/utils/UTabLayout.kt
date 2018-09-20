package com.run.common.utils

import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import android.widget.TextView

object UTabLayout {

    /**
     * 设置tabLayout的下划线
     */
    fun setTabLayoutLine(tabLayout: TabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post {
            try {
                //拿到tabLayout的mTabStrip属性
                val mTabStrip = tabLayout.getChildAt(0) as LinearLayout
                val dp10 = USize.dip2px(tabLayout.context, 10f)
                for (i in 0 until mTabStrip.childCount) {
                    val tabView = mTabStrip.getChildAt(i)
                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
                    mTextViewField.isAccessible = true
                    val mTextView = mTextViewField.get(tabView) as TextView
                    tabView.setPadding(0, 0, 0, 0)
                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    var width = 0
                    width = mTextView.width
                    if (width == 0) {
                        mTextView.measure(0, 0)
                        width = mTextView.measuredWidth
                    }
                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    val params = tabView.layoutParams as LinearLayout.LayoutParams
                    params.width = width
                    params.leftMargin = dp10
                    params.rightMargin = dp10
                    tabView.layoutParams = params

                    tabView.invalidate()
                }

            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

    }

}