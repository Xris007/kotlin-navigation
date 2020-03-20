package com.noblecilla.listmaker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.listmaker.databinding.LayoutItemBinding
import com.noblecilla.listmaker.model.ListEntity

class ItemAdapter(private var listEntity: ListEntity) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listEntity.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEntity.items[position])
    }

    fun update(listEntity: ListEntity) {
        this.listEntity = listEntity
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(itemView) {
            binding.itemIndicator.text = "-"
            binding.itemName.text = item
        }
    }
}
