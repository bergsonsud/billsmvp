package com.example.billsmvp.scenes.receitas.create

import com.google.firebase.Timestamp

interface CreateReceitaContract {
    interface View {
        fun fillData()
        fun showMessage(message : String)
        fun setupButtonAdd()
    }

    interface Presenter {
        fun create(valor: Float, descricao: String, data: Timestamp, recebido: Boolean)
        fun update(id : String, user_id : String, valor: Float, descricao: String, data: Timestamp, recebido: Boolean)
    }
}