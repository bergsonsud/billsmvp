package com.example.billsmvp.scenes.despesas.list

import com.example.billsmvp.models.Despesa

interface ListDespesaContract {
    interface View {
        fun setupAdapter()
        fun fillAdapter()
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
    interface Presenter {
        fun getAll(completion: (success: Boolean, list : List<Despesa>) -> Unit)
    }
}