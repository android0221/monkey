package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.RevenueDetailFragment
import kotlinx.android.synthetic.main.activity_revenue_detail.*


class RevenueDetailActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(act: Context) {
            val intent = Intent(act, RevenueDetailActivity::class.java)
            act.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_revenue_detail
    }

    override fun initViews() {
        iv_back.setOnClickListener { finish() }
    }
    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, RevenueDetailFragment())
        fragmentTransaction.commit()
    }


    override fun initPresenter(): Nothing? {
        return null
    }


}
