package com.run.ui

import com.run.presenter.modle.ArticleBean

/**
 * 文章管理的工具类
 */
class ArticleHelper private constructor() {

    companion object {
        private val TAG: String = ArticleHelper.javaClass.name
        private var articleHelper: ArticleHelper? = null
        val instance: ArticleHelper
            get() {
                if (articleHelper == null) {
                    synchronized(ArticleHelper::class.java) {
                        if (articleHelper == null) {
                            articleHelper = ArticleHelper()
                        }
                    }
                }
                return articleHelper!!
            }
    }


    var mList: List<ArticleBean>? = null
    fun saveList(list: List<ArticleBean>) {
        if (mList == null || mList!!.isEmpty()) {
            this.mList = list
        }
    }

    fun getList(): List<ArticleBean>? {
        return mList
    }

    var shareMoney: String? = ""
   
}
