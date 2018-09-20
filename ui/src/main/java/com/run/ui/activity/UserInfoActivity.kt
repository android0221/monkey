package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.presenter.contract.UserContract
import com.run.presenter.modle.login.UserInfoModile
import com.run.ui.R

class UserInfoActivity : BaseActivity<UserContract.UserPresenter>(), UserContract.UserView {

    companion object {
        fun newInstance(act: Activity) {
            act.startActivity(Intent(act, UserInfoActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_user_info
    }

    private lateinit var iv_user_icon: ImageView
    private lateinit var tv_title: TextView
    private lateinit var tv_address: TextView
    private lateinit var tv_mobile: TextView
    private lateinit var tv_sex: TextView
    private lateinit var tv_apprentice: TextView
    private lateinit var tv_nick: TextView
    override fun initViews() {
        tv_title = findViewById(R.id.tv_title)
        tv_address = findViewById(R.id.tv_address)
        tv_mobile = findViewById(R.id.tv_mobile)
        tv_apprentice = findViewById(R.id.tv_apprentice)
        tv_sex = findViewById(R.id.tv_sex)
        tv_nick = findViewById(R.id.tv_nick)
        iv_user_icon = findViewById(R.id.iv_user_icon)

        findViewById<View>(R.id.iv_back).setOnClickListener(this)
        findViewById<View>(R.id.ll_apprentice).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> this.finish()
            R.id.ll_apprentice -> {
                if (tv_apprentice!!.text.length < 4) {
                    TeacherActivity.newInstance(this, 11)
                }
            }
        }
    }

    /**
     * 拜师回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        if (requestCode == 11 && resultCode == RESULT_OK) {
            val firstId = data.getStringExtra("MODIFYMSG")
            tv_apprentice!!.text = firstId
        }
    }

    //===========================================数据操作============================================
    override fun initPresenter(): UserContract.UserPresenter? {
        return UserContract.UserPresenter(this)
    }

    override fun initData() {
        mPresenter!!.user_info()
    }

    override fun showData(bean: UserInfoModile.DataBean) {
        if (bean == null) return
        UGlide.loadCircleImage(this, bean.head_avatar, iv_user_icon!!)
        tv_title!!.text = "会员ID:" + bean.user_id
        tv_mobile!!.text = if (TextUtils.isEmpty(bean.mobile)) "未绑定" else bean.mobile
        if (!TextUtils.isEmpty(bean.city_name)) {
            val address = bean.province_name + "~" + bean.city_name
            tv_address!!.text = address
        }
        val apprenticeID = if (bean.first_user_id.toInt() == 0) "未绑定" else bean.first_user_id.toString()
        tv_apprentice!!.text = apprenticeID
        tv_nick!!.text = "" + bean.user_id
        tv_sex!!.text = if (bean.gender === 1) "男" else "女"
    }


}
