package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.ULog
import com.run.ui.R
import com.run.ui.fragment.ProblemFragment

class ProblemActivity : BaseActivity<Nothing>() {


    companion object {
        fun newInstance(activity: Context, type: Int) {
            val intent = Intent(activity, ProblemActivity::class.java)
            intent.putExtra("TYPE", type)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_problem
    }

    private lateinit var tv_title: TextView
    override fun initViews() {
        tv_title = findViewById(R.id.tv_title)
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
    }

    private var mType: Int = 0
    private var fragment: ProblemFragment? = null
    override fun initData() {
        mType = intent.getIntExtra("TYPE", 1)
        when (mType) {
            1 -> tv_title!!.text = "新手指导"
            2 -> tv_title!!.text = "如何赚钱"
        }
        // 1. 获取到FragmentManager对象
        val fragmentManager = supportFragmentManager
        // 2. 开启一个事务
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        // 3. 向容器内加入Fragment
        fragment = ProblemFragment.newInstance(mType)
        fragmentTransaction.add(R.id.framelayout, fragment!!)
        // 4. 提交事务
        fragmentTransaction.commit()
        ULog.d("加载视频fragment成功")
    }


    override fun initPresenter(): Nothing? {
        return null
    }


}
