package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.ui.R
import com.run.ui.fragment.MySeniorityFragment

class SeniorityActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context, nick: String?, header: String?, money: String?) {
            var intent = Intent(context, SeniorityActivity::class.java)
            intent.putExtra("nick", nick)
            intent.putExtra("header", header)
            intent.putExtra("money", money)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_seniority
    }


    private lateinit var mTab: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var tv_profit_total: TextView
    private lateinit var tv_userid: TextView
    private lateinit var iv_usericon: ImageView
    override fun initViews() {
        UStatusBar.setDarkMode(this@SeniorityActivity)
        mTab = findViewById(R.id.tab)
        mViewPager = findViewById(R.id.viewpager)
        tv_profit_total = findViewById(R.id.tv_profit_total)
        tv_userid = findViewById(R.id.tv_userid)
        iv_usericon = findViewById(R.id.iv_usericon)
        findViewById<View>(R.id.iv_return).setOnClickListener { finish() }
    }

    override fun initData() {
        UGlide.loadCircleImage(this, intent.getStringExtra("header"), iv_usericon)
        tv_profit_total.text = intent.getStringExtra("money")
        tv_userid.text = intent.getStringExtra("nick")

        val adapter = SeniorityPagerAdapter(supportFragmentManager)
        mViewPager!!.adapter = adapter
        mTab.setupWithViewPager(mViewPager)
        UTabLayout.setTabLayoutLine(mTab)
    }

    override fun initPresenter(): Nothing? {
        return null
    }


    private inner class SeniorityPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_seniority_Title)
        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }

        override fun getItem(position: Int): Fragment {
            return MySeniorityFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }

}
