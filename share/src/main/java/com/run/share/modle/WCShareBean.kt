package com.run.share.modle

class WCShareBean {

    var wc_key: String? = null
    var wc_value: String? = null

    constructor() {}

    constructor(wc_key: String, wc_value: String) {
        this.wc_key = wc_key
        this.wc_value = wc_value
    }
}
