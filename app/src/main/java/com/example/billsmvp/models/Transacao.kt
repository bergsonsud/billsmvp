package com.example.billsmvp.models

import com.google.firebase.Timestamp
import java.util.*


open class Transacao(var id: String, var userId: String, var valor: Float, var descricao: String, var data: Timestamp, var type : String) {


    constructor() : this("", "", 0F, "", Timestamp(Date()), "")

    class TYPE {
        companion object {
            val DATE_TIMESTAMP = "data"
            val TRANSACAO = "transacao"
        }
    }



}