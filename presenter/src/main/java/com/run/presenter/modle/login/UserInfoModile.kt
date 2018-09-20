package com.run.presenter.modle.login

import com.run.config.modle.BaseModle


class UserInfoModile : BaseModle() {

    var data: DataBean? = null

    class DataBean {
        /**
         * user_id : 13769
         * mobile : 13077894546
         * nick_name :
         * user_alipay : null
         * real_name : null
         * wechat_nick_name : null
         * head_avatar : null
         * province_name : 广东
         * city_name : 深圳
         * gender : 1
         * wechat_account : null
         * first_user_id : null
         */

        var user_id: Int = 0
        var mobile: String? = null
        var nick_name: String? = null
        var user_alipay: String? = null
        var real_name: String? = null
        var wechat_nick_name: String? = null
        var head_avatar: String? = null
        var province_name: String? = null
        var city_name: String? = null
        var gender: Int = 0
        var wechat_account: String? = null
        var first_user_id: Long = 0

        var idiograph: String? = null

    }

}
