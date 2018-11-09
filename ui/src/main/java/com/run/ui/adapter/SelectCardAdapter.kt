package com.run.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.CardBean
import com.run.ui.R


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SelectCardAdapter : BaseQuickAdapter<CardBean, BaseViewHolder>(R.layout.item_card_select_layout, null) {

    var cardid: Int = 0

    override fun convert(helper: BaseViewHolder, item: CardBean) {

        UGlide.loadRoundImage(mContext, item.ico!!, helper.getView(R.id.iv_card) as ImageView, 2)
        helper.setText(R.id.tv_card_title, item.title)
                .setText(R.id.tv_card_time, "到期时间：" + item.expire_time)
                .setText(R.id.tv_card_content, item.content)

        val iv_status: ImageView = helper.getView(R.id.iv_status)
        iv_status.isSelected = item.v_id === cardid

    }
}