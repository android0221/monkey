package com.run.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.ui.R

class MoneyAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_withdraw_layout, null) {


    var money: Int = 0

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: Int?) {
//        val button: Button = helper.getView(R.id.btn_money)
//        button.isSelected = item == money
//        button.text = item!!.toString() + "元"
        val moneyView: TextView = helper.getView(R.id.moneyView)
        moneyView.text = item!!.toString() + "元"
        helper.getView<View>(R.id.moneyLayout).isSelected = item == money
        helper.getView<ImageView>(R.id.clickView).visibility = if ((item == money)) View.VISIBLE else View.GONE
    }
}