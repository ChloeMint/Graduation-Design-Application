package com.example.greenplant.entities

class Msg(val msg:String, val type:Int){
    companion object{
        const val RECEIVE = 0
        const val SEND = 1
    }
}