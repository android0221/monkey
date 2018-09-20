package com.run.presenter.modle


import com.run.config.modle.BaseModle

/**
 */

class ContestModle : BaseModle() {


    /**
     * status : 200
     * msg : 收徒大赛暂未开放，请留意公告
     * my_nun : 2
     * invite_nun : 2
     * valid_nun : 1
     * activity_type : 0
     * activity_explain : <img src="http://wx2.sinaimg.cn/large/64101caagy1fovvs0gtfij20f606n42v.jpg" alt="收徒大赛"></img>
     * <span style="font-size: 18px; color: rgb(255, 0, 0);font-weight:bold;">→收徒大赛奖励已全部发放到账，请注意查收！</span>
     * activity_bottom : <div class="text">
     * 【温馨提示】：由于微信会拦截诱导分享，分享一段时间后会被屏蔽，如发现分享的文章已被屏蔽，请隔两分钟后重新分享即可！
     *   有效收徒额外奖励金额设置：
     * <hr style="height:1px;border:none;border-top:1px solid #555555;"></hr>
     *
     *   第一名：奖励688元
     *
     *   第二名：奖励388元
     *
     *   第三名：奖励288元
     *
     *   第四名：奖励188元
     *
     *   第五至第十名：奖励38元
     *
     *   第十一至第二十名：奖励28元
     * <hr style="height:1px;border:none;border-top:1px solid #555555;"></hr>
     *   1、注意：收徒大赛 排名是按照有效收徒排名！<br></br>
     *   2、奖励发放：活动结束后次日下���5点前全部发放到账！<br></br>
     *   3、活动时间：2月28日-3月4日（五天时间）<br></br>
     *   4、有效收徒条件：徒弟成功提现一次-即算有效收徒！
    </div> *
     * list : [{"num":1,"user_id":10000,"money":"688元"},{"num":1,"user_id":10002,"money":"388元"}]
     */

    var my_nun: String? = null
    var invite_nun: Int = 0
    var valid_nun: Int = 0
    var activity_type: String? = null
    var activity_explain: String? = null
    var activity_bottom: String? = null
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * num : 1
         * user_id : 10000
         * money : 688元
         */

        var num: Int = 0
        var user_id: Int = 0
        var money: String? = null

        var head_avatar: String? = null
    }
}
