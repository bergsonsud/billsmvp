package com.example.billsmvp.scenes.receitas.create

import android.net.Uri
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Receita
import com.google.firebase.Timestamp

class CreateReceitaPresenter : CreateReceitaContract.Presenter {
    lateinit var view : CreateReceitaContract.View


    fun uploadAnexo(uriAnexo: Uri, userId: String, id: String) {
        view.upload(uriAnexo,userId,id)
    }

    override fun create(valor: Float, descricao: String, data: Timestamp, recebido: Boolean, uriAnexo: Uri?, filePath : String?) {
        val id = ObjectFirebaseInstance.db.collection("receitas").document().id
        val userId = ObjectFirebaseInstance.getUId().toString()

        uriAnexo?.let {
            uploadAnexo(it,userId,id)
        }

        val receita = Receita(id,userId,valor,descricao,data,recebido, filePath.toString())
        ObjectFirebaseInstance.create("receitas",id,receita) {
            if (it){
                view.showMessage("Receita criada")
            }
            else {
                view.showMessage("Erro ao criar receita")
            }
        }
    }

    override fun update(
        id: String,
        userId: String,
        valor: Float,
        descricao: String,
        data: Timestamp,
        recebido: Boolean,
        uriAnexo: Uri?,
        filePath: String?)
        {
        val userId = ObjectFirebaseInstance.getUId().toString()
        uriAnexo?.let {
            uploadAnexo(it,userId,id)
        }
        val receita = Receita(id,userId,valor,descricao,data,recebido,filePath.toString())
        ObjectFirebaseInstance.update("receitas",receita,id){
            if (it){
                view.showMessage("Receita atualizada")
            }
            else {
                view.showMessage("Erro ao atualizar receita")
            }
        }

    }
}