package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.adapter.SignAdapter
import kotlinx.android.synthetic.main.activity_sign.*
import java.util.*

/**
 * 今日签到
 */
class SignActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, SignActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_sign
    }

    override fun initViews() {

    }

    private var signList: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 6)
    private lateinit var signAdapter: SignAdapter
    private var sign_degree: Int = 0
    override fun initData() {
        singRecyclerView.layoutManager = GridLayoutManager(this, 7)
        signAdapter = SignAdapter()
        singRecyclerView.adapter = signAdapter
        signAdapter.sign_degree = sign_degree
        signAdapter.setNewData(signList)
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
