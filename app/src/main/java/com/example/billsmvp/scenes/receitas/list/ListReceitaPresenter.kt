package com.example.billsmvp.scenes.receitas.list

import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Receita

class ListReceitaPresenter : ListReceitaContract.Presenter {
    lateinit var view : ListReceitaContract.View
    override fun getAll(completion: (success: Boolean, list: List<Receita>) -> Unit) {
        ObjectFirebaseInstance.getAll("receitas", Receita::class.java) { success, list ->
            if (success) {
                completion(true, list)
            }
        }
    }
}