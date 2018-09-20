package com.run.ui.fragment

import com.run.common.base.BaseListFragment
import com.run.presenter.contract.CardContract
import com.run.presenter.modle.CardBean
import com.run.ui.adapter.CardCenterAdapter

class CardCenterFragment : BaseListFragment<CardContract.CardPresenter, CardBean>(), CardContract.CardView {

    companion object {
        fun newInstance(): CardCenterFragment {
            return CardCenterFragment()
        }
    }
    override fun initPresenter(): CardContract.CardPresenter? {
     return  CardContract.CardPresenter(this)
    }
    private lateinit var adapter: CardCenterAdapter
    override fun initData() {
        adapter = CardCenterAdapter()
        initAdapter(adapter)
        requestData()
    }
    override fun requestData() {
        mPresenter!!.voucher_list()
    }
    override fun showData(modle: List<CardBean>?) {
        setData(modle, false)
    }

}