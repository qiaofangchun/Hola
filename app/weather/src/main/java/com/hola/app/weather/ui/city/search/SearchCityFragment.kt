package com.hola.app.weather.ui.city.search

import androidx.fragment.app.viewModels
import com.hola.app.weather.R
import com.hola.app.weather.databinding.FragmentSearchCityBinding.bind
import com.hola.base.fragment.BaseFragment
import com.hola.ext.viewBinding

class SearchCityFragment : BaseFragment(R.layout.fragment_search_city) {
    private val view by viewBinding(::bind)
    private val model by viewModels<SearchCityViewModel>()

    override fun initWithView() {
        view.btnSearch.setOnClickListener {
            model.searchPlace(view.address.toString())
        }
    }

    override fun initWithData() {
        model.getPlaces()
    }
}