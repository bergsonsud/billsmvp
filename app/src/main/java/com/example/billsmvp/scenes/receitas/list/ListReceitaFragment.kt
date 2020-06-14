package com.example.billsmvp.scenes.receitas.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billsmvp.R
import com.example.billsmvp.models.Receita
import com.example.billsmvp.scenes.CustomDialog
import com.example.billsmvp.scenes.CustomDialogInterface
import com.example.billsmvp.scenes.receitas.create.BottomsheetCreateReceita
import kotlinx.android.synthetic.main.fragment_list_receitas.*

class ListReceitaFragment : Fragment(), ListReceitaContract.View, CustomDialogInterface {
    val presenter = ListReceitaPresenter()
    lateinit var adapter: ListReceitaAdapter
    lateinit var bottomsheet: BottomsheetCreateReceita
    lateinit var customDialog: CustomDialog
    var receitas : List<Receita> = arrayListOf()

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
        customDialog.view = this
        setupFloatingButton()
        setupAdapter()
        presenter.getAll {success, list ->
            if (success) {
                receitas = list
                customDialog.transacoes = list
                fillAdapter()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialog = CustomDialog(context,receitas, "receitas")
    }

    override fun setupAdapter() {
        adapter = ListReceitaAdapter(this, requireContext(), arrayListOf())
        list_receitas_recyclerview.layoutManager = LinearLayoutManager(context)
        list_receitas_recyclerview.adapter = adapter
    }


    override fun fillAdapter() {
        adapter.receitas = receitas
        adapter.notifyDataSetChanged()
    }

    fun setupFloatingButton(){
        addReceita.setOnClickListener {
           bottomsheet = BottomsheetCreateReceita(null)
           bottomsheet.show(childFragmentManager,"BottomsheetCreateReceita")
        }
    }

    override fun showEdit(position: Int) {
        bottomsheet = BottomsheetCreateReceita(receitas[position])
        bottomsheet.show(childFragmentManager,"BottomsheetCreateReceita")
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemLongClick(position: Int) {
        customDialog.showOptionsDialog(position)
    }
}