package com.nac.srs_icar_v1.view

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.Window
import android.widget.Toast
import com.nac.srs_icar_v1.CommonUtils
import com.nac.srs_icar_v1.R

object ProgressLoading {
    private var pdLoading: Dialog? = null
    private var isHide = false
    fun donShow() {
        isHide = true
    }

    fun show(context: Context?) {
        if (!CommonUtils.instance?.isInternetAvailable!!) {
            Toast.makeText(
                context,
                context!!.resources.getString(R.string.unavailable_internet),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (!isLoading && context != null && !isHide) {
            try {
                if (pdLoading == null) {
                    pdLoading = Dialog(context)
                    pdLoading!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    pdLoading!!.setContentView(R.layout.progress_loading)
                    if (pdLoading!!.window != null) {
                        pdLoading!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                    }
                    pdLoading!!.setCanceledOnTouchOutside(false)
                    pdLoading!!.window!!.setGravity(Gravity.CENTER)
                    pdLoading!!.setCancelable(false)
                }
                pdLoading!!.show()
            } catch (ignored: Exception) {
                //ignored.printStackTrace();
            }
        }
        isHide = false
    }

    fun dismiss() {
        if (pdLoading != null && pdLoading!!.isShowing) {
            Handler().postDelayed({
                try {
                    if (pdLoading != null && pdLoading!!.isShowing) {
                        pdLoading!!.dismiss()
                        pdLoading = null
                    }
                } catch (ignored: Exception) {
                    //ignored.printStackTrace();
                }
            }, 800)
        }
    }

    private val isLoading: Boolean
        private get() = pdLoading != null && pdLoading!!.isShowing
}