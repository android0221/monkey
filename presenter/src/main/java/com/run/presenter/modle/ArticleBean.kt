package com.run.presenter.modle

import com.chad.library.adapter.base.entity.MultiItemEntity

class ArticleBean : MultiItemEntity {


    /**
     * article_id : 21308
     * category_id : 43
     * title : 今晚，你自己点的火！你自己灭！
     * cover_picture : http://img.baozoukanshu.com/uploads/bookCopyImg/20180124/5faf1643aff4ef93a42224a91fd8dfad.png
     * view_count : 151
     * content_type : 1
     * money_view_user : 0.000
     * fake_view_max : 47916
     * title_end :
     * fake_view_begin
     */

    var article_id: Int = 0
    var category_id: Int = 0
    var title: String? = null
    var cover_picture: String? = null
    var view_count: Int = 0
    var content_type: Int = 0
    var money_view_user: String? = null
    var fake_view_max: Int = 0
    var title_end: String? = null
    var fake_view_begin: String? = null


    //====================================文章类型分类===================================================
    companion object {
        val ARTICLE_TEXT = 1   //普通新闻
        val ARTICLE_VEDIO = 2  //视频
        val ARTICLE_IMAGE = 3  //图片
        val ARTICLE_ADF = 4    //广告
    }


    private var itemType: Int = 0
    override fun getItemType(): Int {
        return itemType
    }

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }
}
