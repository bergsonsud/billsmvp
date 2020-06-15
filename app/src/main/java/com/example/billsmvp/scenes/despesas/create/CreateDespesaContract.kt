package com.example.billsmvp.scenes.despesas.create

import android.content.Intent
import android.net.Uri
import com.google.firebase.Timestamp

interface CreateDespesaContract {
    interface View {
        fun upload(uriAnexo: Uri, userId : String, id: String)
        fun fillData()
        fun showMessage(message : String)
        fun setupButtonAdd()
    }

    interface Presenter {
        fun create(valor: Float, descricao: String, data: Timestamp, pago: Boolean, uriAnexo : Uri?, fiePath : String?)
        fun update(id : String, userId : String, valor: Float, descricao: String, data: Timestamp, pago: Boolean, uriAnexo : Uri?, fiePath : String?)
    }

}