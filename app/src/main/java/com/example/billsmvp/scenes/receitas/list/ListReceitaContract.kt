package com.example.billsmvp.scenes.receitas.list

import com.example.billsmvp.models.Receita

interface ListReceitaContract {
    interface View {
        fun setupAdapterDay()
        fun fillAdapter()
    }
    interface Presenter {
        fun getAll(completion: (success: Boolean, list : List<Receita>) -> Unit)
    }
}