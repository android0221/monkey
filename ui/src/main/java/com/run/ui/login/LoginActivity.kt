package com.run.ui.login

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UInputManager
import com.run.presenter.LoginHelper
import com.run.presenter.contract.login.LoginContract
import com.run.ui.R

/**
 * 登录
 */
class LoginActivity : BaseActivity<LoginContract.LoginPresenter>(), LoginContract.LoginView {


    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_login
    }


    private lateinit var tv_login: TextView
    private lateinit var tv_show_password: TextView
    private lateinit var iv_delete_mobile: ImageView
    private lateinit var iv_delete_password: ImageView
    private lateinit var et_mobile: EditText
    private lateinit var et_password: EditText
    override fun initViews() {
        tv_login = findViewById(R.id.tv_login)
        iv_delete_mobile = findViewById(R.id.iv_delete_mobile)
        iv_delete_password = findViewById(R.id.iv_delete_password)
        et_mobile = findViewById(R.id.et_mobile)
        et_password = findViewById(R.id.et_password)
        tv_show_password = findViewById(R.id.tv_show_password)
        initViewStatus()
        iv_delete_password.setOnClickListener(this)
        iv_delete_mobile.setOnClickListener(this)
        tv_login.setOnClickListener(this)
        tv_show_password.setOnClickListener(this)

        findViewById<View>(R.id.iv_back).setOnClickListener(this)
        findViewById<View>(R.id.ll_find_password).setOnClickListener(this)
        findViewById<View>(R.id.ll_register).setOnClickListener(this)
    }
    /**
     * 设置view的状态
     */
    private fun initViewStatus() {
        et_mobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    iv_delete_mobile.visibility = View.VISIBLE
                } else {
                    iv_delete_mobile.visibility = View.GONE
                }
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    iv_delete_password.visibility = View.VISIBLE
                } else {
                    iv_delete_password.visibility = View.GONE
                }
            }
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.iv_delete_mobile -> et_mobile.setText("")
            R.id.iv_delete_password -> et_password.setText("")
            R.id.tv_login -> login()
            R.id.tv_show_password -> showPassword()
            R.id.ll_find_password -> RegisterActivity.newInstance(this@LoginActivity, 2, 1)
            R.id.ll_register -> RegisterActivity.newInstance(this@LoginActivity, 1, 1)
        }

    }

    //是否显示密码
    private var showPassword = false

    private fun showPassword() {
        if (showPassword) {//显示密码
            et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            et_password.setSelection(et_password.text.toString().length)
            tv_show_password.text = "隐藏密码"
        } else {
            et_password.transformationMethod = PasswordTransformationMethod.getInstance()
            et_password.setSelection(et_password.text.toString().length)
            tv_show_password.text = "显示密码"
        }
        showPassword = !showPassword
    }


    //=============================================数据操作==========================================


    override fun initData() {
        et_mobile.setText(LoginHelper.instance.getmMobile())
        et_password.setText(LoginHelper.instance.getmPassword())
    }


    override fun initPresenter(): LoginContract.LoginPresenter? {
        return LoginContract.LoginPresenter(this)
    }


    fun login() {
        //关闭软键盘
        UInputManager.closeKeybord(et_mobile, this)
        val mobile = et_mobile.text.toString().trim { it <= ' ' }
        val password = et_password.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)) {
            showMsg("手机号或密码不能为空！")
            return
        }
        mPresenter!!.login(mobile, password)
    }


    override fun callBackLogin() {
        this.finish()
    }

    //=======================注册或返回密码返回=====================================================================
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val mobile = data.getStringExtra("MOBILE")
            val mPassword = data.getStringExtra("PASSWORD")
            et_mobile.setText(mobile)
            et_password.setText(mPassword)
            mPresenter!!.login(mobile, mPassword)
        }
    }


}
