package com.example.billsmvp.scenes.receitas.create

import android.net.Uri
import com.google.firebase.Timestamp

interface CreateReceitaContract {
    interface View {
        fun upload(uriAnexo: Uri, userId : String, id: String)
        fun fillData()
        fun showMessage(message : String)
        fun setupButtonAdd()
    }

    interface Presenter {
        fun create(valor: Float, descricao: String, data: Timestamp, recebido: Boolean, uriAnexo : Uri?, filePath : String?)
        fun update(id : String, userId : String, valor: Float, descricao: String, data: Timestamp, recebido: Boolean, uriAnexo : Uri?, filePath : String?)
    }
}