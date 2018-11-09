package com.run.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import com.run.common.base.BaseListFragment
import com.run.common.utils.ULog
import com.run.presenter.contract.ArticleContract
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.ArticleModle
import com.run.ui.ArticleHelper
import com.run.ui.adapter.ArticleAdapter

class ArticleFragment : BaseListFragment<ArticleContract.ArticlePresenter, ArticleBean>(), ArticleContract.ArticleView {

    companion object {
        const val TAG: String = "ArticleFragment"
        fun newInstance(category_id: Int): ArticleFragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putInt("CATEGORY_ID", category_id)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(type: String): ArticleFragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putString("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initPresenter(): ArticleContract.ArticlePresenter? {
        return ArticleContract.ArticlePresenter(this)
    }

    //=================================数据请求======================================================
    private var categordy_id: Int = 0
    private var mType: String? = null
    /**
     * 初始化数据
     */
    private var adapter: ArticleAdapter? = null

    //初始化数据
    override fun initData() {
        adapter = ArticleAdapter()
        initAdapter(adapter!!)
        categordy_id = arguments!!.getInt("CATEGORY_ID")
        mType = arguments!!.getString("TYPE")
    }

    override fun visiable() {
        requestData()
    }

    override fun requestData() {
        ULog.d(TAG, "开始请求数据")
        if (TextUtils.isEmpty(mType)) {
            if (categordy_id < 0) return
            mPresenter!!.requestData(categordy_id, mPage)
        } else {
            mPresenter!!.requestData(mType!!, mPage)
        }
    }

    override fun callBackData(modle: ArticleModle) {
        ArticleHelper.instance.saveList(modle.data!!)
        ULog.d(TAG, "数据回调")
        adapter!!.setModleData(modle.money!!, modle.g_money!!, modle.g_title, modle.a_money!!,
                modle.a_title, modle.share_msg, modle.a_type, modle.g_type)
        val list = modle.data
        for (bean: ArticleBean in modle.data!!) {
            when (bean.category_id) {
                47 -> bean.itemType = ArticleBean.ARTICLE_VEDIO
                else -> bean.itemType = ArticleBean.ARTICLE_TEXT
            }
        }
        setData(list)
    }
}
