package com.practice.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.retrofit.databinding.ItemCountryBinding
import com.practice.newsapp.adapter.CountryAdapter.ViewHolder
import com.practice.retrofit.models.Country
import com.practice.retrofit.util.loadImage

class CountryAdapter(private var list: ArrayList<Country>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var binding: ItemCountryBinding

    inner class ViewHolder(binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.name.text = country.countryName
            binding.capital.text = country.capital
            binding.imageView.loadImage(country.flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCountryBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateCountries(newCountries: List<Country>) {
        list.clear()
        list.addAll(newCountries)
        notifyDataSetChanged()
    }
}
