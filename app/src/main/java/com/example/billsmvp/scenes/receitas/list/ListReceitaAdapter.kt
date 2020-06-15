package com.example.billsmvp.scenes.receitas.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billsmvp.R
import com.example.billsmvp.models.Receita
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_list_receita_row.view.*

class ListReceitaAdapter(var listReceitaInterface : ListReceitaContract.View,var context : Context, var receitas : List<Receita>) : RecyclerView.Adapter<ListReceitaAdapter.ListReceitaAdapterViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListReceitaAdapterViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_list_receita_row,parent,false)
        return ListReceitaAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return receitas.count()
    }

    override fun onBindViewHolder(holder: ListReceitaAdapterViewHolder, position: Int) {
        holder.containerView.descricaoReceita.setText(receitas[position].descricao)
        holder.containerView.valorReceita.setText(receitas[position].valor.toString())
    }

    inner class ListReceitaAdapterViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


 }
}