package com.run.presenter.modle

import com.run.config.modle.BaseModle

class TransmitModle : BaseModle() {


    /**
     * status : 200
     * list : [{"money":"12.240","create_time":"2018-10-08"},{"money":"14.640","create_time":"2018-10-09"},{"money":"28.560","create_time":"2018-10-10"},{"money":"25.440","create_time":"2018-10-11"},{"money":"13.680","create_time":"2018-10-12"},{"money":"17.760","create_time":"2018-10-13"},{"money":"129.040","create_time":"2018-10-14"}]
     * explain :
     *
     *活动时间：2018-10-08 00:00:00-2018-10-14 23:59:59共7天
     *
     * <h3>活动规则：</h3>
     *
     * 转发收益达到以下任意要求，即可拿到红包奖励：
     *
     * 有4天每天转发收益>=20元，返5元
     *
     * 有4天每天转发收益>=30元，返10元
     *
     * 有4天每天转发收益>=50元，返30元
     *
     * 有4天每天转发收益>=100元，返50元
     *
     * 有4天每天转发收益>=200元，返100元
     * prize_type : 0
     * prize_explain : 很遗憾您未获得本期转发送现金红包奖励，请关注下期活动时间并继续努力。
     */

    var explain: String? = null
    var prize_type: Int = 0
    var prize_explain: String? = null
    var list: List<ArtiveBean>? = null


}
