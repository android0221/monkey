package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.run.common.base.BaseActivity
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.ui.R
import com.run.ui.fragment.ApprenticeFragment
import com.run.ui.fragment.RevenueDetailFragment
import kotlinx.android.synthetic.main.activity_my_wallet.*

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity<Nothing>() {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, MyWalletActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_my_wallet
    }


    override fun initViews() {
        UStatusBar.setDarkMode(this)
        backView.setOnClickListener { finish() }
        withDrawView.setOnClickListener { WithDrawActivity.newInstance(this) }
    }

    override fun initData() {
        val adapter = WalletPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        UTabLayout.setTabLayoutLine(tabLayout)
        viewPager.currentItem = 0

    }


    override fun initPresenter(): Nothing? { return null }

    //================================adapter=======================================================
    private inner class WalletPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_wallet_Title)
        private var fragment: Fragment? = null
        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }

        override fun getItem(position: Int): Fragment? {
            fragment = when (position) {
                0 -> RevenueDetailFragment.newInstance("0")
                1 -> RevenueDetailFragment.newInstance("3")
                2 -> RevenueDetailFragment.newInstance("1")
                3 -> ApprenticeFragment.newInstance()
                else -> RevenueDetailFragment.newInstance("0")
            }
            return fragment
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }
}
