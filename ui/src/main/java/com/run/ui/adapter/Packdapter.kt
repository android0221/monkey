package com.run.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.common.utils.UTime
import com.run.presenter.modle.CardBean
import com.run.presenter.modle.PackageDetalModle
import com.run.ui.R
import com.run.ui.activity.RiceTreeActivity
import com.run.ui.activity.WithDrawActivity


class Packdapter : BaseQuickAdapter<PackageDetalModle.PackBean, BaseViewHolder>(R.layout.item_pack_layout, null) {


    override fun convert(helper: BaseViewHolder, item: PackageDetalModle.PackBean) {
        UGlide.loadRoundImage(mContext, item.head_avatar, helper.getView(R.id.headavatarView), 5)

        helper.setText(R.id.createTimeView, "创建时间:" + item.create_time)
                .setText(R.id.expiretimeView, "到期时间:" + item.expire_time)
                .setText(R.id.moneyView, "+" + item.money)
        val statusView: TextView = helper.getView(R.id.statusView)
        val moneyView: TextView = helper.getView(R.id.moneyView)
        when (item.packet_type) {
            0 -> {
                val expireTiem = UTime.timeStrToNumber((item.expire_time))
                if (expireTiem <= System.currentTimeMillis()) {
                    statusView.text = "已过期"
                    statusView.isEnabled = false
                } else {
                    statusView.text = "未领取"
                    statusView.isEnabled = true
                }

                moneyView.visibility = View.GONE
            }
            1 -> {
                statusView.text = "已领取"
                statusView.isEnabled = false
                moneyView.visibility = View.VISIBLE
            }
            2 -> {
                statusView.text = "已发放"
                statusView.isEnabled = false
                moneyView.visibility = View.VISIBLE
            }
        }

    }
}