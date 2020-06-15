package com.example.billsmvp.scenes.despesas.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.billsmvp.R
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.util.formataParaReal
import com.example.billsmvp.util.getString
import com.example.billsmvp.util.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.bottomsheet_show_despesa.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BottomsheetShowDespesa(var despesa : Despesa?) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.bottomsheet_show_despesa, container, false)
    }

    override fun onStart() {
        super.onStart()
        fillData()
        loadImage()
    }


    fun fillData() {
        despesa?.let {
            descricaoshow.text = it.descricao
            valordespesa.text = it.valor.formataParaReal()
            datadespesashow.text = it.data.toDate().getString("dd/MM/yyyy")
            SwitchPagoShow.isChecked = it.pago

        }
    }


    fun loadImage() {
        despesa?.filePath?.let {
            if (it!="") {
                val storageRef = FirebaseStorage.getInstance().reference
                val image = storageRef.child(it)
                image.downloadUrl.addOnSuccessListener{
                    despesa_anexo_imagem.loadImage(it.toString())
                }
            }
        }

        }
}