package com.run.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.ProblemContract
import com.run.presenter.modle.login.UserItemBean
import com.run.ui.activity.ContentDetailActivity
import com.run.ui.adapter.UserItemAdapter

class ProblemFragment : BaseListFragment<ProblemContract.ProblemPresenter, UserItemBean>(), ProblemContract.ProblemView, BaseQuickAdapter.OnItemClickListener {


    companion object {
        fun newInstance(type: Int): ProblemFragment {
            val fragment = ProblemFragment()
            var bundle = Bundle()
            bundle.putInt("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initPresenter(): ProblemContract.ProblemPresenter? {
        return ProblemContract.ProblemPresenter(this)
    }

    private var adapter: UserItemAdapter? = null
    private var mType: Int? = 0
    override fun initData() {
        mType = arguments!!.getInt("TYPE")
        adapter = UserItemAdapter()
        initAdapter(adapter!!)
        adapter!!.onItemClickListener = this

        requestData()
    }

    override fun requestData() {
        mPresenter!!.dynamics(mType!!)
    }

    override fun showData(list: List<UserItemBean>) {
        setData(list, false)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val bean: UserItemBean = adapter!!.getItem(position) as UserItemBean
        ContentDetailActivity.newInstance(this!!.activity!!, bean.title!!, bean.id)
    }

}