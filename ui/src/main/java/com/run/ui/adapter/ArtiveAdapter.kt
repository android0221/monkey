package com.run.ui.adapter

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.ArtiveBean
import com.run.presenter.modle.ProgressArtiveModle
import com.run.ui.R

class ArtiveAdapter : BaseQuickAdapter<ArtiveBean, BaseViewHolder>(R.layout.item_progress_artive_layout, null) {


    var modifyColor: Boolean = false

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun convert(helper: BaseViewHolder, item: ArtiveBean) {
        helper.setText(R.id.timeView, item.create_time).setText(R.id.moneyView, item.money)
        if (modifyColor) {
            val moneyView: TextView = helper.getView(R.id.moneyView)
            moneyView.isSelected = item.money!!.toDouble() >= 20.0
        }


    }
}