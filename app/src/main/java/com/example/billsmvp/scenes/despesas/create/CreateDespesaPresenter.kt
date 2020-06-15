package com.example.billsmvp.scenes.despesas.create

import android.net.Uri
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.google.firebase.Timestamp

class CreateDespesaPresenter : CreateDespesaContract.Presenter {

    lateinit var view: CreateDespesaContract.View


    fun uploadAnexo(uriAnexo: Uri, userId: String, id: String) {
        view.upload(uriAnexo,userId,id)
    }


    override fun create(valor: Float, descricao: String, data: Timestamp, pago: Boolean, uriAnexo: Uri?, filePath : String?) {
        var id = ObjectFirebaseInstance.db.collection("despesas").document().id
        val userId = ObjectFirebaseInstance.getUId().toString()

            uriAnexo?.let {
                uploadAnexo(it,userId,id)
            }



        val despesa = Despesa(id,userId,valor,descricao,data,pago, filePath.toString())
        ObjectFirebaseInstance.create("despesas",id,despesa) {
            if (it){
                view.showMessage("Despesa criada")
            }
            else {
                view.showMessage("Erro ao criar despesa")
            }
        }
    }

    override fun update(id : String, userId : String, valor: Float, descricao: String, data: Timestamp, pago: Boolean, uriAnexo: Uri?, filePath : String?) {
        val userId = ObjectFirebaseInstance.getUId().toString()

        uriAnexo?.let {
            uploadAnexo(it,userId,id)
        }

        val despesa = Despesa(id,userId,valor,descricao,data,pago, filePath.toString())
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