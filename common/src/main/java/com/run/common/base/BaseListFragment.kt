package com.run.common.base

import android.graphics.drawable.AnimationDrawable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.R

abstract class BaseListFragment<T : BaseMvpPresenter, N> : BaseFragment<T>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    override fun initContentView(): Int {
        return R.layout.layout_recyclerview
    }

    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mSwipeLayout: SwipeRefreshLayout

    override fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerview)
        mSwipeLayout = view.findViewById(R.id.swiperefreshlayout)
        initSwipeRefresh()
    }

    /**
     * 初始化刷新
     */
    protected fun initSwipeRefresh() {
        mSwipeLayout.setColorSchemeResources(R.color.colorSwipe)//设置进度动画的颜色。
        mSwipeLayout.setOnRefreshListener(this)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * 初始化adapter
     */
    private var mAdapter: BaseQuickAdapter<N, *>? = null
    protected fun initAdapter(adapter: BaseQuickAdapter<N, *>) {
        mAdapter = adapter
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.adapter = mAdapter
        setLoadingLayout(0)
    }

    //================================================相关布局==============================================================================
    private lateinit var loadingview: ImageView
    private var loadingViewDrawable: AnimationDrawable? = null
    /**
     * 设置加载的Layout
     */
    private fun setLoadingLayout(rid: Int) {
        if (rid <= 0) {
            val layout = LayoutInflater.from(activity).inflate(R.layout.layout_loading_view, mRecyclerView!!.parent as ViewGroup, false)
            loadingview = layout.findViewById(R.id.loadingview)
            loadingViewDrawable = loadingview.background as AnimationDrawable
            mAdapter!!.emptyView = layout
            loadingViewDrawable?.start()
        } else {
            mAdapter!!.setEmptyView(rid, mRecyclerView!!.parent as ViewGroup)
        }
    }

    /**
     * 设置加载内容为空的布局
     */
    private fun setEmptyLayout(rid: Int) {
        if (rid <= 0) {
            mAdapter!!.setEmptyView(R.layout.layout_empty_view, mRecyclerView!!.parent as ViewGroup)
        } else {
            mAdapter!!.setEmptyView(rid, mRecyclerView!!.parent as ViewGroup)
        }
    }
    //===============================设置Adapter参数============================================

    //请求的页数
    protected var mPage = 1

    protected fun setData(list: List<N>?) { if (list != null) setData(list, true) }

    protected fun setData(list: List<N>?, isLoad: Boolean) {
        finishLoading()
        if (mAdapter == null || mRecyclerView == null) return
        if (mPage > 1) {//表示加载更多
            if (list == null || list.isEmpty()) {
                mAdapter!!.loadMoreEnd()
                return
            }
            mAdapter!!.addData(list)
            return
        }
        if (list == null || list.isEmpty()) {
            setEmptyLayout(-1)
            return
        }
        if (mAdapter != null) {
            mAdapter!!.setNewData(list)
        }
        if (isLoad && list.size >= 10) {
            mAdapter!!.setOnLoadMoreListener(this, mRecyclerView)
        }

    }
    /**
     * 结束加载数据
     */
    private fun finishLoading() {
        loadingViewDrawable?.stop()
        if (mSwipeLayout != null) {
            mSwipeLayout!!.isRefreshing = false
        }
        if (mAdapter != null) {
            mAdapter!!.loadMoreComplete()
        }
    }

    /**
     * 刷新
     */
    override fun onRefresh() {
        mPage = 1
        requestData()
    }
    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        mPage++
        requestData()
    }
    /**
     * 数据请求
     */
    public open fun requestData() {}
    /**
     * 错误异常处理
     */

    override fun showErr(errorType: Int, msg: String) {
        finishLoading()
        val layout = LayoutInflater.from(activity).inflate(R.layout.layout_error_view, mRecyclerView!!.parent as ViewGroup, false)
        val msgView: TextView = layout.findViewById(R.id.tv_msg)
        msgView.text = msg
        if (mAdapter != null) {
            mAdapter!!.emptyView = layout
        }
    }
}