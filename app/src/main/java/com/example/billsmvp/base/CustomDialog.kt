package com.example.billsmvp.base

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.billsmvp.R
import com.example.billsmvp.models.Despesa
import com.example.billsmvp.models.Receita
import com.example.billsmvp.models.Transacao
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomDialog(var context: Context, var transacoes : List<Transacao>, var collection : String) {
    lateinit var mainDialog: Dialog
    lateinit var view : CustomDialogInterface
    lateinit var transacao: Transacao

    fun convertTransacao() {
        var classType = transacao.javaClass.simpleName

        if(classType == "Despesa") {
            transacao = transacao as Despesa
        }else {
            transacao = transacao as Receita
        }
    }



    fun showOptionDialogMaterial(position: Int, transacao: Transacao) {
        this.transacao = transacao
        convertTransacao()
        val choices =
            arrayOf<CharSequence>("Visualizar", "Editar", "Excluir")

        var dialogBuilder = MaterialAlertDialogBuilder(context)

        dialogBuilder.setTitle("Selecione ação")
            .setNeutralButton("Cancelar") { _,_ ->
                mainDialog.dismiss()
            }
            .setItems(choices) {_,which ->
                makeChoice(which,position)
            }

        mainDialog = dialogBuilder.create()
        mainDialog.show()
    }

    fun showDeleteDialogMaterial(position: Int, transacao: Transacao) {

        MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_Catalog_MaterialAlertDialog_FilledButton)
            .setTitle("Deletar")
            .setMessage("Tem certeza?")
            .setNeutralButton("Cancelar") { dialog, which ->
                mainDialog.dismiss()
            }
            .setNegativeButton("Nao") { dialog, which ->
                mainDialog.dismiss()
            }
            .setPositiveButton("Sim") { dialog, which ->
                delete()
            }
            .show()
    }

    private fun makeChoice(which: Int, position: Int) {

        mainDialog.dismiss()
        when (which) {
            0 -> view.showTransacao(position, transacao)
            1 -> view.showEdit(position, transacao)
            else -> {
                //showDeleteDialog(position)
                showDeleteDialogMaterial(position, transacao)
            }
        }
    }


    private fun delete() {
        ObjectFirebaseInstance.delete(collection,this.transacao.id)
        view.notifyAdapter()

    }



}