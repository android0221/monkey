package com.run.presenter.modle


import com.run.config.modle.BaseModle

class SeniorityModle : BaseModle() {

    /**
     * status : 200
     * userinfo : {"mobile":"13077894546","head_avatar":null,"wechat_nick_name":null,"user_id":13769,"profit_total":"1.000"}
     * list : [{"mobile":"150****7834","head_avatar":null,"wechat_nick_name":"","profit_total":"601.208"},{"mobile":"188****7328","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZIUpiasBAichpIUBESmoayibEkHy5SyqwxNPXfernKzmdqsM970UO0vy63ntlDNY9nwmpHYIXpX21A/132","wechat_nick_name":"***********女神","profit_total":"411.080"},{"mobile":"130****9989","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epgz0j2s6gichu5XkYXpZYpe1ICydRYTK8k5Qh61pxauEUQ9n6f5xzOciasYgZlQqRjybUc5qSGlKcQ/132","wechat_nick_name":"***********手赚工作室","profit_total":"349.530"},{"mobile":"158****5257","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/NhBClVGd7lfImDKGVJIiarrBqw08aicQSsTGHcViaKUXLCAQSGwfnB1DNpX1NBsiaE7ZOUv9WOOibaW4jfzciaJ9fKxQ/132","wechat_nick_name":"手赚推广***********","profit_total":"279.920"},{"mobile":"151****6324","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEL3oFH8rPqKWweLiacAwS4blZbHg4icuw7dYkeUZ92icDuEDRQvAofXcj48TIVTsNBcf92JPFoM6hP5g/132","wechat_nick_name":"最强战队","profit_total":"226.824"},{"mobile":"152****2655","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erqf66rr6j1HuUs97qWo65xSVK9tTu6XIPj9E4cwG08bG5lBEu7W9gYTsZgzanLP5uHVUeGibPAcgA/132","wechat_nick_name":"吉祥如意","profit_total":"164.888"},{"mobile":"182****4725","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTICuRuBLjC0aBhqsiaZbcOY1KrgmPrQqJ9sroWwtCfXdicOlcOib1MNvZBf9VERtf23t9jRXesr8fK6g/132","wechat_nick_name":"小厨娘微信*********","profit_total":"163.172"},{"mobile":"139****0568","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/3bZVQmzTJia23yn7rHxSiaS1WZNwwaCoibhXAKfiaiccsaYNOfbpSug5oYJFaBhhIKoO6Vf5YpzkzX16gibE0jqVb29A/132","wechat_nick_name":"A平台推荐Lohke**","profit_total":"143.694"},{"mobile":"132****8735","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/R29thWnr3Va6U4BHZXmYb9rsicezSYTSjTHwkrram7h7hH4l1h0xiaF54rhoRGibXlHnFH1LNwx0TDkiaMWKkLBmvw/132","wechat_nick_name":"宝宝V信*********","profit_total":"129.770"},{"mobile":"150****4595","head_avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/TM4cZKgcJ4kUvawb5DHgpM0NP7Z1ib3qPndhBNI0xPe7lTkIg7AGSZOibaP5bGeEuw1tXib0BvBKqicibo3mCyia4ILA/132","wechat_nick_name":"**********苗苗","profit_total":"127.384"}]
     * activity_type : 0
     * activity_explain : <img src="http://wx4.sinaimg.cn/mw690/64101caagy1fp54q7s4bhj20f606ngq9.jpg" alt="收徒大赛"></img>
     */


    var userinfo: UserinfoBean? = null
    var activity_type: String? = null
    var activity_explain: String? = null
    var list: List<ListBean>? = null

    class UserinfoBean {
        /**
         * mobile : 13077894546
         * head_avatar : null
         * wechat_nick_name : null
         * user_id : 13769
         * profit_total : 1.000
         */

        var mobile: String? = null
        var head_avatar: String? = null
        var wechat_nick_name: String? = null
        var user_id: Int = 0
        var profit_total: String? = null
    }

    class ListBean {
        /**
         * mobile : 150****7834
         * head_avatar : null
         * wechat_nick_name :
         * profit_total : 601.208
         */

        var mobile: String? = null
        var head_avatar: String? = null

        var num: String? = null
        var wechat_nick_name: String? = null
        var profit_total: String? = null
    }
}
