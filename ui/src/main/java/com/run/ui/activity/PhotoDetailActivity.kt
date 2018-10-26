package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.run.common.base.BaseActivity
import com.run.common.utils.UStatusBar
import com.run.common.utils.UWebView
import com.run.common.view.PageIndicator
import com.run.presenter.contract.ArticleDetailContract
import com.run.presenter.modle.ArticleDetailModle
import com.run.share.ShareHelper
import com.run.ui.R

@Suppress("DEPRECATION")
/**
 * 图片详情页面
 */
class PhotoDetailActivity : BaseActivity<ArticleDetailContract.ArticlePresenter>(), ArticleDetailContract.ArticleView {
    companion object {
        fun newInstance(context: Context, articled: Int, money: String) {
            var intent = Intent(context, PhotoDetailActivity::class.java)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("MONEY", money)
            context.startActivity(intent)
        }

        const val TAG = "ArticleDetailActivity"
    }

    override fun initContentView(): Int {
        return R.layout.activity_photo_view
    }

    private lateinit var viewpager: ViewPager
    private lateinit var dot_horizontal: LinearLayout
    private lateinit var paths: ArrayList<String>
    private lateinit var titleView: TextView
    override fun initViews() {
        UStatusBar.setStatusColor(this, resources.getColor(com.run.common.R.color.black), 0)
        UStatusBar.setDarkMode(this@PhotoDetailActivity)

        dot_horizontal = findViewById(R.id.dot_horizontal)
        titleView = findViewById(R.id.titleView)

        findViewById<View>(R.id.returnView).setOnClickListener { finish() }
        findViewById<View>(R.id.iv_share).setOnClickListener { ShareHelper.instance.doShare(this, articleid!!, money) }
    }

    var articleid: Int? = null
    private lateinit var money: String
    override fun initData() {
        articleid = intent.getIntExtra("ARTICLEID", 0)
        money = intent.getStringExtra("MONEY")
        mPresenter!!.requestData(articleid!!)
    }

    override fun initPresenter(): ArticleDetailContract.ArticlePresenter? {
        return ArticleDetailContract.ArticlePresenter(this)
    }

    private lateinit var title: String
    override fun callBackData(modle: ArticleDetailModle) {
        val data = modle.data ?: return
        title = data.title!!
        titleView.text = title
        paths = UWebView.getImagePath(data.content!!) as ArrayList<String>
        initViewPager()
    }
    //==========================================初始化viewpager============================================================================
    /**
     * 初始化viewpager
     */
    private fun initViewPager() {
        viewpager = findViewById(R.id.viewpager)
        viewpager.adapter = object : PagerAdapter() {
            override fun isViewFromObject(p0: View, p1: Any): Boolean {
                return p0 == p1
            }

            override fun getCount(): Int {
                return if (paths == null) 0 else paths.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val adView = LayoutInflater.from(this@PhotoDetailActivity).inflate(R.layout.layout_photoview, null)
                val icon = adView.findViewById(R.id.photoview) as PhotoView
                Glide.with(this@PhotoDetailActivity)
                        .load(paths[position])
                        .into(icon)
                container.addView(adView)
                return adView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        viewpager.addOnPageChangeListener(PageIndicator(this@PhotoDetailActivity, dot_horizontal, paths.size))
    }

}
