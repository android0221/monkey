package  com.run.config.modle

import java.io.Serializable


/**
 * 返回的工具类
 */
open class BaseModle : Serializable {
    var status: Int = 0 //返回的状态 200表示成功  -100失败转态
    open var msg: String? = null //提示信息
    open var code: Int = 0   //具体的失败信息

    open  var more: Boolean = false
    open  var success: Boolean = false
}