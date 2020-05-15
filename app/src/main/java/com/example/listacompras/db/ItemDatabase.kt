package com.example.listacompras.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract val itemDAO : ItemDAO

    companion object{

        @Volatile
        private  var INSTANCE : ItemDatabase? = null

            fun getInstance(context: Context):ItemDatabase{
                synchronized(this){
                    var instance:ItemDatabase? = INSTANCE
                        if(instance == null){
                            instance = Room.databaseBuilder(
                                context.applicationContext,
                                ItemDatabase::class.java,
                                "item_data_database"
                            ).build()
                        }
                    return instance
                }
            }
    }

}