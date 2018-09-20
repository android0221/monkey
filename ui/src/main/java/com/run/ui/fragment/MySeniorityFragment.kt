package com.run.ui.fragment

import android.os.Bundle
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.SeniorityContract
import com.run.presenter.modle.SeniorityModle
import com.run.ui.adapter.SeniorityAdapter

class MySeniorityFragment : BaseListFragment<SeniorityContract.SeniorityPresenter, SeniorityModle.ListBean>(), SeniorityContract.SeniorityView {


    companion object {
        fun newInstance(type: Int): MySeniorityFragment {
            val fragment = MySeniorityFragment()
            val bundle = Bundle()
            bundle.putInt("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun initPresenter(): SeniorityContract.SeniorityPresenter? {
        return SeniorityContract.SeniorityPresenter(this)
    }

    private var type: Int = 0
    private var requetType: String? = null
    private lateinit var adapter: SeniorityAdapter

    override fun initData() {
        type = arguments!!.getInt("TYPE")
        when (type) {
            0 -> requetType = "income"
            1 -> requetType = "gymnasiums"
        }
        adapter = SeniorityAdapter()
        initAdapter(adapter!!)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData(requetType!!)
    }

    override fun showData(modle: SeniorityModle) {
        adapter.requetType = requetType
        setData(modle.list, false)
    }

}