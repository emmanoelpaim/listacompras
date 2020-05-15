package com.example.listacompras.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_data_table")
data class Item (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="item_id")
    var id: Int,

    @ColumnInfo(name="item_name")
    var name: String
)