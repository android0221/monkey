package com.run.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.IncomeModle
import com.run.ui.R

class IncarnateTypeAdapter : BaseQuickAdapter<IncomeModle.ListBean, BaseViewHolder>(R.layout.item_incarnatetype_layout, null) {

    override fun convert(helper: BaseViewHolder, item: IncomeModle.ListBean) {
        helper.setText(R.id.tv_incarnate_item, item.title)
    }

}