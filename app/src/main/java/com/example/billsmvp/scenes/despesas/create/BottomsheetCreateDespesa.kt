package com.example.billsmvp.scenes.despesas.create

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.billsmvp.R
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.util.getString
import com.example.billsmvp.util.set
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.bottomsheet_create_despesa_content.*
import java.util.*

class BottomsheetCreateDespesa(var despesa: Despesa?) : BottomSheetDialogFragment(), CreateDespesaContract.View {
    val presenter = CreateDespesaPresenter()
    var selectedDate = Date()
    lateinit var dpd : DatePickerDialog
    lateinit var fragmentContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.bottomsheet_create_despesa, container, false)
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

    override fun setupButtonAdd() {
        btnAddDespesa.setOnClickListener {
            val valor = EditTextValorDespesa.text.toString().toFloat()
            val descricao = EditTextDescricaoDespesa.text.toString()
            val data = Timestamp(selectedDate)
            val pago = SwitchPago.isChecked

            if (despesa==null){
                presenter.create(valor, descricao, data, pago)
            }else {
                var id = despesa?.id.toString()
                var user_id = despesa?.user_id.toString()
                presenter.update(id,user_id,valor, descricao, data, pago)
            }
            this.dismiss()
        }
    }

    override fun fillData() {
        despesa?.let {
            EditTextDescricaoDespesa.setText(it.descricao)
            EditTextValorDespesa.setText(it.valor.toString())
            EditTextDataDespesa.setText(it.data.toDate().getString("dd/MM/yyyy"))
            SwitchPago.isChecked = it.pago
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(fragmentContext,message, Toast.LENGTH_SHORT).show()
    }

    private fun fillDate() {
        EditTextDataDespesa.setText(selectedDate.getString("dd/MM/yyyy"))
    }

    private fun setupDatePicker() {
        EditTextDataDespesa.setOnClickListener {

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