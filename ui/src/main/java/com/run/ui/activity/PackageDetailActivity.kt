package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.PackageDetailFragment
import kotlinx.android.synthetic.main.activity_package_detail.*

/**
 * 红包明细
 */
class PackageDetailActivity : BaseActivity<Nothing>() {
    override fun initContentView(): Int {
        return R.layout.activity_package_detail
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, PackageDetailFragment.newInstance())
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, PackageDetailActivity::class.java))
        }
    }

}
