package com.example.listacompras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.databinding.ListItemBinding
import com.example.listacompras.db.Item

class MyRecyclerViewAdapter(
    private val clickListener:(Item)->Unit) : RecyclerView.Adapter<MyViewHolder>(){

    private val itemsList = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemsList[position], clickListener)
    }

    fun setList(items: List<Item>){
        itemsList.clear()
        itemsList.addAll(items)
    }


}


class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Item, clickListener: (Item) -> Unit){
        binding.nameTextView.text = item.name
        binding.listItemLayout.setOnClickListener {
            clickListener(item)
        }
    }
}