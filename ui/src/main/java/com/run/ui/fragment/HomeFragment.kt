package com.run.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseFragment
import com.run.common.utils.UAnim
import com.run.common.utils.UStatusBar
import com.run.common.utils.UTabLayout
import com.run.persioninfomation.presenter.HomeContract
import com.run.presenter.LoginHelper
import com.run.presenter.modle.ArticleTypeModle
import com.run.ui.R
import com.run.ui.activity.ExplainActivity
import com.run.ui.activity.SearchActivity


class HomeFragment : BaseFragment<HomeContract.HomePresenter>(), HomeContract.HomeView {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_home
    }

    private lateinit var redImage: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun initView(view: View) {
        redImage = view.findViewById(R.id.redpackage_imageview)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        view.findViewById<View>(R.id.searchView).setOnClickListener { SearchActivity.newInstance(activity!!) }
        redImage.setOnClickListener {
            if (LoginHelper.instance.isLogin(activity!!)) {
                ExplainActivity.newInstance(activity!!)
            }
        }
    }


    /**
     * 页面可见的时候
     */
    override fun visiable() {
        super.visiable()
        //设置状态栏字体颜色
        UStatusBar.setLightMode(this.activity!!)
        UAnim.startShakeByPropertyAnim(redImage, 0.6f, 1.0f, 10f, 1000, 3)
        //请求数据
        if (mList != null && mList!!.size > 3) return
        if (mPresenter != null) mPresenter!!.requestData()
    }

    override fun initData() {}
    //======================================================初始化数据=====================================================
    override fun initPresenter(): HomeContract.HomePresenter? {
        return HomeContract.HomePresenter(this)
    }
    override fun showData(lists: List<ArticleTypeModle.DataBean>?) {
        this.mList = lists
        if (mList == null || mList!!.isEmpty()) {
            mList = arrayListOf(ArticleTypeModle.DataBean(0, "推荐"),
                    ArticleTypeModle.DataBean(37, "热点"))
        }
        val adapter = HomePagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        UTabLayout.setTabLayoutLine(tabLayout)
    }
    /**
     * 显示错误信息
     */
    override fun showErr(errorType: Int, msg: String) {
        showData(null)
    }
    //========================================adapter ==============================================
    private var mList: List<ArticleTypeModle.DataBean>? = null
    private inner class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getPageTitle(position: Int): CharSequence? {
            return mList!![position].name
        }
        override fun getItem(position: Int): Fragment {
            return ArticleFragment.newInstance(mList!![position].category_id)
        }
        override fun getCount(): Int {
            return mList!!.size
        }
    }
}