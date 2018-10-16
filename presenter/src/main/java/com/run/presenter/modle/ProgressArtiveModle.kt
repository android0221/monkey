package com.run.presenter.modle

import com.run.config.modle.BaseModle

class ProgressArtiveModle : BaseModle() {

    /**
     * status : 200
     * this_week_list : [{"money":"7.680","create_time":"2018-10-15"}]
     * last_week_list : [{"money":"12.240","create_time":"2018-10-08"},{"money":"14.640","create_time":"2018-10-09"},{"money":"28.560","create_time":"2018-10-10"},{"money":"25.440","create_time":"2018-10-11"},{"money":"13.680","create_time":"2018-10-12"},{"money":"17.760","create_time":"2018-10-13"},{"money":"29.040","create_time":"2018-10-14"}]
     * this_week_money : 7.68
     * last_week_money : 141.36
     * explain : <h2>活动规则：</h2>
     *
     * 1. 上周(2018-10-08~2018-10-14)转发收入>=<span style="color:#c00">30</span>元的，并且本周(2018-10-15~2018-10-21)的转发收入比上周转发收入增加50%以上时，即可获得本周进步奖。
     *
     * 奖金=上周转发收入*20%
     *
     * 2. 上周转发收入 < <span style="color:#c00">30</span>元的会员，本周的转发收入>=<span style="color:#c00">45</span>元，也可以获得本周进步奖，奖金为<span style="color:#c00">6</span>元。
     *
     * 3. 奖金将在下周一(2018-10-22)发放，届时公布客服微信号，请联系客服领取。
     * this_week_explain : 合计：<span style="color:#c00">7.68</span>元，您上周转发收入(<span style="color:#c00">141.36</span>) 满足<span style="color:#c00">30</span>元,这周加油干满<span style="color:#c00">212.04</span>元，就可以获得本周进步奖，奖金为<span style="color:#c00">28.272</span>元。
     * prize_type : 1
     * prize_explain :
     */

    var this_week_money: Double = 0.toDouble()
    var last_week_money: Double = 0.toDouble()
    var explain: String? = null
    var this_week_explain: String? = null
    var prize_type: Int = 0
    var prize_explain: String? = null
    var this_week_list: List<WeekListBean>? = null
    var last_week_list: List<WeekListBean>? = null

    class WeekListBean {
        /**
         * money : 7.680
         * create_time : 2018-10-15
         */

        var money: String? = null
        var create_time: String? = null
    }


}
