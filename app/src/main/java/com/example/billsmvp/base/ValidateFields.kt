package com.example.billsmvp.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.billsmvp.util.formValidateDescricao
import com.example.billsmvp.util.formValidateValor
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easymoneywidgets.EasyMoneyEditText

class ValidateFields(var editText : EditText, var textInputLayout: TextInputLayout) {

    fun watch() {
        editText.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.setErrorEnabled(false)
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })
    }

    fun validateDescricao() : Boolean {
        watch()
        if (this.editText.text.toString().formValidateDescricao()) {
            return true
        }else{
            this.textInputLayout.setError("Este campo é obrigatório")
            return false
        }
    }

    fun validateValor() : Boolean {
        watch()
        var editTextValue = this.editText as EasyMoneyEditText
        if (editTextValue.valueString.toFloat().formValidateValor()) {
            return true
        }else{
            this.textInputLayout.setError("O valor é obrigatório")
            return false
        }
    }


}