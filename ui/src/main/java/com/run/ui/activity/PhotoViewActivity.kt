package com.run.ui.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
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
import com.run.common.view.PageIndicator
import com.run.share.ShareHelper
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_photo_view.*

class PhotoViewActivity : BaseActivity<Nothing>() {
    companion object {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun newInstance(context: Activity, list: ArrayList<String>, position: Int, title: String, articled: Int, money: String) {
            val intent = Intent(context, PhotoViewActivity::class.java)
            intent.putStringArrayListExtra("PATH", list)
            intent.putExtra("POSITION", position)
            intent.putExtra("title", title)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("MONEY", money)
            context.startActivity(intent)
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_photo_view
    }

    private lateinit var viewpager: ViewPager
    private var mPosition: Int = 0
    private lateinit var dot_horizontal: LinearLayout
    private lateinit var paths: ArrayList<String>
    private lateinit var titleView: TextView
    private lateinit var money: String
    override fun initViews() {
        UStatusBar.setStatusBarTranslucent(this)
        UStatusBar.setDarkMode(this@PhotoViewActivity)
        paths = intent.getStringArrayListExtra("PATH")
        mPosition = intent.getIntExtra("POSITION", 0)


        dot_horizontal = findViewById(R.id.dot_horizontal)
        initViewPager()
        viewpager.currentItem = mPosition

        val title = intent.getStringExtra("title")
        titleView = findViewById(R.id.titleView)
        titleView.text = title

        money = intent.getStringExtra("MONEY")

        findViewById<View>(R.id.returnView).setOnClickListener { finish() }
        findViewById<View>(R.id.iv_share).setOnClickListener { ShareHelper.instance.doShare(this, articleid!!, money) }
    }

    var articleid: Int? = null
    override fun initData() {
        articleid = intent.getIntExtra("ARTICLEID", 0)

        if (articleid == 0) {
            iv_share.visibility = View.GONE
            titleView.visibility = View.GONE
        }
    }

    override fun initPresenter(): Nothing? {
        return null
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
                return paths.size
            }

            @SuppressLint("InflateParams")
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val adView = LayoutInflater.from(this@PhotoViewActivity).inflate(R.layout.layout_photoview, null)
                val icon = adView.findViewById(R.id.photoview) as PhotoView
                Glide.with(this@PhotoViewActivity)
                        .load(paths[position])
                        .into(icon)
                container.addView(adView)
                return adView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        viewpager.addOnPageChangeListener(PageIndicator(this@PhotoViewActivity, dot_horizontal, paths.size))
    }


}
