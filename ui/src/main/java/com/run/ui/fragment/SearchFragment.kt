package com.run.ui.fragment

import com.run.common.base.BaseListFragment
import com.run.common.base.BaseObserver
import com.run.common.utils.ULog
import com.run.presenter.contract.ArticleContract
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.ArticleModle
import com.run.presenter.modle.ShareListModle
import com.run.ui.adapter.ArticleAdapter

class SearchFragment :BaseListFragment<ArticleContract.ArticlePresenter, ArticleBean>(),ArticleContract.ArticleView{
    override fun callBackShareData(list: List<ShareListModle.ShareDataBean>) {

    }


    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun initPresenter(): ArticleContract.ArticlePresenter? {
        return ArticleContract.ArticlePresenter(this)
    }
    private var adapter: ArticleAdapter? = null
    override fun initData() {
        adapter = ArticleAdapter()
        initAdapter(adapter!!)
        showErr(BaseObserver.RETURN_ERROR, "暂未查询到相关数据！")
    }

    private var mKey: String? = ""
    fun searchData(key: String?) {
        this.mKey = key
        requestData()
    }
    /**
     * 请求数据
     */
    override fun requestData() { mPresenter!!.searchData(mKey, mPage) }
    override fun callBackData(modle: ArticleModle) {
        ULog.d(ArticleFragment.TAG, "数据回调")
        adapter!!.setModleData(modle.money!!, modle.g_money!!, modle.g_title, modle.a_money!!, modle.a_title, modle.share_msg, modle.a_type, modle.g_type)
        var list = modle.data
        for (bean: ArticleBean in modle.data!!) {
            when (bean!!.category_id) {
                34 -> bean!!.itemType = ArticleBean.ARTICLE_IMAGE
                47 -> bean!!.itemType = ArticleBean.ARTICLE_VEDIO
                else -> bean!!.itemType = ArticleBean.ARTICLE_TEXT
            }
        }
        setData(list)
    }

}