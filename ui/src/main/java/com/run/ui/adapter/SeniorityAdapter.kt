package com.run.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.SeniorityModle
import com.run.ui.R

class SeniorityAdapter : BaseQuickAdapter<SeniorityModle.ListBean, BaseViewHolder>(R.layout.item_seniority_layout, null) {

    var requetType: String? = null
    var topCount: Int = 0

    override fun convert(helper: BaseViewHolder, bean: SeniorityModle.ListBean?) {
        if (bean == null || TextUtils.isEmpty(bean.mobile)) {
            helper.getView<View>(R.id.ll_root).visibility = View.GONE
            var tv_nodata: TextView = helper.getView(R.id.tv_nodata)
            tv_nodata.text = "暂无记录"
            tv_nodata.visibility = View.VISIBLE
            return
        }
        helper.getView<View>(R.id.ll_root).visibility = View.VISIBLE
        helper.getView<View>(R.id.tv_nodata).visibility = View.GONE
        UGlide.loadCircleImage(mContext, bean!!.head_avatar, helper.getView(R.id.iv_head_avatar) as ImageView)
        var num = ""
        var num_1 = ""
        if ("income" == requetType) {//累计收入
            num = bean!!.profit_total!!
            num_1 = "元"
        } else if ("gymnasiums" == requetType) {//累计收徒
            num = bean!!.num!!
            num_1 = "个"
        }

        var iv_seniority: ImageView = helper.getView(R.id.iv_seniority)
        iv_seniority.visibility = View.VISIBLE
        when (helper.adapterPosition) {
            0 + topCount -> iv_seniority.setImageResource(R.mipmap.ph1)
            1 + topCount -> iv_seniority.setImageResource(R.mipmap.ph2)
            2 + topCount -> iv_seniority.setImageResource(R.mipmap.ph3)
            else -> iv_seniority.visibility = View.GONE

        }
        helper.setText(R.id.tv_wechat_nick_name, if (TextUtils.isEmpty(bean.wechat_nick_name)) bean.mobile else bean.wechat_nick_name)
                .setText(R.id.tv_num, num)
                .setText(R.id.tv_num_danwei, num_1)

    }
}