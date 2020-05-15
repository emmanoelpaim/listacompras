package com.example.listacompras

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacompras.db.Item
import com.example.listacompras.db.ItemRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel(), Observable {

    val items = repository.items
    private var isUpdateOrDelete = false

    private lateinit var  itemToUpdateOrDelete : Item

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }


    fun saveOrUpdate(){

        if(inputName.value == null){
            statusMessage.value = Event("Escolha um nome")
        }
        if(isUpdateOrDelete){
            itemToUpdateOrDelete.name = inputName.value!!
            update(itemToUpdateOrDelete)
        }else{
            val name:String = inputName.value!!

            insert(Item(0,name))

            inputName.value = null
        }

    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(itemToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun insert(item: Item): Job =
        viewModelScope.launch {
            val newRowId:Long =  repository.insert(item)
            if(newRowId>-1){
                statusMessage.value = Event("Item inserido com sucesso $newRowId")
            }else{
                statusMessage.value = Event("Erro ocorreu")
            }
        }

    fun update(item: Item): Job = viewModelScope.launch {
        val noOfRows: Int = repository.update(item)
        if(noOfRows>0){
            inputName.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Limpar Tudo"
            statusMessage.value = Event("$noOfRows Registros Atualizados com sucesso")
        }else{
            statusMessage.value = Event("Erro ocorreu")
        }
    }

    fun delete(item: Item): Job = viewModelScope.launch {
        val noOfRowsDeleted: Int = repository.delete(item)
        if(noOfRowsDeleted>0) {
            inputName.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Limpar Tudo"
            statusMessage.value = Event("Item Deletado com sucesso")
        }else{
            statusMessage.value = Event("Erro ocorreu")
        }
    }

    fun clearAll(): Job = viewModelScope.launch {
        val noOfRowsDeleted: Int = repository.deleteAll()
        if(noOfRowsDeleted>0) {
            statusMessage.value = Event("Todos items deletados com sucesso")
        }else{
            statusMessage.value = Event("Erro ocorreu")
        }
    }

    fun initUpdateAndDelete(item: Item){
        inputName.value = item.name
        isUpdateOrDelete = true
        itemToUpdateOrDelete = item
        saveOrUpdateButtonText.value = "Atualizar"
        clearAllOrDeleteButtonText.value = "Excluir"

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}