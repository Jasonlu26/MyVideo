package com.yx.play.net
/**
 * @description
 *
 * @time 2021/5/17 9:03 下午
 * @version
 */
enum class TmmError(var code: Int, var message: String) {

    ERROR_UID_EMPTY(102, "UID can not be empty"),
    ERROR_OTHER_EXCEPTION(188, "UID can not be empty"),
    ERROR_COMMON(500, "unknown"),
    ERROR_TOKEN(401, ""),
    ERROR_SERVER(400, ""),
    ERROR_USER_DELETE(600, ""),
    ERROR_NETWORK(999, "net error");

    companion object {
        fun getDec(code:Int):String {
            return when (code) {
                ERROR_UID_EMPTY.code -> {
                    ERROR_UID_EMPTY.message
                }

                ERROR_OTHER_EXCEPTION.code -> {
                    ERROR_OTHER_EXCEPTION.message
                }

                ERROR_COMMON.code -> {
                    ERROR_COMMON.message
                }
                ERROR_TOKEN.code -> {
                    ERROR_TOKEN.message
                }

                ERROR_NETWORK.code -> {
                    ERROR_NETWORK.message
                }
                else -> {
                    ERROR_COMMON.message
                }
            }
        }
    }



}