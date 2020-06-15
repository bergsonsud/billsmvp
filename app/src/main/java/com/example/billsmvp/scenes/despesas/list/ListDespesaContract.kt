package com.example.billsmvp.scenes.despesas.list

import com.example.billsmvp.models.Despesa

interface ListDespesaContract {
    interface View {
        fun setupAdapterDay()
        fun fillAdapter()
    }
    interface Presenter {
        fun getAll(completion: (success: Boolean, list : List<Despesa>) -> Unit)
    }
}