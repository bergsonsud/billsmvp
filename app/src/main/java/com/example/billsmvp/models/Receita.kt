package com.example.billsmvp.models

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

class Receita(id: String,user_id: String ,valor : Float, descricao : String,data : Timestamp,
              var recebido : Boolean): Transacao(id, user_id,valor, descricao, data), Serializable {

    constructor() : this("","",0F, "", Timestamp(Date()),false) {

    }
}