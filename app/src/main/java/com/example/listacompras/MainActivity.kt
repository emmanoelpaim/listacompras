package com.example.listacompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompras.databinding.ActivityMainBinding
import com.example.listacompras.db.ItemDatabase
import com.example.listacompras.db.Item
import com.example.listacompras.db.ItemRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var adapter: MyRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao = ItemDatabase.getInstance(application).itemDAO
        val repository = ItemRepository(dao)
        val factory = ItemViewModelFactory(repository)
        itemViewModel = ViewModelProvider(this,factory).get(ItemViewModel::class.java)
        binding.myViewModel = itemViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        itemViewModel.message.observe(this, Observer {
            it.getContentIfNoHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun initRecyclerView(){
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Item->listItemClicked(selectedItem)})
        binding.itemRecyclerView.adapter = adapter
        displayItemList()
    }

    private fun displayItemList(){
        itemViewModel.items.observe(this, Observer{
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    
    private fun listItemClicked(item: Item){
        itemViewModel.initUpdateAndDelete(item)
    }
}
