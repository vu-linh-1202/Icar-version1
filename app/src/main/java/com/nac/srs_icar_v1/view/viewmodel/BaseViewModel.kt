package com.nac.sampleapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.nac.sampleapp.view.api.model.BaseModel
import com.nac.srs_icar_v1.App
import com.nac.srs_icar_v1.CommonUtils
import com.nac.srs_icar_v1.Storage
import com.nac.srs_icar_v1.view.ProgressLoading
import com.nac.srs_icar_v1.view.callback.OnActionCallBack
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession

abstract class BaseViewModel : ViewModel() {
    val ex = MutableLiveData(false)
    protected var mCallBack: OnActionCallBack? = null
    protected fun <T> initResponse(key: String?): retrofit2.Callback<T> {
        return object : retrofit2.Callback<T> {
            override fun onResponse(call: retrofit2.Call<T>, rs: Response<T>) {
                ProgressLoading.dismiss()
                if (rs.code() == CODE_200 || rs.code() == CODE_201) {
                    handleSuccess(key, rs.body())
                } else {
                    errorReport(key, rs.code(), rs.errorBody())
                    ex.setValue(true)
                }
            }

            override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
                notifyToView("Lỗi hệ thống, thử lại sau!!!")
                Log.i(TAG, "onFailure..." + t.message)
                ProgressLoading.dismiss()
                ex.value = true
            }
        }
    }

    protected fun errorReport(key: String?, code: Int, errorBody: ResponseBody?) {
        Log.i("TAG", "errorReport: $code")
        try {
            var err: BaseModel<*>? = null
            try {
                err = Gson().fromJson(errorBody!!.string(), BaseModel::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            when (code) {
                CODE_400 -> if (errorBody != null) {
                    notifyToView(if (err != null) err.message else errorBody.string())
                }
                CODE_401 -> {
                    notifyToView("Thông tin đăng nhập sai hoặc tài khoản đã hết hạn, hãy thử đăng nhập lại!")
                    mCallBack!!.callBack(KEY_LOGOUT, null)
                    mCallBack!!.logout()
                }
                CODE_403, CODE_404, CODE_500 -> {
                    Log.d("TAG", "errorReport: " + errorBody.toString())
                    notifyToView("Lỗi hệ thống, thử lại sau!!!")
                }
                else -> {
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mCallBack!!.callBack(KEY_ERROR, code)
    }

    protected fun handleSuccess(key: String?, value: Any?) {
        mCallBack!!.callBack(key, value)
    }

    protected val wS: Retrofit
        protected get() {
            if (!CommonUtils.instance?.isInternetAvailable!!) {
                ex.value = true
            }
            val client = OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier { hostname: String?, session: SSLSession? -> true }
                .readTimeout(30, TimeUnit.SECONDS).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    protected fun notifyToView(msg: String?) {
        mCallBack!!.callBack(KEY_NOTIFY, msg)
    }

    fun setCallBack(mCallBack: OnActionCallBack?) {
        this.mCallBack = mCallBack
    }

    val token: String?
        get() = CommonUtils.instance?.getPref(TOKEN)
    val storage: Storage?
        get() = App.instance?.storage

    companion object {
        const val KEY_NOTIFY = "KEY_NOTIFY"
        const val KEY_LOGOUT = "KEY_LOGOUT"
        const val KEY_ERROR = "KEY_ERROR"
        protected const val CODE_400 = 400
        protected const val CODE_401 = 401
        protected const val CODE_403 = 403
        protected const val CODE_404 = 404
        protected const val CODE_500 = 500
        protected const val CODE_200 = 200
        protected const val CODE_201 = 201
        private val TAG = BaseViewModel::class.java.name
        private const val TOKEN = "TOKEN"
        var BASE_URL = "https:......../"
    }
}