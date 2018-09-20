package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UInputManager
import com.run.presenter.contract.TeacherContract
import com.run.ui.R

class TeacherActivity : BaseActivity<TeacherContract.TeacherPresenter>(), TeacherContract.TeacherView {

    companion object {
        fun newInstance(context: Activity, request: Int) {
            val intent = Intent(context, TeacherActivity::class.java)
            context.startActivityForResult(intent, request)
        }

    }

    override fun initContentView(): Int {
        return R.layout.activity_teacher
    }

    private lateinit var et_edit_1: EditText
    private lateinit var tv_ok: TextView
    override fun initViews() {
        tv_ok = findViewById(R.id.tv_ok)
        et_edit_1 = findViewById(R.id.et_edit_1)
        et_edit_1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                tv_ok!!.isEnabled = s.isNotEmpty()
            }
        })
        findViewById<View>(R.id.iv_back).setOnClickListener(this)
        tv_ok!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_ok -> doTeacher()
        }
    }

    //=========================================数据操作==============================================
    override fun initPresenter(): TeacherContract.TeacherPresenter? {
        return TeacherContract.TeacherPresenter(this)
    }

    override fun initData() {
    }

    /**
     * 执行拜师操作
     */
    private var first_id: String? = null

    private fun doTeacher() {
        first_id = et_edit_1!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(first_id)) {
            showMsg("师傅ID不能为空！")
            return
        }
        UInputManager.closeKeybord(et_edit_1, this)
        mPresenter!!.user_info(first_id!!)
    }

    override fun showSuccess(msg: String) {
        showMsg(msg)
        val intent = Intent()
        intent.putExtra("MODIFYMSG", first_id)
        setResult(RESULT_OK, intent)
        this.finish()
    }


}
