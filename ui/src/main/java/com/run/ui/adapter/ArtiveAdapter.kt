package com.run.ui.adapter

import android.annotation.SuppressLint
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.ProgressArtiveModle
import com.run.ui.R

class ArtiveAdapter : BaseQuickAdapter<ProgressArtiveModle.WeekListBean, BaseViewHolder>(R.layout.item_progress_artive_layout, null) {


    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: ProgressArtiveModle.WeekListBean) {
        helper.setText(R.id.timeView, item.create_time).setText(R.id.moneyView, item.money)

    }
}