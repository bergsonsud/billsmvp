package com.example.billsmvp.base

import com.example.billsmvp.models.Transacao

interface CustomDialogInterface {
    fun showEdit(position: Int, any: Any)
    fun showTransacao(position: Int, any: Any)
    fun notifyAdapter()
}