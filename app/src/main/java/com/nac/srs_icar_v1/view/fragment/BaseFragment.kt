package com.nac.srs_icar_v1.view.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nac.sampleapp.view.viewmodel.BaseViewModel
import com.nac.srs_icar_v1.view.callback.OnAlertCallBack
import com.nac.srs_icar_v1.view.callback.OnHomeCallBack

abstract class BaseFragment<K : ViewBinding?, V : BaseViewModel?> : Fragment(),
    View.OnClickListener {
    protected var mContext: Context? = null
    protected var mRootView: View? = null
    private var callBack: OnHomeCallBack? = null
    protected var mData: Any? = null
    protected var mViewModel: V? = null
    protected var binding: K? = null
    fun setCallBack(callBack: OnHomeCallBack?) {
        this.callBack = callBack
    }

    fun <T : View?> findViewById(id: Int): T? {
        return findViewById(id, null)
    }

    fun <T : View?> findViewById(id: Int, event: View.OnClickListener?): T? {
        val v: T = mRootView!!.findViewById(id)
        if (v != null && event != null) {
            v.setOnClickListener(event)
        }
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(layoutId, container, false)
        mViewModel = ViewModelProvider(this).get(viewModelClass)
        binding = initBinding(mRootView)
        initViews()
        return mRootView
    }

    protected abstract fun initBinding(mRootView: View?): K
    protected abstract val viewModelClass: Class<V>
    protected abstract val layoutId: Int
    protected abstract fun initViews()
    override fun onClick(v: View) {
        //do nothing
    }

    fun setData(data: Any?) {
        mData = data
    }

    protected fun showAlert(
        title: String?,
        msg: String?,
        textBt1: String?,
        textBt2: String?,
        callBack: OnAlertCallBack?
    ) {
        val alert = AlertDialog.Builder(
            mContext!!
        ).create()
        alert.setTitle(title)
        //alert.setIcon(R.drawable.ic_warn);
        alert.setMessage(msg)
        alert.setCanceledOnTouchOutside(false)
        alert.setCancelable(false)
        if (textBt1 != null) {
            alert.setButton(
                AlertDialog.BUTTON_POSITIVE,
                textBt1, DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                    if (callBack != null) {
                        callBack.callBack(AlertDialog.BUTTON_POSITIVE, null)
                    }
                })
        }
        if (textBt2 != null) {
            alert.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                textBt2, DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                    if (callBack != null) {
                        callBack.callBack(AlertDialog.BUTTON_NEGATIVE, null)
                    }
                })
        }
        alert.show()
    }
}




