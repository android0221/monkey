package com.run.ui.login

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
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
import com.run.presenter.LoginHelper
import com.run.presenter.contract.login.RegisterContract
import com.run.ui.R

class RegisterActivity : BaseActivity<RegisterContract.RegisterPresenter>(), RegisterContract.RegisterView {

    companion object {
        fun newInstance(act: Activity, type: Int, code: Int) {
            val intent = Intent(act, RegisterActivity::class.java)
            intent.putExtra("TYPE", type)
            act.startActivityForResult(intent, code)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_register
    }


    private lateinit var tv_show_password: TextView
    private lateinit var tv_code: TextView
    private lateinit var tv_title: TextView
    private lateinit var iv_delete_mobile: ImageView
    private lateinit var iv_delete_password: ImageView
    private lateinit var iv_delete_code: ImageView
    private lateinit var et_mobile: EditText
    private lateinit var et_password: EditText
    private lateinit var et_code: EditText
    override fun initViews() {
        tv_title = findViewById(R.id.tv_title)
        tv_show_password = findViewById(R.id.tv_show_password)
        tv_code = findViewById(R.id.tv_code)
        iv_delete_mobile = findViewById(R.id.iv_delete_mobile)
        iv_delete_password = findViewById(R.id.iv_delete_password)
        iv_delete_code = findViewById(R.id.iv_delete_code)
        et_mobile = findViewById(R.id.et_mobile)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)
        initViewStatus()
        findViewById<View>(R.id.tv_back).setOnClickListener(this)
        findViewById<View>(R.id.tv_sure).setOnClickListener(this)
        iv_delete_mobile.setOnClickListener(this)
        iv_delete_password.setOnClickListener(this)
        iv_delete_code.setOnClickListener(this)
        tv_code.setOnClickListener(this)
        tv_show_password.setOnClickListener(this)
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
                    tv_code.isEnabled = true
                } else {
                    tv_code.isEnabled = false
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
        et_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    iv_delete_code.visibility = View.VISIBLE
                } else {
                    iv_delete_code.visibility = View.GONE
                }
            }
        })
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


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_back -> finish()
            R.id.iv_delete_mobile -> et_mobile.setText("")
            R.id.iv_delete_code -> et_code.setText("")
            R.id.iv_delete_password -> et_password.setText("")
            R.id.tv_show_password -> showPassword()
            R.id.tv_sure -> doSure()
            R.id.tv_code -> getCode()
        }
    }


    //================================数据初始化=====================================================
    private var mType = 1//1:表示注册；2:表示找回密码

    override fun initData() {
        mType = intent.getIntExtra("TYPE", 1)
        when (mType) {
            1 -> tv_title.text = "手机注册"
            2 -> {
                tv_title.text = "找回密码"
                et_mobile.setText(LoginHelper.instance.getmMobile())
            }
        }


    }

    private var mMobile: String? = null
    private var mPassword: String? = null
    private var mCode: String? = null
    private fun doSure() {
        mMobile = et_mobile.text.toString().trim { it <= ' ' }
        mPassword = et_password.text.toString().trim { it <= ' ' }
        mCode = et_code.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mMobile) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mCode)) {
            showMsg("输入内容不能为空！请检查")
            return
        }
        if (mType == 1) {
            mPresenter!!.doRegister(mMobile!!, mCode!!, mPassword!!)
        } else if (mType == 2) {
            mPresenter!!.resetPassword(mMobile!!, mCode!!, mPassword!!)
        }
    }


    override fun initPresenter(): RegisterContract.RegisterPresenter? {
        return RegisterContract.RegisterPresenter(this)
    }

    /*
    * 获取验证码
    */
    private fun getCode() {
        mMobile = et_mobile.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mMobile)) {
            showMsg("手机号不能为空！请检查")
            return
        }
        mPresenter!!.getCode(mMobile!!, mType)
    }


    //=================================回调返回======================================================

    /**
     * 注册或修改密码返回结果
     */
    override fun callBackfinish(msg: String) {
        showMsg(msg)
        val intent = Intent()
        intent.putExtra("MOBILE", mMobile)
        intent.putExtra("PASSWORD", mPassword)
        setResult(RESULT_OK, intent)
        this.finish()
    }


    /**
     * 设置获取验证码按钮的状态
     */
    private var mTimer: CountDownTimer? = null

    override fun callBackCode() {
        showMsg("获取验证码成功！")
        mTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                tv_code.isEnabled = false
                tv_code.text = "还剩" + l / 1000 + "s"
            }

            override fun onFinish() {
                tv_code.text = "获取验证码"
                tv_code.isEnabled = true
            }
        }
        (mTimer as CountDownTimer).start()
    }

    /**
     * 销毁验证码计时
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

}
