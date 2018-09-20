package com.run.ui.adapter

import android.annotation.SuppressLint
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.ui.R

class MoneyAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_number_button, null) {


    var money: Int = 0

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: Int?) {
        val button: Button = helper.getView(R.id.btn_money)
        button.isSelected = item == money
        button.text = item!!.toString() + "元"
    }
}