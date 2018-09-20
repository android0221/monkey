package com.run.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.ApprenticeBean
import com.run.ui.R


class ApprenticeAdapter : BaseQuickAdapter<ApprenticeBean, BaseViewHolder>(R.layout.item_apprentice_layout, null) {

    override fun convert(helper: BaseViewHolder, bean: ApprenticeBean) {
        UGlide.loadCircleImage(mContext, bean.head_avatar, helper.getView(R.id.iv_head) as ImageView)
        helper.setText(R.id.tv_user_id, "" + bean.user_id)
                .setText(R.id.tv_mobile, "" + bean.mobile)
                .setText(R.id.tv_bonus_grant, bean.bonus_amount + "元")
                .setText(R.id.tv_bonus_amount, bean.bonus_grant + "元")
    }
}