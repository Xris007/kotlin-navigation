package com.noblecilla.listmaker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.listmaker.databinding.LayoutListBinding
import com.noblecilla.listmaker.model.ListEntity

class ListAdapter(private var list: List<ListEntity>, private val listener: (ListEntity) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position, listener)
    }

    fun update(list: List<ListEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listEntity: ListEntity, position: Int, listener: (ListEntity) -> Unit) =
            with(itemView) {
                binding.listPosition.text = (position + 1).toString()
                binding.listName.text = listEntity.name

                setOnClickListener { listener(listEntity) }
            }
    }
}
