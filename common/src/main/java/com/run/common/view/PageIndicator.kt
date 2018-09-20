package com.run.common.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import com.run.common.R

import java.util.ArrayList

/**
 * 指示器
 */
class PageIndicator(context: Context, linearLayout: LinearLayout, private val mPageCount: Int//页数
) : ViewPager.OnPageChangeListener {
    private val mImgList: MutableList<ImageView>//保存img总个数
    private val img_select: Int
    private val img_unSelect: Int

    init {

        mImgList = ArrayList()
        img_select = R.drawable.shape_point_press
        img_unSelect = R.drawable.shape_point
        val imgSize = 20

        for (i in 0 until mPageCount) {
            val imageView = ImageView(context)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            //为小圆点左右添加间距
            params.leftMargin = 12
            params.rightMargin = 12
            //给小圆点一个默认大小
            params.height = imgSize
            params.width = imgSize
            if (i == 0) {
                imageView.setBackgroundResource(img_select)
            } else {
                imageView.setBackgroundResource(img_unSelect)
            }
            //为LinearLayout添加ImageView
            linearLayout.addView(imageView, params)
            mImgList.add(imageView)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        for (i in 0 until mPageCount) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if (position % mPageCount == i) {
                mImgList[i].setBackgroundResource(img_select)
            } else {
                mImgList[i].setBackgroundResource(img_unSelect)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
}
