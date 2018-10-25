package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import com.run.common.base.BaseActivity
import com.run.common.utils.ULog
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_luck_turn_table_layout.*
import java.util.*

/**
 * 幸运大转盘
 */
open class LuckTurnTableActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, LuckTurnTableActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_luck_turn_table_layout
    }


    private var mStartAnimation: Animation? = null
    private var mEndAnimation: Animation? = null
    private var isRunning: Boolean = false
    private val mPrizeGrade = 6 //奖品级别，0代表没有
    private var mItemCount = 4
    private val mPrizePosition = intArrayOf(0, 4, 2, 1, 5, 3) //奖品在转盘中的位置(到达一等奖的距离)
    protected val prizeTitle = arrayOf("5元京东卡", "200元京东卡", "富光水杯", "小米电视", "扫地机器人", "iphone 7")

    override fun initViews() {
        mStartAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        mStartAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })


        startView.setOnClickListener {
            // 未抽过奖并有抽奖的机会
            var random = Random()
            mItemCount = random.nextInt(6) + 1
            ULog.d("LuckTurnTableActivity :$mItemCount")
            if (!isRunning) {
                isRunning = true
                mStartAnimation!!.reset()
                turntableView.startAnimation(mStartAnimation)
                if (mEndAnimation != null) {
                    mEndAnimation!!.cancel()
                }
                Handler().postDelayed({ endAnimation() }, 2000)
            }
        }
    }

    override fun initData() {
    }

    override fun initPresenter(): Nothing? {
        return null
    }


    // 结束动画，慢慢停止转动，抽中的奖品定格在指针指向的位置
    private fun endAnimation() {
        val position = mPrizePosition[mPrizeGrade - 1]
//        val toDegreeMin = 360 / mItemCount * (position - 0.5f)
        val toDegreeMin = 60.0f * mItemCount

        ULog.d("LuckTurnTableActivity  toDegreeMin :$toDegreeMin")

        val random = Random()
        val randomInt = random.nextInt(360 / mItemCount - 1)
        val toDegree = toDegreeMin + randomInt.toFloat() + (360 * 5).toFloat() //5周 + 偏移量
        // 按中心点旋转 toDegree度
        // 参数：旋转的开始角度、旋转的结束角度、X轴的伸缩模式、X坐标的伸缩值、Y轴的伸缩模式、Y坐标的伸缩值
        mEndAnimation = RotateAnimation(0f, toDegreeMin, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mEndAnimation!!.duration = 3000 // 设置旋转时间
        mEndAnimation!!.repeatCount = 0 // 设置重复次数
        mEndAnimation!!.fillAfter = true// 动画执行完后是否停留在执行完的状态
        mEndAnimation!!.interpolator = DecelerateInterpolator() // 动画播放的速度
        mEndAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                isRunning = false
                showMsg("恭喜你获得 ${prizeTitle[mItemCount - 1]}")
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        turntableView.startAnimation(mEndAnimation)
        mStartAnimation!!.cancel()
    }


}
