package com.run.presenter.modle


import com.run.config.modle.BaseModle

class ArticleTypeModle : BaseModle() {

    /**
     * status : 200
     * data : [{"category_id":0,"name":"推荐"},{"category_id":37,"name":"热点"},{"category_id":43,"name":"小说"},{"category_id":34,"name":"美女"},{"category_id":28,"name":"养生"},{"category_id":25,"name":"搞笑"},{"category_id":38,"name":"美文"},{"category_id":46,"name":"猎奇"},{"category_id":26,"name":"社会"},{"category_id":27,"name":"育儿"},{"category_id":29,"name":"吃货"},{"category_id":30,"name":"两性"},{"category_id":40,"name":"科技"},{"category_id":39,"name":"探索"},{"category_id":42,"name":"旅游"},{"category_id":41,"name":"历史"},{"category_id":44,"name":"高价"}]
     */

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * category_id : 0
         * name : 推荐
         */

        var category_id: Int = 0
        var name: String? = null

        constructor(category_id: Int, name: String?) {
            this.category_id = category_id
            this.name = name
        }

        constructor()


    }
}
