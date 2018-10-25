package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.ricetree.WaterFlake
import com.run.ui.ricetree.WaterModel
import kotlinx.android.synthetic.main.activity_rice_tree.*
import java.util.ArrayList

/**
 * 发财树
 */
class RiceTreeActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, RiceTreeActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_rice_tree
    }

    override fun initViews() {
        waterFlakeView.setOnWaterItemListener { showMsg("点击了") }
    }

    private var mModelList: MutableList<WaterModel>? = null
    override fun initData() {
        mModelList = ArrayList()
        for (i in 0..5) {
            mModelList!!.add(WaterModel("sds"))
        }
        waterFlakeView.setModelList(mModelList, waterFlakeView)
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
