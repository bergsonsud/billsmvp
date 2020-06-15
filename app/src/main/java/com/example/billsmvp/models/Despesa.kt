package com.example.billsmvp.models

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

class Despesa(id: String, userId: String, valor : Float, descricao : String, data : Timestamp,
              var pago : Boolean, var filePath : String): Transacao(id, userId,valor, descricao, data, TYPE.TRANSACAO), Serializable {

    constructor() : this("","",0F, "", Timestamp(Date()),false, "") {

    }

}