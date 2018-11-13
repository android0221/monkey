package com.run.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.run.common.R
import com.run.common.base.BaseListFragment
import com.run.common.utils.ULog
import com.run.presenter.contract.ArticleContract
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.ArticleModle
import com.run.presenter.modle.ShareListModle
import com.run.share.ShareHelper
import com.run.ui.ArticleHelper
import com.run.ui.adapter.ArticleAdapter
import java.util.*


@Suppress("UNREACHABLE_CODE")
class ArticleFragment : BaseListFragment<ArticleContract.ArticlePresenter, ArticleBean>(),
        ArticleContract.ArticleView {


    override fun initContentView(): Int {
        return R.layout.layout_article_fragment
    }

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

    private lateinit var openSelectView: TextView
    private lateinit var articleCancleView: Button
    private lateinit var articleSelectView: LinearLayout

    override fun initView(view: View) {
        super.initView(view)
        openSelectView = view.findViewById(R.id.openSelectView)
        articleCancleView = view.findViewById(R.id.articleCancleView)
        articleSelectView = view.findViewById(R.id.articleSelectView)

        openSelectView.setOnClickListener {
            articleSelectView.visibility = View.VISIBLE
            openSelectView.visibility = View.GONE
            adapter?.canSelect = true
            adapter?.notifyDataSetChanged()
        }

        articleCancleView.setOnClickListener {
            openSelectView.visibility = View.VISIBLE
            articleSelectView.visibility = View.GONE
            adapter?.canSelect = false
            adapter?.notifyDataSetChanged()
        }

        view.findViewById<View>(R.id.articleSendView).setOnClickListener {
            val buffer = StringBuffer()
            for (id in adapter?.selectList!!) {
                buffer.append(id.toString() + ",")
            }
            val shareIDList = buffer.toString().substring(0, buffer.toString().length - 2)
            ULog.d("xiaoruan", shareIDList)
            mPresenter?.doShareList(shareIDList)
        }
    }

    //初始化数据
    override fun initData() {
        adapter = ArticleAdapter()
        initAdapter(adapter!!)
        categordy_id = arguments!!.getInt("CATEGORY_ID")
        mType = arguments!!.getString("TYPE")

        if (categordy_id == 47) {
            openSelectView.visibility = View.GONE
        }
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
        if (modle.batch_share == 1 && categordy_id != 47 && articleSelectView.visibility == View.GONE) {
            openSelectView.visibility = View.VISIBLE
        } else {
            openSelectView.visibility = View.GONE
        }
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

    private val emojeList = arrayListOf("[玫瑰]", "[红包] ", "[礼物]", "[發]", "[耶]", "[机智]", "[胜利]", "[啤酒]", "[咖啡]", "[爱心]")
    /**
     * 获取分享的内容回调
     */
    override fun callBackShareData(list: List<ShareListModle.ShareDataBean>) {
        val shareMsg = StringBuffer()
        //微信红包要动态换
        val index1 = Random().nextInt(emojeList.size)
        val index2 = Random().nextInt(emojeList.size)
        for (data in list) {
            shareMsg.append(emojeList[index1] + data.title + "\r\n" + emojeList[index2] + data.url + "\r\n")
        }
        ShareHelper.instance.shareText(activity!!, shareMsg.toString())
        adapter?.selectList?.clear()
        openSelectView.visibility = View.VISIBLE
        articleSelectView.visibility = View.GONE
        adapter?.canSelect = false
        adapter?.notifyDataSetChanged()

    }
}
