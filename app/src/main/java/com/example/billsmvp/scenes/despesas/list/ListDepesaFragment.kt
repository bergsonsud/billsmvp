package com.example.billsmvp.scenes.despesas.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billsmvp.R
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.scenes.CustomDialog
import com.example.billsmvp.scenes.CustomDialogInterface
import com.example.billsmvp.scenes.despesas.create.BottomsheetCreateDespesa
import kotlinx.android.synthetic.main.fragment_list_despesas.*

class ListDepesaFragment : Fragment(), ListDespesaContract.View, CustomDialogInterface {
    val presenter = ListDespesaPresenter()
    lateinit var adapter: ListDespesaAdapter
    lateinit var bottomsheet: BottomsheetCreateDespesa
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
        setupAdapter()
        presenter.getAll {success, list ->
            if (success) {
                despesas = list
                customDialog.transacoes = list
                fillAdapter()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialog = CustomDialog(context,despesas, "despesas")
    }

    override fun setupAdapter() {
        adapter = ListDespesaAdapter(this,context, arrayListOf())
        list_despesas_recyclerview.layoutManager = LinearLayoutManager(context)
        list_despesas_recyclerview.adapter = adapter
    }


    override fun fillAdapter() {
        adapter.despesas = despesas
        adapter.notifyDataSetChanged()
    }

    fun setupBottomsheet() {
    }

    fun setupFloatingButton(){
        addDespesa.setOnClickListener {
            bottomsheet = BottomsheetCreateDespesa(null)
            bottomsheet.show(childFragmentManager,"BottomsheetCreateDespesa")
        }
    }

    override fun showEdit(position: Int) {
        bottomsheet = BottomsheetCreateDespesa(despesas[position])
        bottomsheet.show(childFragmentManager,"BottomsheetCreateDespesa")
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemLongClick(position: Int) {
        customDialog.showOptionsDialog(position)
    }
}