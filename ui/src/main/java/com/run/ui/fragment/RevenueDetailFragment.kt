package com.run.ui.fragment

import com.run.common.base.BaseListFragment
import com.run.presenter.contract.RevenueContract
import com.run.presenter.modle.IncomeRecordModle
import com.run.ui.adapter.IncomeRecordAdapter

class RevenueDetailFragment : BaseListFragment<RevenueContract.RevenuePresenter, IncomeRecordModle.DataBean>(), RevenueContract.RevenueView {

    companion object {
        fun newInstance(): RevenueDetailFragment {
            var fragment = RevenueDetailFragment()
            return fragment
        }
    }

    override fun initPresenter(): RevenueContract.RevenuePresenter? {
        return RevenueContract.RevenuePresenter(this)
    }

    private lateinit var adapter: IncomeRecordAdapter
    override fun initData() {
        adapter = IncomeRecordAdapter()
        initAdapter(adapter)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData("0", mPage)
    }

    override fun showData(modle: List<IncomeRecordModle.DataBean>?) {
        setData(modle)
    }

}