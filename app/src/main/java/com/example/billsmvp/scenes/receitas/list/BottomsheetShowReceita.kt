package com.example.billsmvp.scenes.receitas.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.billsmvp.R
import com.example.billsmvp.models.Receita
import com.example.billsmvp.util.formataParaReal
import com.example.billsmvp.util.getString
import com.example.billsmvp.util.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.bottomsheet_show_receita.*

class BottomsheetShowReceita(var receita : Receita?) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.bottomsheet_show_receita, container, false)
    }

    override fun onStart() {
        super.onStart()
        fillData()
        loadImage()
    }


    fun fillData() {
        receita?.let {
            descricaoreceitashow.text = it.descricao
            valorreceitashow.text = it.valor.formataParaReal()
            datareceitashow.text = it.data.toDate().getString("dd/MM/yyyy")
            SwitchRecebidoShow.isChecked = it.recebido
        }
    }

    fun loadImage() {
        receita?.filePath?.let {
            if (it!="") {
                val storageRef = FirebaseStorage.getInstance().reference
                val image = storageRef.child(it)
                image.downloadUrl.addOnSuccessListener{
                    receita_anexo_imagem.loadImage(it.toString())
                }
            }
        }

    }

}