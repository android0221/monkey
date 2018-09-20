package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.CardCenterFragment
import kotlinx.android.synthetic.main.activity_revenue_detail.*

class CardCenterActivity : BaseActivity<Nothing>() {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, CardCenterActivity::class.java))
        }

    }
    override fun initContentView(): Int {
        return R.layout.activity_card_center
    }

    override fun initViews() {
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, CardCenterFragment())
        fragmentTransaction.commit()
    }




    override fun initPresenter(): Nothing? {
        return null
    }


}
