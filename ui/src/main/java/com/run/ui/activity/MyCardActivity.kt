package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.ui.R
import com.run.ui.fragment.CardFragment

class MyCardActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Activity) {
            activity.startActivity(Intent(activity, MyCardActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_my_card
    }

    private lateinit var mTab: TabLayout
    private var mViewPager: ViewPager? = null
    override fun initViews() {
        UStatusBar.setDarkMode(this)
        mTab = findViewById(R.id.tab)
        mViewPager = findViewById(R.id.viewpager)
        findViewById<View>(R.id.iv_return).setOnClickListener { finish() }
        findViewById<View>(R.id.tv_cardcenter).setOnClickListener { CardCenterActivity.newInstance(this@MyCardActivity) }
    }

    override fun initData() {
        val adapter = CardPagerAdapter(supportFragmentManager)
        mViewPager!!.adapter = adapter
        mTab.setupWithViewPager(mViewPager)
        UTabLayout.setTabLayoutLine(mTab)
    }

    override fun initPresenter(): Nothing? {
        return null
    }
    private inner class CardPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_card_Title)
        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }
        override fun getItem(position: Int): Fragment {
            return CardFragment.newInstance(position)
        }
        override fun getCount(): Int {
            return mTilte.size
        }
    }


}
