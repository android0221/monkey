package com.run.presenter.modle

import com.run.config.modle.BaseModle

class SignModle : BaseModle() {


    /**
     * status : 200
     * num : 1
     * money : null
     * term_money : 2
     * start_money : 0.2
     * increasing : 0.05
     */

    var num: Int = 0
    var money: String? = null
    var term_money: Int = 0
    var start_money: Double = 0.toDouble()
    var increasing: Double = 0.toDouble()
}
