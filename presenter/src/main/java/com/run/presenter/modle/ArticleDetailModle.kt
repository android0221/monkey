package com.run.presenter.modle


import com.run.config.modle.BaseModle


class ArticleDetailModle : BaseModle() {


    var data: ArticleDetailBean? = null
    var list: List<ArticleBean>? = null
    var money: String? = null
    var a_type: Int = 0
    var a_title: String? = null
    var a_money: String? = null
    var g_type: Int = 0
    var g_title: String? = null
    var g_money: String? = null


    var share_msg: String? = null

    /**
     * status : 200
     * data : [{"article_id":21308,"category_id":43,"title":"今晚，你自己点的火！你自己灭！","cover_picture":"http://img.baozoukanshu.com/uploads/bookCopyImg/20180124/5faf1643aff4ef93a42224a91fd8dfad.png","view_count":151,"content_type":1,"money_view_user":"0.000","fake_view_max":47916,"title_end":""},{"article_id":21307,"category_id":43,"title":"\u201c你弄痛我了！\u201d\u201c那我轻点\u2026\u2026\u201d","cover_picture":"http://img.baozoukanshu.com/uploads/bookCopyImg/20180205/9954c16993a6f7c148f48a34b31e4055.png","view_count":99,"content_type":1,"money_view_user":"0.000","fake_view_max":64430,"title_end":""},{"article_id":21306,"category_id":43,"title":"新婚夜走错楼层上了邻家妹妹的床引发的事难收拾......","cover_picture":"http://wx3.sinaimg.cn/mw600/661eb95cgy1fli17l1kcij20xc0m3q4t.jpg","view_count":77,"content_type":1,"money_view_user":"0.000","fake_view_max":51508,"title_end":""},{"article_id":21305,"category_id":43,"title":"有需求是如何和保守女朋友讲......","cover_picture":"http://ws2.sinaimg.cn/mw600/82e98952gy1fl2qde9mstj20xc0m8go8.jpg","view_count":2,"content_type":1,"money_view_user":"0.000","fake_view_max":65470,"title_end":""},{"article_id":21304,"category_id":43,"title":"这个鲜红的唇印，究竟是谁的......","cover_picture":"http://wx4.sinaimg.cn/mw600/006AlxShly1fknb4z04ocj31111jkdo2.jpg","view_count":3,"content_type":1,"money_view_user":"0.000","fake_view_max":83748,"title_end":""},{"article_id":21303,"category_id":43,"title":"情侣之间前戏怎么做才最有激情......","cover_picture":"http://ws4.sinaimg.cn/mw600/006HcaCrgy1fkcc81xepgj30f00qo40s.jpg","view_count":26,"content_type":1,"money_view_user":"0.000","fake_view_max":65183,"title_end":""},{"article_id":21302,"category_id":43,"title":"姐夫你快停下，姐姐要回来了\u2026\u2026 ","cover_picture":"http://wx2.sinaimg.cn/mw600/7c4f157bly1fjp8q5ew9yj20go0p040w.jpg","view_count":70,"content_type":1,"money_view_user":"0.000","fake_view_max":57930,"title_end":""},{"article_id":21301,"category_id":43,"title":"前戏这么做，让女人最舒服\u2026\u2026 ","cover_picture":"http://wx4.sinaimg.cn/mw600/006HcaCrgy1fj35zwmcokj30u00u0tb4.jpg","view_count":12,"content_type":1,"money_view_user":"0.000","fake_view_max":44910,"title_end":""},{"article_id":21300,"category_id":43,"title":"小萝莉和老大叔，一个愿打一个愿挨","cover_picture":"http://wx4.sinaimg.cn/mw600/82e98952gy1fl094omrmaj20qn0zk792.jpg","view_count":2,"content_type":1,"money_view_user":"0.000","fake_view_max":84246,"title_end":""},{"article_id":21299,"category_id":43,"title":"第一天上班，发现上司竟然是约过炮的对象......","cover_picture":"http://photocdn.sohu.com/20121101/Img356403860.jpg","view_count":2,"content_type":1,"money_view_user":"0.000","fake_view_max":97049,"title_end":""}]
     * money : 0.15
     * a_type : 1
     * a_title : 活动价
     * a_money : 0.15元
     * g_type : 0
     * g_title : 限时上涨到
     * g_money : 0.2元
     */
    class ArticleDetailBean {
        var category_id: Int = 0
        var title: String? = null
        var content: String? = null
        var content_describe: String? = null
        var share_picture: String? = null
        var create_time: String? = null
        var frame_url: String? = null
        var content_type: Int = 0
    }


}
