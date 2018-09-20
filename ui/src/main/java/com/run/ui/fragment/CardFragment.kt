package com.run.ui.fragment

import android.os.Bundle
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.CardContract
import com.run.presenter.modle.CardBean
import com.run.ui.adapter.CardAdapter

class CardFragment : BaseListFragment<CardContract.CardPresenter, CardBean>(), CardContract.CardView {
    companion object {
        fun newInstance(type: Int): CardFragment {
            val fragment = CardFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initPresenter(): CardContract.CardPresenter? {
        return CardContract.CardPresenter(this)
    }

    private var mType: String? = null
    private lateinit var adapter: CardAdapter
    override fun initData() {
        adapter = CardAdapter()
        initAdapter(adapter)
        val typeid = arguments!!.getInt("type")
        when (typeid) {
            0 -> mType = "now"
            1 -> mType = "already"
            2 -> mType = "formerly"
        }
        adapter.setmType(mType!!)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData(mType!!)
    }

    override fun showData(modle: List<CardBean>?) {
        setData(modle)
    }

}