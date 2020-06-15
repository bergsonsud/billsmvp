package com.example.billsmvp.scenes.despesas.list

import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.models.TransacaoDate
import com.example.billsmvp.util.formataParaReal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class ListDespesaPresenter : ListDespesaContract.Presenter {
    lateinit var view : ListDespesaContract.View
    var despesas : List<Despesa> = arrayListOf()
    lateinit var groups : Map<Int,List<Despesa>>
    var lista : MutableList<Any> = ArrayList()


    override fun getAll(completion: (success: Boolean, list : List<Despesa>) -> Unit) {
        ObjectFirebaseInstance.getCurrentMonthOrded("despesas", Despesa::class.java) {success, list ->
            if (success) {
                completion(true, list)
            }
        }
    }

    fun setTotalDespesas() : String {
        return despesas.map { it.valor }.sum().formataParaReal()
    }

    fun group() {
        val calendar = GregorianCalendar.getInstance()
        groups = despesas.groupBy { item ->
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