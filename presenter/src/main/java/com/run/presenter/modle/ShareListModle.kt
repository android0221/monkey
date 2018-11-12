package com.run.presenter.modle

import com.run.config.modle.BaseModle

class ShareListModle:BaseModle() {


    /**
     * status : 200
     * share_data : [{"url":"http://t.cn/EAY5de4","title":"苏35战机迂回拦截美法军机：美军战机一动不动"},{"url":"http://t.cn/EAY5dDk","title":"上门求购战机被明拒：法国给出的理由令人很尴尬"},{"url":"http://t.cn/EAY5gvt","title":"再次下水三艘舰艇，兼具火力成本，能力不逊052D驱逐舰"}]
     */

    var share_data: List<ShareDataBean>? = null
    class ShareDataBean {
        /**
         * url : http://t.cn/EAY5de4
         * title : 苏35战机迂回拦截美法军机：美军战机一动不动
         */

        var url: String? = null
        var title: String? = null
    }
}
