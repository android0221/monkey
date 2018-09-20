package com.run.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.CardContract
import com.run.presenter.modle.CardBean
import com.run.ui.adapter.SelectCardAdapter

class SelectCardFrament : BaseListFragment<CardContract.CardPresenter, CardBean>(), CardContract.CardView, BaseQuickAdapter.OnItemClickListener {


    companion object {
        fun newInstance(cardid: Int): SelectCardFrament {
            var fragment = SelectCardFrament()
            var bundle = Bundle()
            bundle.putInt("cardid", cardid)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initPresenter(): CardContract.CardPresenter? {
                   return CardContract.CardPresenter(this)
    }
    private lateinit var adapter: SelectCardAdapter
    private var cardid: Int? = 0
    override fun initData() {
        adapter = SelectCardAdapter()
        initAdapter(adapter)

        cardid = arguments!!.getInt("cardid")
        adapter.cardid = cardid!!

        mPresenter = CardContract.CardPresenter(this)

        adapter.setOnItemClickListener(this)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData("now")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val item:CardBean = adapter!!.getItem(position) as CardBean
        val intent = Intent()
        intent.putExtra("CARDID", item!!.v_id)
        intent.putExtra("TITLE", item.title)
        activity!!.setResult(Activity.RESULT_OK, intent)
        activity!!.finish()

    }

    override fun showData(modle: List<CardBean>?) {
        setData(modle, false)
    }

}