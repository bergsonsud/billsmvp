package com.example.billsmvp.models

import com.google.firebase.Timestamp
import java.util.*


open class Transacao(var id: String,var user_id: String, var valor: Float,var descricao: String,var data: Timestamp) {


    constructor() : this("", "", 0F, "", Timestamp(Date()))



}