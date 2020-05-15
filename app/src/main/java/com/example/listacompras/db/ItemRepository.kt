package com.example.listacompras.db

class ItemRepository(private val dao : ItemDAO) {

    val items = dao.getAllItems()

    suspend fun insert(item: Item):Long{
        return dao.insertItem(item)
    }

    suspend fun update(item: Item): Int{
        return dao.updateItem(item)
    }

    suspend fun delete(item: Item): Int{
        return dao.deleteItem(item)
    }

    suspend fun deleteAll(): Int{
        return dao.deleteAll()
    }

}