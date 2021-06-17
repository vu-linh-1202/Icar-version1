package com.nac.srs_icar_v1.view.callback

interface OnActionCallBack {
    fun callBack(key: String?, data: Any?) {}
    fun logout() {}
}