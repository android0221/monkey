package com.run.presenter.modle.login

import com.run.config.modle.BaseModle


class ShareModle : BaseModle() {
    var share_data: ShareDataBean? = null

    class ShareDataBean {
        /**
         * url : http://T.syhnch.cn/society-therefore-private-buy-UTNUZVRkB2cFZwMvVGFaIQRq.action
         * title : 去年在山上捡回来的，不知道是小猫还是小狗，不过还是捡回来养了。可爱吧
         * share_picture : http://mpic.spriteapp.cn/ugc/2018/03/07/5a9ff53d6c5ae.gif
         * content_describe : 去年在山上捡回来的，不知道是小猫还是小狗，不过还是捡回来养了。可爱吧
         */

        var url: String? = null
        var title: String? = null
        var share_picture: String? = null
        var content_describe: String? = null
        var sort: String? = null
        var friend_url: String? = null
        var friend_type: Int = 0
    }
}
