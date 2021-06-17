package com.nac.srs_icar_v1

import android.content.Context
import android.net.ConnectivityManager
import java.util.regex.Pattern

class CommonUtils {
    val isInternetAvailable: Boolean
        get() {
            val cm = App.instance!!.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return ni != null && ni.isConnectedOrConnecting
        }

    fun clearPref(key: String?) {
        val pref = App.instance!!.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        pref.edit().remove(key).apply()
    }

    fun clearPref(vararg keys: String?) {
        val pref = App.instance!!.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val edt = pref.edit()
        for (key in keys) {
            edt.remove(key)
        }
        edt.apply()
    }

    fun getPref(key: String?, isDelete: Boolean): String? {
        val pref = App.instance!!.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val vl = pref.getString(key, null)
        if (vl != null && isDelete) {
            pref.edit().remove(key).apply()
        }
        return vl
    }

    fun getPref(key: String?): String? {
        return getPref(key, false)
    }

    fun savePref(key: String?, value: String?) {
        val pref = App.instance!!.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        pref.edit().putString(key, value).apply()
    }

    fun isEmail(email: String?): Boolean {
        val pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.find()
    }

    fun isPhone(phone: String): Boolean {
        return phone.matches("^(09|03|07|08|05)\\d{8}$")
    }

    companion object {
        private const val PREF_FILE = "file_pref"
        var instance: CommonUtils? = null
            get() {
                if (field == null) {
                    field = CommonUtils()
                }
                return field
            }
            private set
    }
}

private fun String.matches(regex: String): Boolean {
    TODO("Not yet implemented")
}
