package com.nac.sampleapp.view.fragment

import android.view.View
import com.nac.sampleapp.view.viewmodel.M000ViewModel
import com.nac.srs_icar_v1.databinding.FrgM000SplashBinding
import com.nac.srs_icar_v1.view.fragment.BaseFragment

class M000SplashFrg : BaseFragment<FrgM000SplashBinding?, M000ViewModel>() {
    override fun initBinding(mRootView: View?): FrgM000SplashBinding? {
        return mRootView?.let { FrgM000SplashBinding.bind(it) }
    }

    override val viewModelClass: Class<M000ViewModel>
        get() = TODO("Not yet implemented")
    override val layoutId: Int
        get() = TODO("Not yet implemented")

    override fun initViews() {
        //do something
    }

    companion object {
        val TAG = M000SplashFrg::class.java.name
    }
}