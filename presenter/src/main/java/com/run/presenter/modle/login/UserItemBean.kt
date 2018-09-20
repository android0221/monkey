package com.run.presenter.modle.login
class UserItemBean {

    var title: String? = null
    var content: String? = null

    var id: Int = 0

    var create_time: String? = null


    var resId: Int = 0

    var isColor: Boolean = false

    constructor() {}

    constructor(title: String, content: String) {
        this.title = title
        this.content = content
    }

    constructor(title: String, resId: Int) {
        this.title = title
        this.resId = resId
    }

    constructor(title: String, content: String, color: Boolean) {
        this.title = title
        this.content = content
        this.isColor = color
    }

}
