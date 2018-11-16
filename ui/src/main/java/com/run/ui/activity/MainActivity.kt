package com.run.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import com.run.common.base.BaseActivity
import com.run.common.helper.SharedPreferenceHelper
import com.run.common.utils.UActivityManager
import com.run.common.utils.UStatusBar
import com.run.common.view.NoScrollViewPager
import com.run.presenter.LoginHelper
import com.run.presenter.contract.MainContract
import com.run.presenter.contract.MainContract.MainPresenter
import com.run.ui.R
import com.run.ui.fragment.HomeFragment
import com.run.ui.fragment.PersionFragment
import com.run.ui.fragment.SeniorityFragment
import com.run.ui.fragment.VedioFragment
import com.run.ui.service.MyService
import com.run.version.UpdataVersionHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


@Suppress("JAVA_CLASS_ON_COMPANION")
class MainActivity : BaseActivity<MainPresenter>(), MainContract.MainView {

    companion object {
        val TAG: String = MainActivity.javaClass.name
        fun newInstance(context: Activity) {
            context.startActivity(Intent(context, MainActivity::class.java))
            context.finish()
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setStatus(0)
    }

    //===========================================布局=================================================
    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    private lateinit var indexView: TextView
    private lateinit var communityView: TextView
    private lateinit var vedioView: TextView
    private lateinit var persionView: TextView
    private lateinit var viewpager: NoScrollViewPager

    override fun initViews() {
        //设置状态栏
        UStatusBar.setTransparentForImageViewInFragment(this@MainActivity, null)
        viewpager = findViewById(R.id.viewpager)
        indexView = findViewById(R.id.indexView)
        communityView = findViewById(R.id.communityView)
        persionView = findViewById(R.id.persionView)
        vedioView = findViewById(R.id.vedioView)
        indexView.setOnClickListener { setStatus(0) }
        communityView.setOnClickListener { setStatus(1) }
        vedioView.setOnClickListener { setStatus(2) }
        persionView.setOnClickListener {
            if (LoginHelper.instance.isLogin(this)) {
                setStatus(3)
            }
        }
    }


    private fun setStatus(position: Int) {
        initStatus()
        when (position) {
            0 -> indexView.isSelected = true
            1 -> communityView.isSelected = true
            2 -> vedioView.isSelected = true
            3 -> persionView.isSelected = true
        }
        viewpager.currentItem = position
    }


    /**
     * 初始化状态
     */
    private fun initStatus() {
        indexView.isSelected = false
        communityView.isSelected = false
        vedioView.isSelected = false
        persionView.isSelected = false
    }

    //==========================================初始化数据===========================================
    /**
     * 首页的fragment集合
     */
    lateinit var fragmentList: List<Fragment>

    @SuppressLint("CheckResult")
    override fun initData() {
        //启动通知服务
//        MyService.openMyService(this)
        fragmentList = arrayListOf(HomeFragment.newInstance(),
                SeniorityFragment.newInstance(),
                VedioFragment.newInstance(),
                PersionFragment.newInstance())
        viewpager.adapter = MainFragmentAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit = 4
        setStatus(0)
        //统计APP的激活数据
        mPresenter!!.statisticsActive(this@MainActivity)
        if (SharedPreferenceHelper.firstOpenApp(this) || !LoginHelper.instance.isLogin) {
            UpdataVersionHelper.getInstance().checkUpadata(this, 1)
        } else {
            SharedPreferenceHelper.getOpenApp(this)
            Observable.timer(2, TimeUnit.SECONDS).subscribe {
                ExplainActivity.newInstance(this, 1)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            UpdataVersionHelper.getInstance().checkUpadata(this, 1)
        }
    }

    override fun initPresenter(): MainPresenter? {
        return MainPresenter(this)
    }
    //==============================================fragment集合=====================================
    inner class MainFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }

    //======================================= 退出应用 ==============================================
    private var mIsExit: Boolean = false
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                UActivityManager.exit()
            } else {
                Toast.makeText(this, "再按一次退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show()
                mIsExit = true
                Handler().postDelayed({ mIsExit = false }, 2000)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
