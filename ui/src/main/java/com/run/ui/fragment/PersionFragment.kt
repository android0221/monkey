package com.run.ui.fragment

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseFragment
import com.run.common.dialog.DialogHelper
import com.run.common.utils.UGlide
import com.run.common.utils.USharePreference
import com.run.common.utils.UStatusBar
import com.run.presenter.LoginHelper
import com.run.presenter.contract.PersionContract
import com.run.presenter.modle.UserJsonModle
import com.run.ui.R
import com.run.ui.activity.*
import kotlinx.android.synthetic.main.fragment_persion.*
import kotlinx.android.synthetic.main.layout_setting_more.*

open class PersionFragment : BaseFragment<PersionContract.PersionPresenter>(), PersionContract.PersionView {


    companion object {
        fun newInstance(): PersionFragment {
            return PersionFragment()
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_persion
    }

    private lateinit var rl_mm: View
    private lateinit var iv_usericon: ImageView
    private lateinit var tv_userid: TextView
    private lateinit var tv_profit_balance: TextView
    private lateinit var tv_total: TextView
    private lateinit var tv_today_money: TextView
    private lateinit var tv_sign: TextView
    private lateinit var invitegameLayout: View
    private lateinit var firstView: TextView
    private lateinit var progressAwardLayout: View
    private lateinit var transmitLayout: View
    private lateinit var truntableLayout: View
    private lateinit var treeLayout: View

    override fun initView(view: View) {
        rl_mm = view.findViewById(R.id.rl_mm)
        invitegameLayout = view.findViewById(R.id.invitegameLayout)
        iv_usericon = view.findViewById(R.id.iv_usericon)
        tv_userid = view.findViewById(R.id.tv_userid)
        tv_profit_balance = view.findViewById(R.id.tv_profit_balance)
        tv_total = view.findViewById(R.id.tv_total)
        tv_today_money = view.findViewById(R.id.tv_today_money)
        tv_sign = view.findViewById(R.id.tv_sign)
        firstView = view.findViewById(R.id.firstView)
        progressAwardLayout = view.findViewById(R.id.progressAwardLayout)
        transmitLayout = view.findViewById(R.id.transmitLayout)
        truntableLayout = view.findViewById(R.id.truntableLayout)
        treeLayout = view.findViewById(R.id.treeLayout)

        view.findViewById<View>(R.id.iv_set).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_kf).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_sign).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_wt).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_yi).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_symx).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_invite).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_withdraw).setOnClickListener(this)
        view.findViewById<View>(R.id.totalMoneyLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.ll_money).setOnClickListener(this)

        iv_usericon.setOnClickListener(this)
        invitegameLayout.setOnClickListener(this)
        progressAwardLayout.setOnClickListener(this)
        transmitLayout.setOnClickListener(this)
        truntableLayout.setOnClickListener(this)
        treeLayout.setOnClickListener(this)
    }

    //=============================================数据请求==================================================
    override fun initPresenter(): PersionContract.PersionPresenter? {
        return PersionContract.PersionPresenter(this)
    }

    override fun initData() {}

    /**
     * fragment页面可见时调用；
     */
    override fun visiable() {
        UStatusBar.setDarkMode(this.activity!!)
        requestData()
    }

    private var qqKey: String? = null
    private var activity_Type: String? = null //收徒大赛
    private fun requestData() {
        if (LoginHelper.instance.isLogin(this!!.activity!!)) {
            if (mPresenter == null) initData()
            mPresenter!!.getUserInfo()
            if (TextUtils.isEmpty(qqKey)) {
                mPresenter!!.getQQKey()
            }
            mPresenter!!.inform()

        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_set -> SettingActivity.newInstance(activity!!)
            R.id.ll_kf -> CustomerActivity.newInstance(activity!!) //AppIntentAction.joinQQGroup(qqKey, activity!!)
            R.id.ll_sign -> SignActivity.newInstance(activity!!, signtype)//doSign()
            R.id.ll_wt -> ProblemActivity.newInstance(activity!!, 1)
            R.id.ll_yi -> FeedBackActivity.newInstance(activity!!)
            R.id.ll_symx, R.id.totalMoneyLayout -> MyWalletActivity.newInstance(activity!!, nick, avatar, total)
            R.id.iv_usericon -> UserInfoActivity.newInstance(activity!!)
            R.id.ll_invite -> InviteActivity.newInstance(activity!!)
            R.id.ll_withdraw -> WithDrawActivity.newInstance(activity!!)
            R.id.invitegameLayout -> ContestActivity.newInstance(activity!!)
            R.id.progressAwardLayout -> ProgressActiveActivity.newInstance(activity!!, userid)
            R.id.transmitLayout -> TransmitActivity.newInstance(activity!!, userid)
            R.id.truntableLayout -> LuckTurnTableActivity.newInstance(activity!!)
            R.id.treeLayout -> RiceTreeActivity.newInstance(activity!!, avatar)
            R.id.ll_money -> ExplainActivity.newInstance(activity!!)
        }
    }

    private fun doSign() {
        if (signtype == 1) {
            showMsg("今日已签到")
            return
        }
        mPresenter!!.sign()
    }

    //=======================数据回调================================================================
    private var avatar: String? = ""
    private var nick: String? = ""
    private var total: String? = "0.0"
    private var signtype: Int = 0//是否签到
    private var userid: Int = 0
    @SuppressLint("SetTextI18n")
    override fun callBackUserData(modle: UserJsonModle) {
        signtype = modle.signtype
        if (signtype == 1) {
            tv_sign.text = "已签到"
        }
        tv_today_money.text = if (TextUtils.isEmpty(modle.count_income)) "0.0" else modle.count_income + ""//今日收益
        val bean = modle.data
        if (bean != null) {
            avatar = bean.head_avatar
            userid = bean.user_id
            nick = if (TextUtils.isEmpty(bean.wechat_nick_name)) "" + bean.user_id.toString() else bean.wechat_nick_name + "(ID: " + bean.user_id + ")"
            total = if (TextUtils.isEmpty(bean.profit_total)) "0.0" else bean.profit_total
            //加载头像
            UGlide.loadCircleImage(activity, avatar, iv_usericon)
            tv_userid.text = nick
            tv_profit_balance.text = if (TextUtils.isEmpty(bean.profit_balance)) "0.0" else bean.profit_balance + ""
            tv_total.text = total + ""
        }

        if (modle.first_user_id <= 0) {
            firstView.visibility = View.GONE
        } else {
            firstView.text = "师傅ID(" + modle.first_user_id + ")"
            firstView.visibility = View.VISIBLE
        }

        // TODO()  是否开启现金券
        //  showCardDialog()
        //收徒大赛
        activity_Type = modle.activity_type
        if (activity_Type == "0") {
            invitegameView.text = modle.activity_msg
            invitegameLayout.visibility = View.GONE
        } else {//开启收徒大赛
            invitegameLayout.visibility = View.VISIBLE
        }

        //本周进步奖活动
        progress_type = modle.progress_type!!
        if (progress_type == "1") { //没有开启
            progressAwardView.text = modle.progress_msg
            progressAwardLayout.visibility = View.VISIBLE
        } else { //有开启
            progressAwardLayout.visibility = View.GONE
        }

        //转发送现金红包
        if (modle.transmit_type == "1") {
            transmitMsgView.text = modle.transmit_msg
            transmitLayout.visibility = View.VISIBLE
        } else {
            transmitLayout.visibility = View.GONE
        }

        if (modle.pachira_macrocarpa_type == "1") {
            treeMsgView.text = modle.pachira_msg
            treeLayout.visibility = View.VISIBLE
        } else {
            treeLayout.visibility = View.GONE
        }

    }

    private lateinit var progress_type: String
    override fun callBackQQKey(key: String) {
        if (TextUtils.isEmpty(key)) return
        qqKey = key
    }

    override fun callBackSign(key: String) {
        showMsg(key)
        signtype = 1
    }
    override fun callBackMegagame(invite_top_img: String, type: String) {
        try {
            activity_Type = type
            if (activity_Type == "0") {
                invitegameLayout.visibility = View.GONE
            } else {//开启收徒大赛
                invitegameLayout.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //活动公告
    override fun callBacInform(key: String) {
        UGlide.loadGif(activity, "https://img.zcool.cn/community/012d2059accd89a8012028a9d9bf43.gif", lbImageView)
        hintView.isSelected = true
        hintView.text = key
    }
    //==========================================优惠券对话框========================================
    private var isShowCard = false

    private fun showCardDialog() {
        isShowCard = USharePreference[activity!!, "isShowCard", false] as Boolean
        if (!isShowCard) {
            val contentView = View.inflate(activity, R.layout.dialog_qrcode_layout, null)
            contentView.setOnClickListener {
                CardCenterActivity.newInstance(activity!!)
                DialogHelper.closeDialog()
            }
            DialogHelper.showDialog(activity!!, contentView)
            USharePreference.put(activity!!, "isShowCard", true)
        }
    }


}