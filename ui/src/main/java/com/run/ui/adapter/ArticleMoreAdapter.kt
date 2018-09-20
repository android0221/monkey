package com.run.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.presenter.modle.ArticleBean
import com.run.ui.R
import com.run.ui.activity.ArticleDetailActivity
import com.run.ui.activity.PhotoDetailActivity
import com.run.ui.activity.VedioDetailActivity

/**
 * 更多推荐
 */
class ArticleMoreAdapter : BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.item_article_more_layout, null) {
    override fun convert(helper: BaseViewHolder, item: ArticleBean) {
        UGlide.loadRoundImage(mContext, item.cover_picture!!, helper.getView(R.id.iv_cover_picture) as ImageView, 5)
        val tv_title: TextView = helper.getView(R.id.tv_title)
        tv_title!!.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)
        helper.setText(R.id.tv_view_count, "阅读:" + (item.view_count + item.fake_view_max))
        var money = if (!item.money_view_user.equals("0.000")) {
            item.money_view_user
        } else {
            "0.2"
        }
        helper.setText(R.id.tv_share, "分享阅读$money" + "元/位")
        helper.getView<View>(R.id.rl_root).setOnClickListener {
            when (item.category_id) {
                34 -> PhotoDetailActivity.newInstance(mContext, item.article_id, money!!)
                47 -> VedioDetailActivity.newInstance(mContext, item.article_id, item.cover_picture!!, money!!)
                else -> ArticleDetailActivity.newInstance(mContext, item.article_id, money!!)

            }

        }

    }
}
