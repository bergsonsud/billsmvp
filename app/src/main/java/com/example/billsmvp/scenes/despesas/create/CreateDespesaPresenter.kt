package com.example.billsmvp.scenes.despesas.create

import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Despesa
import com.google.firebase.Timestamp

class CreateDespesaPresenter : CreateDespesaContract.Presenter {

    lateinit var view: CreateDespesaContract.View
    override fun create(valor: Float, descricao: String, data: Timestamp, pago: Boolean) {
        val id = ObjectFirebaseInstance.db.collection("despesas").document().id
        val user_id = ObjectFirebaseInstance.getUId().toString()
        val despesa = Despesa(id,user_id,valor,descricao,data,pago)
        ObjectFirebaseInstance.create("despesas",id,despesa) {
            if (it){
                view.showMessage("Despesa criada")
            }
            else {
                view.showMessage("Erro ao criar despesa")
            }
        }
    }

    override fun update(id : String,user_id : String, valor: Float, descricao: String, data: Timestamp, pago: Boolean) {
        val despesa = Despesa(id,user_id,valor,descricao,data,pago)
        ObjectFirebaseInstance.update("despesas",despesa,id){
            if (it){
                view.showMessage("Despesa atualizada")
            }
            else {
                view.showMessage("Erro ao atualizar despesa")
            }
        }
    }
}