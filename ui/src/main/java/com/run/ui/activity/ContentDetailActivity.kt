package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.modle.login.DyContentModle
import com.run.ui.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContentDetailActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Context, title: String, id: Int) {
            val intent = Intent(activity, ContentDetailActivity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("id", id)
            activity.startActivity(intent)


        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_content_detail
    }

    private var wb_msg: WebView? = null
    private var tv_title: TextView? = null
    override fun initViews() {
        tv_title = findViewById(R.id.tv_title)
        wb_msg = findViewById(R.id.wb_content)

        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
    }

    override fun initData() {
        val title = intent.getStringExtra("TITLE")
        tv_title!!.setText(title)
        val id = intent.getIntExtra("id", 0)
        LoginManager.problemContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<DyContentModle>() {
                    override fun onError(errorType: Int, msg: String?) {
                        showErr(errorType, msg!!)
                    }

                    override fun onSuccess(model: DyContentModle) {
                        val bean = model.data
                        if (bean != null) {
                            wb_msg!!.loadDataWithBaseURL(null, bean!!.content, "text/html", "utf-8", null)
                        }
                    }
                })
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
