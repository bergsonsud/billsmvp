package com.example.billsmvp.scenes.receitas.list

import com.example.billsmvp.models.Receita
import com.example.billsmvp.models.TransacaoDate
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.util.formataParaReal
import java.util.*

class ListReceitaPresenter : ListReceitaContract.Presenter {
    lateinit var view : ListReceitaContract.View
    var receitas : List<Receita> = arrayListOf()
    lateinit var groups : Map<Int,List<Receita>>
    var lista : MutableList<Any> = ArrayList()

    override fun getAll(completion: (success: Boolean, list: List<Receita>) -> Unit) {
        ObjectFirebaseInstance.getCurrentMonthOrded("receitas", Receita::class.java) { success, list ->
            if (success) {
                completion(true, list)
            }
        }
    }

    fun setTotalReceitas() : String {
        return receitas.map { it.valor }.sum().formataParaReal()
    }


    fun group() {
        val calendar = GregorianCalendar.getInstance()
        groups = receitas.groupBy { item ->
            val date = item.data.toDate()
            calendar.setTime(date)
            calendar.get(Calendar.DATE)
        }
    }

    fun foreachMapLista() {
        lista.clear()
        groups.forEach{ (key, value) ->
            val transacaoDate = TransacaoDate(key)
            lista.add(transacaoDate)
            value.forEach{
                lista.add(it)
            }

        }
    }
}