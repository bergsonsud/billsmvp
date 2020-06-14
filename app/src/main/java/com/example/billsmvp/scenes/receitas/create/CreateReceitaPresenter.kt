package com.example.billsmvp.scenes.receitas.create

import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Receita
import com.google.firebase.Timestamp

class CreateReceitaPresenter : CreateReceitaContract.Presenter {
    lateinit var view : CreateReceitaContract.View
    override fun create(valor: Float, descricao: String, data: Timestamp, recebido: Boolean) {
        val id = ObjectFirebaseInstance.db.collection("receitas").document().id
        val user_id = ObjectFirebaseInstance.getUId().toString()
        val receita = Receita(id,user_id,valor,descricao,data,recebido)
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
        user_id: String,
        valor: Float,
        descricao: String,
        data: Timestamp,
        recebido: Boolean
    ) {
        val receita = Receita(id,user_id,valor,descricao,data,recebido)
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