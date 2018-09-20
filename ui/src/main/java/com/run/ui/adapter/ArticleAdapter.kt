package com.run.ui.adapter


import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.emoj.EmotionUtils.EMOTION_CLASSIC_TYPE
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.presenter.modle.ArticleBean
import com.run.share.ShareHelper
import com.run.ui.R
import com.run.ui.activity.ArticleDetailActivity
import com.run.ui.activity.PhotoDetailActivity
import com.run.ui.activity.VedioDetailActivity


class ArticleAdapter : BaseMultiItemQuickAdapter<ArticleBean, BaseViewHolder>(null) {

    init {
        addItemType(ArticleBean.ARTICLE_TEXT, R.layout.item_article_text_layout)
        addItemType(ArticleBean.ARTICLE_VEDIO, R.layout.item_article_vedio_layout)
        addItemType(ArticleBean.ARTICLE_IMAGE, R.layout.item_article_image_layout)
    }

    private var money: String? = null
    private var g_money: String? = null
    private var g_title: String? = null
    private var a_title: String? = null
    private var a_money: String? = null
    private var shareMsg: String? = null
    private var a_type: Int = 0
    private var g_type: Int = 0

    fun setModleData(money: String, g_money: String, g_title: String?, a_money: String, a_title: String?, shareMsg: String?, a_type: Int, g_type: Int) {
        this.money = money
        this.g_money = g_money
        this.g_title = g_title
        this.a_title = a_title
        this.a_money = a_money
        this.shareMsg = shareMsg
        this.a_type = a_type
        this.g_type = g_type
    }

    private var b_money: String? = null
    private var t_money: String? = null
    private var b_title: String? = null

    override fun convert(helper: BaseViewHolder, item: ArticleBean) {//设置头像
        if (item.category_id === 44) {
            t_money = g_money
            b_title = g_title
        } else {
            t_money = a_money
            b_title = a_title
        }
        val tv_title_end: TextView = helper.getView(R.id.tv_title_end)
        if (TextUtils.isEmpty(item.title_end)) {
            tv_title_end.visibility = GONE
        } else {
            tv_title_end.visibility = VISIBLE
        }

        val tv_title: TextView = helper.getView(R.id.tv_title)
        tv_title.text = SpanStringUtils.getEmotionContent(EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)

        b_money = if (!item.money_view_user.equals("0.000")) {
            item.money_view_user
        } else {
            money
        }
        helper.setText(R.id.tv_money, "￥" + b_money + "元")
                .setText(R.id.tv_view_count, "" + (item.view_count + item.fake_view_max))
        when (helper!!.itemViewType) {
            ArticleBean.ARTICLE_TEXT -> {
                UGlide.loadRoundImage(mContext, item.cover_picture!!, helper.getView(R.id.iv_cover_picture) as ImageView, 5)
                helper.setText(R.id.tv_a_title, "$b_title:")
                        .setText(R.id.tv_a_money, t_money)
                val ll_type = helper.getView<View>(R.id.ll_type)

                if (item.category_id === 44) {
                    if (g_type == 0) {
                        ll_type.visibility = GONE
                    } else {
                        ll_type.visibility = VISIBLE
                    }
                } else {
                    if (a_type == 0) {
                        ll_type!!.visibility = GONE
                    } else {
                        ll_type.visibility = VISIBLE
                    }
                }
                helper.getView<View>(R.id.rl_root).setOnClickListener {
                    ArticleDetailActivity.newInstance(mContext, item!!.article_id, b_money!!)
                }
            }
            ArticleBean.ARTICLE_IMAGE -> {
                UGlide.loadRoundImage(mContext, item.cover_picture!!, helper.getView(R.id.iv_cover_picture) as ImageView, 5)
                helper.getView<View>(R.id.rl_root).setOnClickListener {
                    PhotoDetailActivity.newInstance(mContext, item!!.article_id, b_money!!)
                }
            }
            ArticleBean.ARTICLE_VEDIO -> {
                UGlide.loadRoundImage(mContext, item.cover_picture!!, helper.getView(R.id.iv_cover_picture) as ImageView, 5)
                helper.getView<View>(R.id.rl_root).setOnClickListener {
                     VedioDetailActivity.newInstance(mContext, item!!.article_id, item.cover_picture!!, b_money!!)
                }
            }
        }
        var shareView: View = helper.getView(R.id.tv_share)
        //分享
        shareView.setOnClickListener {
            ShareHelper.instance.doShare(mContext, item!!.article_id, this!!.b_money!!)
        }
    }

}
