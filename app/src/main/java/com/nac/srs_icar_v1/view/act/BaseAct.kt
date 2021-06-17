package com.nac.srs_icar_v1.view.act

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nac.srs_icar_v1.R
import com.nac.srs_icar_v1.view.callback.OnHomeCallBack
import com.nac.srs_icar_v1.view.fragment.BaseFragment
import org.jetbrains.annotations.Nullable

abstract class BaseAct<V : ViewBinding?> : AppCompatActivity(), View.OnClickListener,
    OnHomeCallBack {
    protected var binding: V? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(rootView)
        binding = initBinding(rootView)
        initViews()
    }

    protected abstract fun initBinding(rootView: View?): V
    protected abstract fun initViews()
    protected abstract val layoutId: Int
    override fun onClick(v: View) {
        //do nothing
    }

    protected fun showNotify(sms: String?) {
        Toast.makeText(this, sms, Toast.LENGTH_SHORT).show()
    }
    @Nullable
    override fun showFrg(className: String?, data: Any?, isBacked: Boolean) {
        try {
            val clazz = Class.forName(className)
            val constructor = clazz.getConstructor()
            val frg = constructor.newInstance() as BaseFragment<*, *>
            frg.setCallBack(this)
            frg.setData(data)
            val trans = supportFragmentManager.beginTransaction()
            if (isBacked) {
                trans.addToBackStack(null)
            }
            trans.replace(R.id.ln_main, frg).commit()
        } catch (e: Exception) {
            showNotify("Err: " + e.message)
            e.printStackTrace()
        }
    }
}