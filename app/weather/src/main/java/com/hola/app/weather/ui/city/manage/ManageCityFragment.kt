package com.hola.app.weather.ui.city.manage

import androidx.fragment.app.viewModels
import com.hola.app.weather.R
import com.hola.app.weather.databinding.FragmentManageCityBinding.bind
import com.hola.base.fragment.BaseFragment
import com.hola.viewbind.viewBinding

class ManageCityFragment : BaseFragment(R.layout.fragment_manage_city) {
    private val view by viewBinding(::bind)
    private val model by viewModels<ManageCityViewModel>()

    override fun initWithView() {

    }

    override fun initWithData() {
        model.getPlaces()
    }
}