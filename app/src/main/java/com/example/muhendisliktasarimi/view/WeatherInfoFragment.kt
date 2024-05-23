package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentWeatherInfoBinding
import com.example.muhendisliktasarimi.viewmodel.WeatherViewModel


class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info), SearchView.OnQueryTextListener {

    private lateinit var _fragmentBinding: FragmentWeatherInfoBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_weather_info,container,false)

        (activity as AppCompatActivity).setSupportActionBar(_fragmentBinding.toolbarTools)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_search,menu)
                val item = menu.findItem(R.id.action_search).actionView as SearchView
                item.queryHint = "Şehir ismi"
                item.setOnQueryTextListener(this@WeatherInfoFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
        return _fragmentBinding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        getData()
        _fragmentBinding.swipeRefreshLayout.setOnRefreshListener {
            _fragmentBinding.progressBar.visibility = View.VISIBLE
            _fragmentBinding.textViewErr.visibility = View.GONE
            _fragmentBinding.textViewNoInternet.visibility = View.GONE
            _fragmentBinding.cardTitle.visibility = View.GONE
            _fragmentBinding.cardDesc.visibility = View.GONE
            _fragmentBinding.cardDegree.visibility = View.GONE
            getData()
            _fragmentBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getData(){
        viewModel.state.observe(viewLifecycleOwner, Observer {

            it?.let { data ->
                _fragmentBinding.let {
                    if (data.isLoading){
                        _fragmentBinding.progressBar.visibility = View.VISIBLE
                        _fragmentBinding.card.visibility = View.VISIBLE
                        _fragmentBinding.cardTitle.visibility = View.GONE
                        _fragmentBinding.cardDegree.visibility = View.GONE
                        _fragmentBinding.cardDesc.visibility = View.GONE
                        _fragmentBinding.textViewErr.visibility = View.GONE
                        _fragmentBinding.textViewNoInternet.visibility = View.GONE
                    }else if (data.error == "No internet connection"){
                        _fragmentBinding.textViewErr.visibility = View.GONE
                        _fragmentBinding.textViewNoInternet.visibility = View.VISIBLE
                        _fragmentBinding.card.visibility = View.GONE
                        _fragmentBinding.progressBar.visibility = View.GONE
                        _fragmentBinding.cardTitle.visibility = View.GONE
                        _fragmentBinding.cardDesc.visibility = View.GONE
                        _fragmentBinding.cardDegree.visibility = View.GONE

                    }else if (data.error == "Error"){
                        _fragmentBinding.textViewErr.visibility = View.VISIBLE
                        _fragmentBinding.textViewNoInternet.visibility = View.GONE
                        _fragmentBinding.card.visibility = View.GONE
                        _fragmentBinding.progressBar.visibility = View.GONE
                    }
                    else{
                        _fragmentBinding.progressBar.visibility = View.GONE
                        _fragmentBinding.textViewErr.visibility = View.GONE
                        _fragmentBinding.textViewNoInternet.visibility = View.GONE
                        _fragmentBinding.card.visibility = View.VISIBLE
                        _fragmentBinding.cardTitle.visibility = View.VISIBLE
                        _fragmentBinding.cardDegree.visibility = View.VISIBLE
                        _fragmentBinding.cardDesc.visibility = View.VISIBLE
                        _fragmentBinding.title.text = "Şehir: ${data.weather?.name}"
                        _fragmentBinding.degree.text = "Hava sıcaklığı: ${data.weather?.main?.temp.toString().take(2)}°"
                        _fragmentBinding.desc.text = "Hissedilen sıcaklık: ${data.weather?.main?.feels_like.toString().take(2)}°"
                        if (data.weather?.main?.temp!! <= 25 && data.weather.main.temp >=20){
                            _fragmentBinding.anim.setAnimation(R.raw.cloudy)
                            _fragmentBinding.anim.playAnimation()
                            ObjectAnimator.ofFloat(_fragmentBinding.anim,"translationY",5000.0f,0.0f).apply {
                                duration = 500

                            }.start()
                        }else if (data.weather.main.temp < 20 && data.weather.main.temp >= 10){
                            _fragmentBinding.anim.setAnimation(R.raw.anim_cloudy)
                            // şuanki
                            _fragmentBinding.anim.playAnimation()

                        }else if (data.weather.main.temp < 10){
                            _fragmentBinding.anim.setAnimation(R.raw.anim_winter)
                            _fragmentBinding.anim.playAnimation()
                            ObjectAnimator.ofFloat(_fragmentBinding.anim,"translationY",5000.0f,0.0f).apply {
                                duration = 500

                            }.start()
                        }else{
                            _fragmentBinding.anim.setAnimation(R.raw.anim_sunny)
                            _fragmentBinding.anim.playAnimation()
                            ObjectAnimator.ofFloat(_fragmentBinding.anim,"translationY",5000.0f,0.0f).apply {
                                duration = 500

                            }.start()
                        }
                    }
                }

            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            if (query.isNotEmpty())
                viewModel.getWeatherInfo(query.lowercase().trim())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}