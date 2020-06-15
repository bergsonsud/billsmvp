package com.example.billsmvp.base

import com.example.billsmvp.models.Transacao

interface TransacaoInterface {
    fun onItemClick(position: Int)
    fun onItemLongClick(position: Int, transacao: Transacao)
}