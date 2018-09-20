package  com.run.config.modle

class BaseRxBean<T> : BaseModle {

    var type: Int = 0
    override var code: Int = 0
    var data: T? = null
    override var msg: String? = null

    constructor(type: Int, code: Int) {
        this.type = type
        this.code = code
    }

    constructor(type: Int, code: Int, data: T) {
        this.type = type
        this.code = code
        this.data = data
    }

    constructor(type: Int, code: Int, msg: String) {
        this.type = type
        this.code = code
        this.msg = msg
    }

    constructor()
}
