package com.run.monkey

import android.app.Activity
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.run.common.base.BaseActivity
import com.run.common.helper.SharedPreferenceHelper
import com.run.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_guide.*

/**
 * 新手指导页面
 */
class GuideActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Activity) {
            context.startActivity(Intent(context, GuideActivity::class.java))
            context.finish()
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_guide
    }

    override fun initViews() {
        viewpager.adapter = GuidePagerAdapter()
    }

    override fun initData() {}
    override fun initPresenter(): Nothing? {
        return null
    }
    //=========================================Adapter适配器=====================================
    private val guideImageList = arrayOf(R.mipmap.gda, R.mipmap.gdf, R.mipmap.gdc, R.mipmap.gdg)
    inner class GuidePagerAdapter : PagerAdapter() {
        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }
        override fun getCount(): Int {
            return guideImageList.size
        }
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var imageView = ImageView(this@GuideActivity)
            imageView.setImageResource(guideImageList[position])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            container.addView(imageView)
            if (position == guideImageList.size - 1) {//是否为最后一个引导页面
                imageView.setOnClickListener {
                    //去到主页面
                    SharedPreferenceHelper.openGuide(this@GuideActivity)
                    MainActivity.newInstance(this@GuideActivity)
                }
            }
            return imageView
        }
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
