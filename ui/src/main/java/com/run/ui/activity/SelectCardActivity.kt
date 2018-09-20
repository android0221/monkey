package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.SelectCardFrament

class SelectCardActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(act: Activity, cardID: Int, requestCode: Int) {
            val intent = Intent(act, SelectCardActivity::class.java)
            intent.putExtra("CARDID", cardID)
            act.startActivityForResult(intent, requestCode)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_select_card

    }

    override fun initViews() {
        findViewById<View>(R.id.iv_back).setOnClickListener(this)
        findViewById<View>(R.id.cancle).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.cancle -> {
                val intent = Intent()
                intent.putExtra("CARDID", 0)
                intent.putExtra("TITLE", "")
                this@SelectCardActivity.setResult(RESULT_OK, intent)
                this.finish()
            }
        }
    }

    private var cardid: Int = 0
    override fun initData() {
        cardid = intent.getIntExtra("CARDID", -1)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, SelectCardFrament.newInstance(cardid))
        fragmentTransaction.commit()
    }
    override fun initPresenter(): Nothing? {
        return null
    }


}
