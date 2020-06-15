package com.example.billsmvp.scenes.receitas.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billsmvp.R
import com.example.billsmvp.base.TransacaoInterface
import com.example.billsmvp.models.Receita
import com.example.billsmvp.models.Transacao
import com.example.billsmvp.base.CustomDialog
import com.example.billsmvp.base.CustomDialogInterface
import com.example.billsmvp.scenes.receitas.create.BottomsheetCreateReceita
import kotlinx.android.synthetic.main.fragment_list_receitas.*
import java.util.*

class ListReceitaFragment : Fragment(), ListReceitaContract.View,
    CustomDialogInterface,
    TransacaoInterface {
    val presenter = ListReceitaPresenter()
    lateinit var adapter: ListReceitaAdapter
    lateinit var adapterDay: ListReceitaDayAdapter
    lateinit var bottomsheetCreate: BottomsheetCreateReceita
    lateinit var bottomsheetShow: BottomsheetShowReceita
    lateinit var customDialogReceita: CustomDialog
    var receitas : List<Receita> = arrayListOf()
    lateinit var groups : Map<Int,List<Receita>>
    val lista : MutableList<Any> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_list_receitas, container, false)
    }


    override fun onStart() {
        super.onStart()
        presenter.view = this
        customDialogReceita.view = this
        setupBottomsheet()
        setupFloatingButton()
        setupAdapterDay()
        presenter.getAll {success, list ->
            if (success) {
                receitas = list
                customDialogReceita.transacoes = list
                presenter.receitas = list
                presenter.group()
                presenter.foreachMapLista()
                fillAdapterDay()
                fillTotalReceitas()
            }
        }

    }

    fun fillTotalReceitas() {
        presenter?.setTotalReceitas()?.let {
            valorTotalReceitas?.text = it
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialogReceita =
            CustomDialog(context, receitas, "receitas")
    }

    override fun setupAdapterDay() {
        adapterDay = ListReceitaDayAdapter(this, requireContext(), arrayListOf())
        list_receitas_recyclerview.layoutManager = LinearLayoutManager(context)
        list_receitas_recyclerview.adapter = adapterDay
    }

    fun fillAdapterDay() {
        adapterDay.lista = presenter.lista
        adapterDay.notifyDataSetChanged()
    }


    override fun fillAdapter() {
        adapter.receitas = presenter.receitas
        adapter.notifyDataSetChanged()
    }

    fun setupBottomsheet() {
    }

    fun setupFloatingButton(){
        addReceita.setOnClickListener {
            bottomsheetCreate = BottomsheetCreateReceita(null)
            bottomsheetCreate.show(childFragmentManager,"BottomsheetCreateReceita")
        }
    }

    override fun showEdit(position: Int, any: Any) {
        bottomsheetCreate = BottomsheetCreateReceita(any as Receita)
        bottomsheetCreate.show(childFragmentManager,"BottomsheetCreateReceita")
    }

    override fun showTransacao(position: Int, any: Any) {
        bottomsheetShow = BottomsheetShowReceita(any as Receita)
        bottomsheetShow.show(childFragmentManager,"BottomsheetShowReceita")
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemLongClick(position: Int, transacao: Transacao) {
        customDialogReceita.showOptionDialogMaterial(position, transacao as Receita)
    }

    override fun notifyAdapter() {
        fillAdapterDay()
    }

}