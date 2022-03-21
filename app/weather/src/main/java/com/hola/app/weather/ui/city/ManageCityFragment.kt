package com.hola.app.weather.ui.city

import androidx.fragment.app.viewModels
import com.hola.app.weather.R
import com.hola.app.weather.databinding.FragmentManageCityBinding.bind
import com.hola.base.fragment.BaseFragment
import com.hola.ext.viewBinding

class ManageCityFragment : BaseFragment(R.layout.fragment_manage_city) {
    private val view by viewBinding(::bind)
    private val model by viewModels<ManageCityViewModel>()

    override fun initWithView() {
        view.btnSearch.setOnClickListener {
            model.searchPlace(view.address.toString())
        }
    }

    override fun initWithData() {

    }
}