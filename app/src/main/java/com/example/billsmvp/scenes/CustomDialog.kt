package com.example.billsmvp.scenes

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.billsmvp.objects.ObjectFirebaseInstance
import com.example.billsmvp.models.Transacao

class CustomDialog(var context: Context, var transacoes : List<Transacao>, var collection : String) {
    lateinit var mainDialog: Dialog
    lateinit var view : CustomDialogInterface
    var clazzParam = "transacao"

    fun showOptionsDialog(position: Int) {

        val options = arrayOf("Editar","Excluir")
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle("Selecione acao")
        dialogBuilder.setSingleChoiceItems(options,-1) { _, which->
            makeChoice(which,position)
        }

        mainDialog = dialogBuilder.create()
        mainDialog.show()
    }

    private fun makeChoice(which: Int, position: Int) {
        mainDialog.dismiss()
        when (which) {
            0 -> view.showEdit(position)
            else -> {
                showDeleteDialog(position)
            }
        }
    }

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle("Deletar")
        dialogBuilder.setMessage("Tem certeza?")

        dialogBuilder.setPositiveButton("Sim"){_,_ ->
            delete(position)
        }

        dialogBuilder.setNegativeButton("Nao"){_,_ ->
            mainDialog.dismiss()
        }

        val dialog: Dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun delete(position: Int) {
        //var id = transacoes[position]
        ObjectFirebaseInstance.delete(collection,transacoes[position].id )
    }

}