package com.example.billsmvp.scenes.despesas.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billsmvp.R
import com.example.billsmvp.models.Despesa
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_list_despesas_row.view.*

class ListDespesaAdapter(var listDespesaInterface : ListDespesaContract.View,var context: Context?, var despesas : List<Despesa>) :
    RecyclerView.Adapter<ListDespesaAdapter.ListDespesaViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDespesaViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_list_despesas_row,parent,false)
        return ListDespesaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return despesas.count()
    }

    override fun onBindViewHolder(holder: ListDespesaViewHolder, position: Int) {
        holder.containerView.descricao.text = despesas[position].descricao
        holder.containerView.valor.text = despesas[position].valor.toString()

        holder.containerView.setOnClickListener(holder)
        holder.containerView.setOnLongClickListener(holder)
    }

    inner class ListDespesaViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer, View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            listDespesaInterface.onItemClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            listDespesaInterface.onItemLongClick(adapterPosition)
            return true
        }
    }
}