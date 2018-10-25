package com.run.common.utils

import java.text.DecimalFormat

object UType {

    //===============================工具类==========================================================
    public fun doubleToString(num: Double): String {
        //使用0.00不足位补0，#.##仅保留有效位
        return DecimalFormat("0.00").format(num)
    }
}