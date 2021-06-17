package com.nac.sampleapp.view.fragment

import android.view.View
import com.nac.sampleapp.view.viewmodel.M001LoginViewModel
import com.nac.srs_icar_v1.databinding.FrgM001MenuBinding
import com.nac.srs_icar_v1.view.fragment.BaseFragment

class M001LoginFrg : BaseFragment<FrgM001MenuBinding?, M001LoginViewModel>() {
    override fun initBinding(mRootView: View?): FrgM001MenuBinding? {
        return mRootView?.let { FrgM001MenuBinding.bind(it) }
    }

    override val viewModelClass: Class<M001LoginViewModel>
        get() = TODO("Not yet implemented")
    override val layoutId: Int
        get() = TODO("Not yet implemented")

    override fun initViews() {}
}