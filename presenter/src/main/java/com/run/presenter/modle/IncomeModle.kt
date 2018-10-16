package com.run.presenter.modle

import com.run.config.modle.BaseModle

/**
 * Created by xiaor on 2018/3/29.
 */

class IncomeModle : BaseModle() {


    /**
     * typelist : [3,10,20,30,50,100,200]
     * status : 200
     * list : [{"title":"微信自动转账","value":2}]
     * msg : 注意：每人每天只能提现三次；提现什么时候到账?每周一至周五：当天15点前的提现申请，当天支付；15点后的申请，次日支付；
     * 周六日不受理提现；法定节假日提现、支付时间另行通知。
     * wechat_subscribe :0 没有绑定微信
     */


    var typelist: List<Int>? = null
    var list: List<ListBean>? = null

    var profit_balance: Double = 0.toDouble()//可提现收入
    var profit_total: Double = 0.toDouble()//总共收入
    var all_money: Double = 0.toDouble()
    var wechat_subscribe: Int = 0 //是否绑定微信

    class ListBean {
        /**
         * title : 微信自动转账
         * value : 2
         */

        var title: String? = null
        var value: Int = 0
    }
}
