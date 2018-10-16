package com.run.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.IncomeBean
import com.run.ui.R


class DrawRecordAdapter : BaseQuickAdapter<IncomeBean, BaseViewHolder>(R.layout.item_drawrecord_layout, null) {

    override fun convert(helper: BaseViewHolder, bean: IncomeBean?) {
        if (bean == null) return
        helper.setText(R.id.tv_time, bean.update_time)
                .setText(R.id.tv_fail_reason, "失败原因" + bean.fail_reason)
                .setText(R.id.tv_money, bean.money!! + "")

        var status = ""
        val ll_fail: View = helper.getView(R.id.ll_fail)
        ll_fail.visibility = View.GONE

        var updataTime: String = "未到账"
        when (bean.withdraw_status) {
            1 -> status = "等待审核"
            2 -> status = "等待转账"
            3 -> {
                status = "提现成功"
                updataTime = bean.update_time!!
            }
            4 -> {
                status = "提现失败"
                ll_fail.visibility = View.VISIBLE
            }
            else -> status = ""
        }
        helper.setText(R.id.tv_withdraw_status, status)
                .setText(R.id.tv_update_time, updataTime)
        var wdithType = ""
        when (bean.withdraw_type) {
            1 -> wdithType = "微信支付"
            3 -> wdithType = "微信好友手动转账"
        }
        helper.setText(R.id.tv_withdraw_type, wdithType)
    }

}
