package com.run.ui.fragment

import com.run.common.base.BaseListFragment
import com.run.presenter.modle.PackDetailContract
import com.run.presenter.modle.PackageDetalModle
import com.run.ui.adapter.Packdapter

class PackageDetailFragment : BaseListFragment<PackDetailContract.PackDetailPresenter, PackageDetalModle.PackBean>(), PackDetailContract.PackDetailView {
    companion object {
        fun newInstance(): PackageDetailFragment {
            return PackageDetailFragment()
        }
    }

    override fun initPresenter(): PackDetailContract.PackDetailPresenter? {
        return PackDetailContract.PackDetailPresenter(this)
    }

    private lateinit var adapter: Packdapter
    override fun initData() {
        adapter = Packdapter()
        initAdapter(adapter)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData(mPage)
    }

    override fun callBackData(list: List<PackageDetalModle.PackBean>) {
        setData(list)
    }

}