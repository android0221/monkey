package com.run.ui.fragment

import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseFragment
import com.run.common.utils.UAnim
import com.run.common.utils.UStatusBar
import com.run.ui.R
import com.run.ui.activity.InviteActivity
import com.run.ui.activity.SearchActivity

class VedioFragment : BaseFragment<Nothing>() {

    companion object {
        fun newInstance(): VedioFragment {
            return VedioFragment()
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_vedio
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private lateinit var redpackageView: ImageView
    override fun initView(view: View) {
        redpackageView = view.findViewById(R.id.redpackage_imageview)
        view.findViewById<View>(R.id.serachView).setOnClickListener { SearchActivity.newInstance(context!!) }
        redpackageView.setOnClickListener { InviteActivity.newInstance(activity!!) }
    }


    private lateinit var fragment: ArticleFragment
    override fun initData() {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragment = ArticleFragment.newInstance(47)
        fragmentTransaction.add(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }

    override fun visiable() {
        super.visiable()
        UStatusBar.setLightMode(this!!.activity!!)
        UAnim.startShakeByPropertyAnim(redpackageView, 0.6f, 1.0f, 10f, 1000, 3)
        if (fragment != null) {
            fragment.requestData()
        }
    }
}
