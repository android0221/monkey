package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.dialog.DialogHelper
import com.run.common.view.MyBottomSheetDialog
import com.run.ui.R
import com.run.ui.fragment.ApprenticeFragment
import kotlinx.android.synthetic.main.activity_apprentice_list.*
import kotlinx.android.synthetic.main.apprentice_expain_layout.*

@Suppress("DEPRECATION")
class ApprenticeListActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context, explain: String?) {
            val intent = Intent(context, ApprenticeListActivity::class.java)
            intent.putExtra("explain", explain)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_apprentice_list
    }

    override fun initViews() {
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
        sortView.setOnClickListener {
            showSortDialog()
        }
        initExpainLayout()
    }

    private fun showSortDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_apprent_sort_layout, null)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val dialog = MyBottomSheetDialog(this)
            dialog.setContentView(view)
            view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
            /**
             * 按照收益排行
             */
            view.findViewById<View>(R.id.sortMoneyView).setOnClickListener {
                dialog.cancel()
                fragment.order = 2
                fragment.requestData()
            }
            view.findViewById<View>(R.id.sortTimeView).setOnClickListener {
                dialog.cancel()
                fragment.order = 1
                fragment.requestData()
            }
            dialog.show()
        } else {
            view.findViewById<View>(R.id.tv_cancle).setOnClickListener { DialogHelper.closeDialog() }
            /**
             * 按照收益排行
             */
            view.findViewById<View>(R.id.sortMoneyView).setOnClickListener {
                DialogHelper.closeDialog()
                fragment.order = 2
                fragment.requestData()
            }
            view.findViewById<View>(R.id.sortTimeView).setOnClickListener {
                DialogHelper.closeDialog()
                fragment.order = 1
                fragment.requestData()
            }


            DialogHelper.showDialog(this, view)
        }

    }

    private lateinit var fragment: ApprenticeFragment
    override fun initData() {
        fragment = ApprenticeFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    /**
     * 初始化说明收徒奖励
     */
    private fun initExpainLayout() {
        titleLayout.setOnClickListener {
            apprenticeExpainLayout.visibility = if (apprenticeExpainLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        apprenticeExpainLayout.setOnClickListener {
            apprenticeExpainLayout.visibility = View.GONE
        }
        val explain = intent.getStringExtra("explain")
        webView.loadDataWithBaseURL(null, explain, "text/html", "utf-8", null)
    }

}
