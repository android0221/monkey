package com.run.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseFragment
import com.run.common.utils.UAnim
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.ui.R
import com.run.ui.activity.InviteActivity
import com.run.ui.activity.SearchActivity

class SeniorityFragment : BaseFragment<Nothing>() {

    companion object {
        fun newInstance(): SeniorityFragment {
            return SeniorityFragment()
        }
    }
    override fun initContentView(): Int {
        return R.layout.fragment_seniority
    }

    private lateinit var redImage: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun initView(view: View) {
        redImage = view.findViewById(R.id.redpackage_imageview)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        view.findViewById<View>(R.id.iv_seach).setOnClickListener { SearchActivity.newInstance(context!!) }
        redImage.setOnClickListener { InviteActivity.newInstance(activity!!) }
    }
    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        val adapter = SeniorityPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        UTabLayout.setTabLayoutLine(tabLayout)
    }
    override fun visiable() {
        super.visiable()
        /**
         * 设置状态栏字体颜色
         */
        UStatusBar.setLightMode(this!!.activity!!)
        /*
        * 开始抖动动画
        */
        UAnim.startShakeByPropertyAnim(redImage, 0.6f, 1.0f, 10f, 1000, 3)
    }

    //================================adapter===========================================================
    private inner class SeniorityPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_seniorty_Title)
        private var fragment: Fragment? = null

        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }

        override fun getItem(position: Int): Fragment? {
            fragment = when (position) {
                0 -> ArticleFragment.newInstance("day")
                1 -> ArticleFragment.newInstance("week")
                2 -> ArticleFragment.newInstance("month")
                else -> ArticleFragment.newInstance("day")
            }
            return fragment
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }

}
