package com.example.billsmvp.scenes.despesas.create

import com.google.firebase.Timestamp

interface CreateDespesaContract {
    interface View {
        fun fillData()
        fun showMessage(message : String)
        fun setupButtonAdd()
    }

    interface Presenter {
        fun create(valor: Float, descricao: String, data: Timestamp, pago: Boolean)
        fun update(id : String,user_id : String,valor: Float, descricao: String, data: Timestamp, pago: Boolean)
    }

}