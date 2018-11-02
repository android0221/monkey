package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.presenter.contract.WalletContract
import com.run.presenter.modle.IncomeRecordModle
import com.run.ui.R
import com.run.ui.fragment.ApprenticeFragment
import com.run.ui.fragment.RevenueDetailFragment
import kotlinx.android.synthetic.main.activity_my_wallet.*

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity<WalletContract.WalletPresenter>(), WalletContract.WalletView {


    companion object {

        fun newInstance(context: Context, nick: String?, header: String?, money: String?) {
            var intent = Intent(context, MyWalletActivity::class.java)
            intent.putExtra("nick", nick)
            intent.putExtra("header", header)
            intent.putExtra("money", money)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_my_wallet
    }

    override fun initViews() {
        UStatusBar.setDarkMode(this)
        backView.setOnClickListener { finish() }
        withDrawView.setOnClickListener {
            SeniorityActivity.newInstance(this, intent.getStringExtra("nick"), intent.getStringExtra("header"), intent.getStringExtra("money"))
        }

        totalLayout.setOnClickListener { viewPager.currentItem = 0 }
        shareLayout.setOnClickListener { viewPager.currentItem = 1 }
        recruitLayout.setOnClickListener { viewPager.currentItem = 2 }
    }

    override fun initData() {
        val adapter = WalletPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        UTabLayout.setTabLayoutLine(tabLayout)
        viewPager.currentItem = 0

        mPresenter!!.requestData("0", 1)
    }

    override fun initPresenter(): WalletContract.WalletPresenter {
        return WalletContract.WalletPresenter(this)
    }

    override fun showData(bean: IncomeRecordModle.MoneyBean) {
        recruitView.text = if (TextUtils.isEmpty(bean.profit_recruit)) "0.0" else bean.profit_recruit
        shareView.text = if (TextUtils.isEmpty(bean.profit_share)) "0.0" else bean.profit_share
        totalView.text = if (TextUtils.isEmpty(bean.profit_total)) "0.0" else bean.profit_total
    }

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
                else -> RevenueDetailFragment.newInstance("0")
            }
            return fragment
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }
}
