package com.example.billsmvp.scenes.despesas.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billsmvp.R
import com.example.billsmvp.base.TransacaoInterface
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.models.Transacao
import com.example.billsmvp.base.CustomDialog
import com.example.billsmvp.base.CustomDialogInterface
import com.example.billsmvp.scenes.despesas.create.BottomsheetCreateDespesa
import kotlinx.android.synthetic.main.fragment_list_despesas.*

class ListDepesaFragment : Fragment(), ListDespesaContract.View,
    CustomDialogInterface,
    TransacaoInterface {
    val presenter = ListDespesaPresenter()
    lateinit var adapter: ListDespesaAdapter
    lateinit var adapterDay: ListDespesaDayAdapter
    lateinit var bottomsheetCreate: BottomsheetCreateDespesa
    lateinit var bottomsheetShow: BottomsheetShowDespesa
    lateinit var customDialog: CustomDialog
    var despesas : List<Despesa> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_list_despesas, container, false)
    }


    override fun onStart() {
        super.onStart()
        presenter.view = this
        customDialog.view = this
        setupBottomsheet()
        setupFloatingButton()
        setupAdapterDay()
        presenter.getAll {success, list ->
            if (success) {
                despesas = list
                customDialog.transacoes = list
                presenter.despesas = list
                presenter.group()
                presenter.foreachMapLista()
                fillAdapterDay()
                fillTotalDespesas()
            }
        }


    }

    fun fillTotalDespesas() {
        presenter?.setTotalDespesas()?.let {
            valorTotalDespesas?.text = it
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialog =
            CustomDialog(context, despesas, "despesas")
    }


    override fun setupAdapterDay() {
        adapterDay = ListDespesaDayAdapter(this, requireContext(), arrayListOf())
        list_despesas_recyclerview.layoutManager = LinearLayoutManager(context)
        list_despesas_recyclerview.adapter = adapterDay
    }



    fun fillAdapterDay() {
        adapterDay.lista = presenter.lista
        adapterDay.notifyDataSetChanged()
    }


    override fun fillAdapter() {
        adapter.despesas = despesas
        adapter.notifyDataSetChanged()
    }

    fun setupBottomsheet() {
    }

    fun setupFloatingButton(){
        addDespesa.setOnClickListener {
            bottomsheetCreate = BottomsheetCreateDespesa(null)
            bottomsheetCreate.show(childFragmentManager,"BottomsheetCreateDespesa")
        }
    }

    override fun showEdit(position: Int, any: Any) {
        bottomsheetCreate = BottomsheetCreateDespesa(any as Despesa)
        bottomsheetCreate.show(childFragmentManager,"BottomsheetCreateDespesa")
    }

    override fun showTransacao(position: Int, any: Any) {
        bottomsheetShow = BottomsheetShowDespesa(any as Despesa)
        bottomsheetShow.show(childFragmentManager,"BottomsheetShowDespesa")
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemLongClick(position: Int, transacao: Transacao ) {
        customDialog.showOptionDialogMaterial(position, transacao as Despesa)
    }

    override fun notifyAdapter() {
        fillAdapterDay()
    }
}