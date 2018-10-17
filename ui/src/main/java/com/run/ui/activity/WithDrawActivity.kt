package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.base.BaseActivity
import com.run.common.dialog.DialogHelper
import com.run.common.utils.ULog
import com.run.common.view.MyBottomSheetDialog
import com.run.presenter.contract.WithDrawContract
import com.run.presenter.modle.IncomeModle
import com.run.ui.R
import com.run.ui.adapter.IncarnateTypeAdapter
import com.run.ui.adapter.MoneyAdapter
import java.text.DecimalFormat

class WithDrawActivity : BaseActivity<WithDrawContract.WithDrawPresenter>(), WithDrawContract.WithDrawView {


    companion object {
        fun newInstance(act: Context) {
            newInstance(act, 0, "")
        }

        fun newInstance(act: Context, my_voucherid: Int, title: String) {
            val intent = Intent(act, WithDrawActivity::class.java)
            intent.putExtra("MY_VOUCHERID", my_voucherid)
            intent.putExtra("TITLE", title)
            act.startActivity(intent)
        }

    }

    override fun initContentView(): Int {
        return R.layout.activity_with_draw
    }


    private var tv_incarnate_type: TextView? = null
    private var tv_incarnate_money: TextView? = null
    private var tv_profit_balance: TextView? = null
    private var profit_had: TextView? = null
    private var tv_profit_total: TextView? = null
    private var tv_card: TextView? = null
    private var btn_income: Button? = null
    private var wb_msg: WebView? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: MoneyAdapter? = null
    private var money: Int = 0
    private var profitBalance: Double = 0.toDouble()

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        tv_incarnate_type = findViewById(R.id.tv_incarnate_type)
        tv_incarnate_money = findViewById(R.id.tv_incarnate_money)
        tv_profit_balance = findViewById(R.id.tv_profit_balance)
        profit_had = findViewById(R.id.profit_had)
        tv_profit_total = findViewById(R.id.tv_profit_total)
        wb_msg = findViewById(R.id.wb_msg)
        tv_card = findViewById(R.id.tv_card)
        btn_income = findViewById(R.id.btn_income)
        btn_income!!.setOnClickListener(this)
        findViewById<View>(R.id.ll_incarnate_type).setOnClickListener(this)
        findViewById<View>(R.id.ll_incarnate_money).setOnClickListener(this)
        findViewById<View>(R.id.tv_income_list).setOnClickListener(this)
        findViewById<View>(R.id.ll_card).setOnClickListener(this)
        findViewById<View>(R.id.iv_back).setOnClickListener(this)

        //更多工具
        recyclerView = findViewById(R.id.recyclerview)
        mAdapter = MoneyAdapter()
        recyclerView!!.layoutManager = GridLayoutManager(this, 3)
        recyclerView!!.adapter = mAdapter

        mAdapter!!.setOnItemClickListener { _, _, position ->
            money = mAdapter!!.getItem(position)!!
            mAdapter!!.money = money
            ULog.e("money 2=$money")
            tv_incarnate_money!!.text = money.toString() + ""
            mAdapter!!.notifyDataSetChanged()
            if (profitBalance < money) { //余额不足
                btn_income!!.text = "余额不足"
                btn_income!!.isEnabled = false
            } else {
                btn_income!!.text = "立即提现"
                btn_income!!.isEnabled = true
            }
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.btn_income -> {
                btn_income!!.isEnabled = false
                mPresenter!!.money(money, type, my_voucherid)
            }
            R.id.ll_incarnate_type -> showType()
            R.id.ll_incarnate_money -> {
                if (recyclerView!!.visibility == View.VISIBLE) {
                    recyclerView!!.visibility = View.GONE
                } else {
                    recyclerView!!.visibility = View.VISIBLE
                }
            }
            R.id.ll_card -> SelectCardActivity.newInstance(this, my_voucherid, 1)
            R.id.tv_income_list -> WithDrawBillActivity.newInstance(this@WithDrawActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        if (requestCode == 1 && resultCode == RESULT_OK) {
            my_voucherid = data.getIntExtra("CARDID", 0)
            cardTitle = data.getStringExtra("TITLE")
            tv_card!!.text = cardTitle
        }
    }

    private var mLists: List<IncomeModle.ListBean>? = null
    /**
     * 显示提现方式
     */
    private fun showType() {
        val dialog = MyBottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_incarnate_type_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        val recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = IncarnateTypeAdapter()
        recyclerView.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            dialog.cancel()
            val bean = mLists!!.get(position)
            type = bean.value
            tv_incarnate_type!!.text = bean.title
        }
        if (mLists != null) {
            adapter.setNewData(mLists)
        }
        dialog.show()
    }

    //=========================================数据操作==============================================
    override fun initPresenter(): WithDrawContract.WithDrawPresenter? {
        return WithDrawContract.WithDrawPresenter(this)
    }

    private var type: Int = 0//提现type
    private var cardTitle: String? = null
    private var my_voucherid = 0
    override fun initData() {
        my_voucherid = intent.getIntExtra("MY_VOUCHERID", 0)
        cardTitle = intent.getStringExtra("TITLE")
        tv_card!!.text = cardTitle
        mPresenter!!.money_view()
    }

    private var mMoneyList: List<Int>? = null
    private var profit_money = 0.00

    @SuppressLint("SetTextI18n")
    override fun showData(bean: IncomeModle) {
        mLists = bean.list
        if (mLists != null && mLists!!.isNotEmpty()) {
            type = mLists!![0].value
            tv_incarnate_type!!.text = mLists!![0].title
        }
        mMoneyList = bean.typelist
        if (mMoneyList != null && mMoneyList!!.isNotEmpty()) {
            money = mMoneyList!![0]
            ULog.e("money =$money")
            tv_incarnate_money!!.text = money.toString() + ""
        }
        if (mAdapter != null) {
            mAdapter!!.money = money
            mAdapter!!.setNewData(mMoneyList)
        }
        wb_msg!!.loadDataWithBaseURL(null, bean.msg, "text/html", "utf-8",
                null)
        profitBalance = bean.profit_balance
        tv_profit_balance!!.text = profitBalance.toString() + ""

        if (profitBalance < money) { //余额不足
            btn_income!!.text = "余额不足"
            btn_income!!.isEnabled = false
        } else {
            btn_income!!.text = "立即提现"
            btn_income!!.isEnabled = true
        }

        profit_money = bean.all_money
        profit_had!!.text = doubleToString(profit_money) + ""
        tv_profit_total!!.text = bean.profit_total.toString()


        if (bean.wechat_subscribe == 0) { //绑定微信
            mPresenter!!.money(money, type, my_voucherid)
        }
    }

    /**
     *绑定微信
     */
    override fun gotoBindWC(msg: String, url: String, content: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg)
        builder.setPositiveButton("确定") { dialog, _ ->
            BindWCActivity.newInstance(this@WithDrawActivity, url, content)
            dialog.cancel()
        }
        builder.setNegativeButton("取消") { dialog, _ -> dialog.cancel() }
        builder.setCancelable(false)
        builder.show()
    }

    override fun moneyFinish(msg: String) {
        val contentView = View.inflate(this, R.layout.dialog_withdraw_success_layout,
                null)
        val msgView: TextView = contentView.findViewById(R.id.msgView)
        msgView.text = msg
        contentView.findViewById<View>(R.id.tv_cancle).setOnClickListener {
            DialogHelper.closeDialog()
            startActivity(Intent(this@WithDrawActivity, MainActivity::class.java))
            finish()
        }
        DialogHelper.showDialog(this, contentView)
    }

    override fun showMoneyError(msg: String) {
        if (TextUtils.isEmpty(msg)) return
        val contentView = View.inflate(this, R.layout.dialog_withdraw_fail_layout,
                null)
        contentView.findViewById<View>(R.id.tv_cancle).setOnClickListener {
            DialogHelper.closeDialog()
        }
        val tv_msg: TextView = contentView.findViewById(R.id.tv_msg)
        tv_msg.text = msg
        DialogHelper.showDialog(this@WithDrawActivity, contentView)
    }

    //===============================工具类==========================================================
    private fun doubleToString(num: Double): String {
        //使用0.00不足位补0，#.##仅保留有效位
        return DecimalFormat("0.00").format(num)
    }

}
