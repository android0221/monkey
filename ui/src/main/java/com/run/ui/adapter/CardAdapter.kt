package com.run.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.CardBean
import com.run.ui.R
import com.run.ui.activity.WithDrawActivity


class CardAdapter : BaseQuickAdapter<CardBean, BaseViewHolder>(R.layout.item_card_center_layout, null) {

    private var mType: String? = null

    fun getmType(): String? {
        return mType
    }

    fun setmType(mType: String) {
        this.mType = mType
    }

    override fun convert(helper: BaseViewHolder, item: CardBean) {
        UGlide.loadRoundImage(mContext, item.ico!!, helper.getView(R.id.iv_card) as ImageView, 2)
        helper.setText(R.id.tv_card_title, item.title)
                .setText(R.id.tv_card_time, "到期时间：" + item.expire_time)
                .setText(R.id.tv_card_content, item.content)
        val button: TextView = helper.getView(R.id.btn_card)
        when (mType) {
            "now" -> {
                button.text = "立即使用"
                button.isEnabled = true
            }
            "already" -> {
                button.text = "已使用"
                button.isEnabled = false

            }
            "formerly" -> {
                button.text = "已失效"
                button.isEnabled = false
            }
        }

        button.setOnClickListener { WithDrawActivity.newInstance(mContext, item.v_id, item.title!!) }
    }
}