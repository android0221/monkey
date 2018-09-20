package com.run.presenter.modle


import com.run.config.modle.BaseModle

/**
 * Created by Administrator on 2018/3/16.
 */

class UserJsonModle : BaseModle() {


    /**
     * status : 200
     * data : {"head_avatar":null,"wechat_nick_name":null,"mobile":"13077894546","user_id":17282,"profit_balance":"1.010","profit_total":"1.010"}
     * deposit : [{"outer_name":"A~心如止水","money":"5.00","create_time":"2018-04-03 16:21:51","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJSiaueqhWk0z8o02Tic0FLo6lib5icsZCehb7aBVUGgMo6RADbG8MwrSMiayibwLCdd02QNqvKybOK8UAQ/132"},{"outer_name":"小溪","money":"5.00","create_time":"2018-04-03 16:21:36","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLfIDOibG8vpByapzRblznxFNuHiaWSY2tWpcBEJ0VjtpibH9Sib76JOqBzfUh3llSrcGVTvP1Qub1BjQ/132"},{"outer_name":"阿杜威信ddhafq668","money":"5.00","create_time":"2018-04-03 16:13:16","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJwZP5j4zRcX4r3D1hGyONp1wNxFUxicDgsjDGFwslBk7ETGgIgcwOicFJIF2QXXg6UicMj1FziaklgUA/132"},{"outer_name":"天天来钱V信744683693","money":"5.00","create_time":"2018-04-03 16:12:52","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DauIm0j4kOibxliaUPfic2PCgVFltjJxFoSbeMcunwzHJBib27FKqQ7dU946OLiaDQfWhVWibiccvE6gias3SOb7IY42ibg/132"},{"outer_name":"万里长城永不倒","money":"5.00","create_time":"2018-04-03 16:07:02","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/dm6QiajBGqSyec7X5C2J7oQQO55vYaVPdb6YULnIMx9DxXMgKQkysf5bpIVuvmW0Z1SCCt0ctAwF6ahGcic70eVA/132"},{"outer_name":"幸福人生","money":"10.00","create_time":"2018-04-03 16:06:35","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/GBy50QGsYNhmcDGhicTr2aczJtYsP2JibibYMvw4hibkJicoWVK7qHibpKejWZRtsF3MfZYibsXIia537zuXnAS7FGetXg/132"},{"outer_name":"虎牙","money":"5.00","create_time":"2018-04-03 16:06:05","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLyqVVjubY0hzw9Ow7GBp3icc9Sd0xeyorbAdo7fiarZb9ibTzPSsl6gIzQ7nom3Vlxpjf5GmkXtT6ibQ/132"},{"outer_name":"活在当下","money":"5.00","create_time":"2018-04-03 16:05:58","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/zNwoXIh18yGhiaatI4icsCRRR4jBrHeh5597Sh5Fdk1ia9HcuicI2eIB42SFkFjficTQlIdMZAdBr16eZTqz2njb55w/132"},{"outer_name":"失落的记忆","money":"10.00","create_time":"2018-04-03 16:05:50","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIo1ypyzcZ638Wq1HAdf5ic9yicdYpHI6n6DoDTNX2bSU0iaBWmATHzrgky2Aux11vUk52gefjpuvVow/132"},{"outer_name":"人生","money":"5.00","create_time":"2018-04-03 16:05:45","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKlBmbyNfEnArPRt54ZgmInAEDrvT32e8xYVHGTSItQiaWKbsVOQH1d8EfBVIicWVPeibOjVNZH8k3Kg/132"}]
     * signtype : 0
     * shield_type : 0
     * shield_msg : 暂无弹窗消息
     * count_income : null
     * count_invite : 0
     */

    var data: DataBean? = null
    var signtype: Int = 0
    var shield_type: Int = 0
    var shield_msg: String? = null
    var count_income: String? = null
    var count_invite: Int = 0
    var deposit: List<DepositBean>? = null

    class DataBean {
        /**
         * head_avatar : null
         * wechat_nick_name : null
         * mobile : 13077894546
         * user_id : 17282
         * profit_balance : 1.010
         * profit_total : 1.010
         */

        var head_avatar: String? = null
        var wechat_nick_name: String? = null
        var mobile: String? = null
        var user_id: Int = 0
        var profit_balance: String? = null
        var profit_total: String? = null
    }

    class DepositBean {
        /**
         * outer_name : A~心如止水
         * money : 5.00
         * create_time : 2018-04-03 16:21:51
         * head_avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJSiaueqhWk0z8o02Tic0FLo6lib5icsZCehb7aBVUGgMo6RADbG8MwrSMiayibwLCdd02QNqvKybOK8UAQ/132
         */

        var outer_name: String? = null
        var money: String? = null
        var create_time: String? = null
        var head_avatar: String? = null
    }
}
