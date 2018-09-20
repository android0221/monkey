package com.run.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.ContestModle
import com.run.ui.R

/**
 * 收徒大赛Item
 */
class ContestAdapter : BaseQuickAdapter<ContestModle.ListBean, BaseViewHolder>(R.layout.item_contest_layout, null) {

    override fun convert(helper: BaseViewHolder, item: ContestModle.ListBean) {
        if (TextUtils.isEmpty(item.money)) {
            helper.getView<View>(R.id.ll_root).visibility = View.GONE
            helper.getView<View>(R.id.ll_nodata).visibility = View.VISIBLE
            return
        }
        UGlide.loadCircleImage(mContext, item.head_avatar, helper.getView(R.id.iv_head_avatar) as ImageView)
        helper.setText(R.id.tv_user_id, item.user_id.toString())
                .setText(R.id.tv_rank, helper.layoutPosition.toString() + "")
                .setText(R.id.tv_num, item.num.toString())
                .setText(R.id.tv_money, item.money)
    }
}
