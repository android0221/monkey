package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.SearchFragment

class SearchActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_search
    }
    private lateinit var et_search: EditText
    override fun initViews() {
        et_search = findViewById(R.id.et_search)
        et_search.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch()
            }
            false
        }
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
    }

    private var mWord: String? = null
    private fun doSearch() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this@SearchActivity.currentFocus
                        .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        mWord = et_search.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mWord)) return
        if (fragment != null) {
            fragment.searchData(mWord)
        }
    }

    private lateinit var fragment: SearchFragment
    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment = SearchFragment.newInstance()
        fragmentTransaction.add(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
