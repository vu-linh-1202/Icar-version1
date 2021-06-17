package com.nac.sampleapp.view.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseModel<T> : Serializable {
    @SerializedName("success")
    var isStatus = false

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }

    override fun toString(): String {
        return "BaseModel{" +
                "status=" + isStatus +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }
}