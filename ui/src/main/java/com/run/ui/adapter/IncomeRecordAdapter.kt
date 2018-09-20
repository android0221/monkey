package com.run.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.IncomeRecordModle
import com.run.ui.R

class IncomeRecordAdapter : BaseQuickAdapter<IncomeRecordModle.DataBean, BaseViewHolder>(R.layout.item_income_record_layout, null) {
    override fun convert(helper: BaseViewHolder, item: IncomeRecordModle.DataBean) {
        helper.setText(R.id.tv_money, "+" + item.money)
                .setText(R.id.tv_create_time, item.create_time)
        val intro: TextView = helper.getView(R.id.tv_itro)
        intro.text = item.intro
    }


}