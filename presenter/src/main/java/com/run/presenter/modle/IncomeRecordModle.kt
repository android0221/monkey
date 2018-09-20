package com.run.presenter.modle


import com.run.config.modle.BaseModle

class IncomeRecordModle : BaseModle() {


    var data: List<DataBean>? = null

    class DataBean {
        /**
         * intro : 完成系统的新手注册任务,发放注册奖金!
         * money : 1.000
         * create_time : 2018-03-15 17:30:35
         */

        var intro: String? = null
        var money: String? = null
        var create_time: String? = null

        var empty_msg: String? = null


        constructor() {}

        constructor(empty_msg: String) {
            this.empty_msg = empty_msg
        }


    }
}
