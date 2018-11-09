package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.presenter.contract.RiceTreeContract
import com.run.presenter.modle.PacketModle
import com.run.presenter.modle.WaterModel
import com.run.ui.R
import com.run.ui.R.id.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rice_tree.*
import java.util.concurrent.TimeUnit

/**
 * 发财树
 */
class RiceTreeActivity : BaseActivity<RiceTreeContract.RiceTreePresenter>(), RiceTreeContract.RiceTreeView {


    companion object {
        fun newInstance(context: Context, headerUrl: String?) {
            val intent = Intent(context, RiceTreeActivity::class.java)
            intent.putExtra("URl", headerUrl)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_rice_tree
    }

    override fun initViews() {
        UStatusBar.setStatusBarTranslucent(this)
        UStatusBar.setDarkMode(this@RiceTreeActivity)
        UGlide.loadCircleImage(this, intent.getStringExtra("URl"), headerView)
        waterFlakeView.setOnWaterItemListener {
            if (awardStatus) {
                awardStatus = false
                mPresenter!!.getPacket(it.r_id)
            }
        }
        backView.setOnClickListener { finish() }
        activeView.setOnClickListener {
            ContentDetailActivity.newInstance(this, "发财树活动说明", 13)
        }
        //领取奖励
        singStatusView.setOnClickListener {
            mPresenter!!.getAward()

        }
        //红包明细
        packdetailView.setOnClickListener {
            PackageDetailActivity.newInstance(this)
        }
    }

    private var awardStatus = true
    private var mModelList: MutableList<WaterModel>? = null
    override fun initData() {
        mPresenter!!.requestData()
    }


    override fun initPresenter(): RiceTreeContract.RiceTreePresenter {
        return RiceTreeContract.RiceTreePresenter(this)
    }

    private var money: String? = ""
    override fun callBackData(modle: PacketModle) {
        awardStatus = true
        money = modle.z_money.toString()
        totalmoneyView.text = "总金额：" + modle.z_money + "元"
        when (modle.activity_type) {
            0 -> {
                singStatusView.visibility = View.VISIBLE
            }
            1 -> {
                singStatusView.visibility = View.GONE
                mModelList = modle.pack_list
                waterFlakeView.setModelList(mModelList, headerView)
            }
        }
        Observable.timer(60, TimeUnit.SECONDS).subscribe {
            if (mPresenter != null) {
                initData()
            }
        }
    }



    override fun callBackResult(msg: String) {
        showMsg("恭喜你获得" + msg + "元红包")
        Observable.timer(3, TimeUnit.SECONDS).subscribe {
            if (mPresenter != null) {
                initData()
            }
        }
    }

    override fun callBackAward(msg: String) {
        PackResultActivity.newInstance(this, msg, money)
    }


}
