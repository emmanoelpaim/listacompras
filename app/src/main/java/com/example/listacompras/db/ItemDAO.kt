package com.example.listacompras.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDAO {

    @Insert
    suspend fun insertItem(item: Item): Long

    @Update
    suspend fun updateItem(item: Item): Int

    @Delete
    suspend fun deleteItem(item: Item): Int

    @Query("DELETE FROM item_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM item_data_table")
    fun getAllItems():LiveData<List<Item>>

}