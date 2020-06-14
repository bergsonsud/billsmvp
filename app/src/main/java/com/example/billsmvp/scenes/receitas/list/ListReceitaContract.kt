package com.example.billsmvp.scenes.receitas.list

import com.example.billsmvp.models.Receita

interface ListReceitaContract {
    interface View {
        fun setupAdapter()
        fun fillAdapter()
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
    interface Presenter {
        fun getAll(completion: (success: Boolean, list : List<Receita>) -> Unit)
    }
}