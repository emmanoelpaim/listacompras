package com.example.listacompras

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
    private set


    fun getContentIfNoHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}