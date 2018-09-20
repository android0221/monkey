package com.run.ui.fragment

import android.view.View
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.WithDrawBillContract
import com.run.presenter.modle.IncomeBean
import com.run.ui.R
import com.run.ui.adapter.DrawRecordAdapter

class WithDrawBillFragment : BaseListFragment<WithDrawBillContract.WithDrawBillPresenter, IncomeBean>(), WithDrawBillContract.WithDrawBillView {

    companion object {
        fun newInstance(): WithDrawBillFragment {
            return WithDrawBillFragment()
        }
    }

    override fun initPresenter(): WithDrawBillContract.WithDrawBillPresenter? {
        return WithDrawBillContract.WithDrawBillPresenter(this)
    }

    private var headView: View? = null
    private lateinit var adapter: DrawRecordAdapter
    override fun initData() {
        adapter = DrawRecordAdapter()
        initAdapter(adapter)
        if (headView == null) {
            headView = View.inflate(activity, R.layout.head_drawrecord_heard_layout, null)
            if (adapter != null) {
                adapter.addHeaderView(headView)
            }
        }

        mPresenter = WithDrawBillContract.WithDrawBillPresenter(this)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.bill("0", mPage)
    }

    override fun showData(modle: List<IncomeBean>) {
        setData(modle)
    }

}