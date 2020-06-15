package com.example.billsmvp.scenes.receitas.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billsmvp.R
import com.example.billsmvp.base.TransacaoInterface
import com.example.billsmvp.models.Transacao
import com.example.billsmvp.models.TransacaoDate
import com.example.billsmvp.util.formataParaReal
import kotlinx.android.synthetic.main.fragment_list_receita_date.view.*
import kotlinx.android.synthetic.main.fragment_list_receita_row.view.*

class ListReceitaDayAdapter(var transacaoInterface : TransacaoInterface, val context: Context,
                            var lista: MutableList<Any>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val DATE_TIMESTAMP = 0
        val TRANSACAO = 1
    }


    override fun getItemViewType(position: Int): Int {

        val type = when (lista[position].javaClass.simpleName) {
            "TransacaoDate" -> DATE_TIMESTAMP
            else -> TRANSACAO
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dayView = LayoutInflater.from(context).inflate(R.layout.fragment_list_receita_date,parent,false)
        val transacaoView = LayoutInflater.from(context).inflate(R.layout.fragment_list_receita_row,parent,false)

        val viewHolder : RecyclerView.ViewHolder = when (viewType) {
            0 -> return RecyclerViewDateViewHolder(dayView)
            else ->  return RecyclerTransacaoViewHolder(transacaoView)
        }

    }

    override fun getItemCount(): Int {
        return lista.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType==0) {
            holder.itemView.dataReceita.text = "Dia "+(lista[position] as TransacaoDate).day.toString()

        }else{
            var transacao = (lista[position] as Transacao)
            holder.itemView.descricaoReceita.text = (lista[position] as Transacao).descricao
            holder.itemView.valorReceita.text = (lista[position] as Transacao).valor.formataParaReal()

            holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    transacaoInterface.onItemLongClick(position, transacao)
                    return true
                }

            })
        }

    }


    inner class RecyclerViewDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener{
        override fun onClick(v: View?) {

        }

        override fun onLongClick(v: View?): Boolean {
            return true
        }
    }

    inner class RecyclerTransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}