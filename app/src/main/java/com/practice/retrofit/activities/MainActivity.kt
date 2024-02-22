package com.practice.retrofit.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.retrofit.databinding.ActivityMainBinding
import com.practice.retrofit.adapter.CountryAdapter
import com.practice.retrofit.util.ResultOf
import com.practice.retrofit.viewModel.CountryModel

class MainActivity : AppCompatActivity() {
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CountryModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CountryModel::class.java]
        viewModel.refresh()

        binding.countriesList.layoutManager = LinearLayoutManager(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { result ->
            when (result) {
                is ResultOf.Failure -> {
                    binding.listError.visibility = View.VISIBLE
                    binding.loadingView.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }

                is ResultOf.Loading -> {
                    binding.listError.visibility = View.GONE
                    binding.loadingView.visibility = View.VISIBLE
                    binding.countriesList.visibility = View.GONE
                }

                is ResultOf.Success -> {
                    binding.listError.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.countriesList.visibility = View.VISIBLE
                    countryAdapter = CountryAdapter(result.value, this)
                    binding.countriesList.setHasFixedSize(true)
                    binding.countriesList.adapter = countryAdapter
                }

            }

        })
    }
}