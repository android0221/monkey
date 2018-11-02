package com.run.ui.fragment

import android.os.Bundle
import com.run.common.R
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.RevenueContract
import com.run.presenter.modle.IncomeRecordModle
import com.run.ui.activity.MyWalletActivity
import com.run.ui.adapter.IncomeRecordAdapter

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RevenueDetailFragment : BaseListFragment<RevenueContract.RevenuePresenter, IncomeRecordModle.DataBean>(), RevenueContract.RevenueView {


    companion object {
        fun newInstance(type: String): RevenueDetailFragment {
            val fragment = RevenueDetailFragment()
            var bundle = Bundle()
            bundle.putString("TYPE", type)
            fragment.arguments = bundle
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
        mPresenter!!.requestData(arguments!!.getString("TYPE"), mPage)
    }

    override fun showData(modle: List<IncomeRecordModle.DataBean>?) {

        setData(modle)
    }

}