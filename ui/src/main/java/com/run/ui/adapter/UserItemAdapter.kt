package com.run.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.login.UserItemBean
import com.run.ui.R

class UserItemAdapter : BaseQuickAdapter<UserItemBean, BaseViewHolder>(R.layout.item_user_layout, null) {
    override fun convert(helper: BaseViewHolder, item: UserItemBean) {
        helper.setText(R.id.tv_title, item.title).setText(R.id.tv_content, item.content)
        val tv_content = helper.getView<TextView>(R.id.tv_content)
        if (item.isColor) {
            tv_content.setTextColor(mContext.resources.getColor(R.color.colorPrimary))
        } else {
            tv_content.setTextColor(mContext.resources.getColor(R.color.color_f9))
        }
    }

}
