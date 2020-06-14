package com.example.billsmvp.scenes.receitas.create

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.billsmvp.R
import com.example.billsmvp.models.Receita
import com.example.billsmvp.util.getString
import com.example.billsmvp.util.set
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.bottomsheet_create_receita_content.*
import java.util.*

class BottomsheetCreateReceita(var receita : Receita?) : BottomSheetDialogFragment(), CreateReceitaContract.View {
    val presenter = CreateReceitaPresenter()
    var selectedDate = Date()
    lateinit var dpd : DatePickerDialog
    lateinit var fragmentContext : Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.bottomsheet_create_receita, container, false)

    }

    override fun onStart() {
        super.onStart()
        presenter.view = this
        fillDate()
        fillData()
        setupButtonAdd()
        setupDatePicker()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun fillData() {
        receita?.let {
            EditTextDescricaoReceita.setText(it.descricao)
            EditTextValorReceita.setText(it.valor.toString())
            EditTextDataReceita.setText(it.data.toDate().getString("dd/MM/yyyy"))
            SwitchRecebido.isChecked = it.recebido
        }
    }

    private fun fillDate() {
        EditTextDataReceita.setText(selectedDate.getString("dd/MM/yyyy"))
    }

    override fun showMessage(message: String) {
        Toast.makeText(fragmentContext,message, Toast.LENGTH_SHORT).show()
    }

    override fun setupButtonAdd() {
        btnAddReceita.setOnClickListener {
            val valor = EditTextValorReceita.text.toString().toFloat()
            val descricao = EditTextDescricaoReceita.text.toString()
            val data = Timestamp(selectedDate)
            val recebido = SwitchRecebido.isChecked

            if (receita==null){
                presenter.create(valor, descricao, data, recebido)
            }else {
                var id = receita?.id.toString()
                var user_id = receita?.user_id.toString()
                presenter.update(id,user_id,valor, descricao, data, recebido)
            }
            this.dismiss()
        }
    }

    private fun setupDatePicker() {
        EditTextDataReceita.setOnClickListener {

            val c = Calendar.getInstance()
            c.time = selectedDate
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            dpd = DatePickerDialog(fragmentContext, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                selectedDate = selectedDate.set(Calendar.YEAR,year)
                selectedDate = selectedDate.set(Calendar.MONTH,monthOfYear)
                selectedDate = selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                fillDate()

            }, year, month, day)
            dpd.show()

        }
    }
}