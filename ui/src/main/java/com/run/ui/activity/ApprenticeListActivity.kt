package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.ApprenticeFragment

class ApprenticeListActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ApprenticeListActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_apprentice_list
    }

    override fun initViews() {
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
    }

    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, ApprenticeFragment.newInstance())
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
