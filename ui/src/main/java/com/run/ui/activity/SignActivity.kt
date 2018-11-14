package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.dialog.DialogCallBack
import com.run.common.dialog.DialogHelper
import com.run.common.utils.UGlide
import com.run.common.utils.UToastDialog
import com.run.common.utils.UType
import com.run.presenter.contract.SignContract
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.SignModle
import com.run.share.ShareHelper
import com.run.ui.ArticleHelper
import com.run.ui.R
import com.run.ui.adapter.SignAdapter
import com.run.ui.dialog.SignDialog
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.layout_sing_rule.*

import java.util.*

@Suppress("DEPRECATION")
/**
 * 今日签到
 */
class SignActivity : BaseActivity<SignContract.SignPresenter>(), SignContract.SignView {


    companion object {
        fun newInstance(context: Context, signtype: Int) {
            val intent = Intent(context, SignActivity::class.java)
            intent.putExtra("SIGNTYPE", signtype)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_sign
    }


    override fun initViews() {
        backView.setOnClickListener { finish() }
        singRecyclerView.layoutManager = GridLayoutManager(this, 7)
        signAdapter = SignAdapter()
        singRecyclerView.adapter = signAdapter
        singStatusView.setOnClickListener { doSign() }
    }


    private var mPosition: Int = 0
    /**
     * 执行签到任务
     */
    @SuppressLint("SetTextI18n")
    private fun doSign() {
        if (money.toDouble() < 2.00) {
            val mList: List<ArticleBean>? = ArticleHelper.instance.getList()
            if (mList == null || mList.isEmpty()) {
                SignDialog.newInstance(this, "您今天的转发收益暂时没有满" + mModle.term_money + "元哦，请继续转发赚钱")
                        .show(this@SignActivity, callBack = object : DialogCallBack {
                            override fun onNext() {
                                MainActivity.newInstance(this@SignActivity)
                            }

                            override fun cancle() {
                            }
                        })
            } else {
                var bean = mList[mPosition]
                val contentView = View.inflate(this@SignActivity, R.layout.dialog_sign_layout, null)
                val dialogTitleView: TextView = contentView.findViewById(R.id.dialogTitleView)
                val articleImageView: ImageView = contentView.findViewById(R.id.articleImageView)
                val articleTitleView: TextView = contentView.findViewById(R.id.articleTitleView)
                val nextView: TextView = contentView.findViewById(R.id.nextView)
                dialogTitleView.text = "您今天的转发收益暂时没有满" + mModle.term_money + "元哦，请继续转发赚钱"
                articleTitleView.text = bean.title
                UGlide.loadImage(this, bean.cover_picture!!, articleImageView)

                contentView.findViewById<View>(R.id.cancleView).setOnClickListener { DialogHelper.closeDialog() }

                contentView.findViewById<View>(R.id.nextView).setOnClickListener {

                    if (mPosition >= 10) {
                        MainActivity.newInstance(this@SignActivity)
                    } else {
                        if (mPosition == 9) {
                            nextView.text = "更多"
                        }
                        mPosition++
                        bean = mList[mPosition]
                        articleTitleView.text = bean.title
                        UGlide.loadImage(this, bean.cover_picture!!, articleImageView)
                    }
                }
                contentView.findViewById<View>(R.id.doShareView).setOnClickListener {
                    val money = if (bean.money_view_user!!.toDouble() <= 0.2) "0.2" else bean.money_view_user
                    ShareHelper.instance.doShare(this, bean.article_id, money)
                    DialogHelper.closeDialog()
                }
                DialogHelper.showDialog(this@SignActivity, contentView)
            }

        } else {
            mPresenter!!.sign()
        }

    }

    private var signList: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 6)
    private lateinit var signAdapter: SignAdapter
    override fun initData() {
        if (intent.getIntExtra("SIGNTYPE", 0) == 1) {
            singStatusView.isEnabled = false
            singStatusView.text = "已签到"

        }
        mPresenter!!.requestSignInfo()

    }

    override fun initPresenter(): SignContract.SignPresenter {
        return SignContract.SignPresenter(this)
    }

    private var sign_degree: Int = 0
    private var money: String = "0.0"
    private lateinit var mModle: SignModle
    @SuppressLint("SetTextI18n")
    override fun showSignData(modle: SignModle) {
        if (modle.signtype == 1) {
            singStatusView.isEnabled = false
            singStatusView.text = "已签到"
        }
        this.mModle = modle
        sign_degree = modle.num
        signAdapter.sign_degree = sign_degree
        signAdapter.increasing = modle.increasing
        signAdapter.start_money = modle.start_money
        signAdapter.setNewData(signList)

        money = if (TextUtils.isEmpty(modle.money)) "0.0" else modle.money!!
        val termMsg = "每天转发收益满 <font color='#ff0000'>${modle.term_money}元</font> 即可签到"
        val singMsg = "连续签到：<font color='#ff0000'>${sign_degree}天</font>"
        signDayView.text = Html.fromHtml(singMsg)
        val moneyMsg = "今天转发收益：<font color='#ff0000'>${money}元</font>"
        signMoneyView.text = Html.fromHtml(moneyMsg)
        termView.text = Html.fromHtml(termMsg)


        val day1 = modle.start_money
        val day2 = modle.start_money + modle.increasing
        val day3 = day2 + modle.increasing
        val day4 = day3 + modle.increasing
        val day5 = day4 + modle.increasing
        val day6 = day5 + modle.increasing
        val day7 = day6 + modle.increasing

        prizeDayView1.text = "奖励" + UType.doubleToString(day1) + "元"
        prizeDayView2.text = "奖励" + UType.doubleToString(day2) + "元"
        prizeDayView3.text = "奖励" + UType.doubleToString(day3) + "元"
        prizeDayView4.text = "奖励" + UType.doubleToString(day4) + "元"
        prizeDayView5.text = "奖励" + UType.doubleToString(day5) + "元"
        prizeDayView6.text = "奖励" + UType.doubleToString(day6) + "元"
        prizeDayView7.text = "奖励" + UType.doubleToString(day7) + "元"

        ruleView.text = Html.fromHtml("1.$termMsg")
    }

    /**
     * 签到完成回调
     */
    override fun callBackSign(key: String) {
        UToastDialog.getToastDialog().isCanToast = true
        if (sign_degree <= 7) {
            UToastDialog.getToastDialog().ToastShow(this, "签到奖励", mModle.start_money + sign_degree * mModle.increasing)
        } else {
            UToastDialog.getToastDialog().ToastShow(this, "签到奖励", mModle.start_money + 7 * mModle.increasing)
        }
        singStatusView.isEnabled = false
        singStatusView.text = "已签到"
        sign_degree++
        val singMsg = "连续签到：<font color='#ff0000'>${sign_degree}天</font>"
        signDayView.text = Html.fromHtml(singMsg)
        signAdapter.sign_degree = sign_degree
        signAdapter.notifyDataSetChanged()
    }

}
