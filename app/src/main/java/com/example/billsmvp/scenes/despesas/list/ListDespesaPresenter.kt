package com.example.billsmvp.scenes.despesas.list

import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Despesa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ListDespesaPresenter : ListDespesaContract.Presenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    lateinit var view : ListDespesaContract.View

    override fun getAll(completion: (success: Boolean, list : List<Despesa>) -> Unit) {
        ObjectFirebaseInstance.getAll("despesas", Despesa::class.java) {success, list ->
            if (success) {
                completion(true, list)
            }
        }
    }

}