package com.run.ui.adapter


import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.base.BaseObserver
import com.run.common.utils.UGlide
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.CardBean
import com.run.ui.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CardCenterAdapter : BaseQuickAdapter<CardBean, BaseViewHolder>(R.layout.item_card_center_layout, null) {

    override fun convert(helper: BaseViewHolder, item: CardBean) {
        UGlide.loadRoundImage(mContext, item.ico!!, helper.getView(R.id.iv_card) as ImageView, 2)
        helper.setText(R.id.tv_card_title, item.title)
                .setText(R.id.tv_card_time, "截至领取：" + item.expire_time)
                .setText(R.id.tv_card_content, item.content)
        val button: TextView = helper.getView(R.id.btn_card)
        button.isEnabled = true
        button.text = "立即领取"
        button.setOnClickListener {
            //领取卡券
            ApiManager.get_voucher(item.v_id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onError(errorType: Int, msg: String?) {

                        }
                        override fun onSuccess(o: BaseModle) {
                            button.text = "已领取"
                            button.isEnabled = false
                        }
                    })
        }
    }
}