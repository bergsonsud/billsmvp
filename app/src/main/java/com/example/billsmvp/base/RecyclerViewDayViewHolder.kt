package com.example.billsmvp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface UpdateViewHolder {
    fun bindViews(any: Any)
}


class RecyclerViewDayViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView),
    UpdateViewHolder {

    // get the views reference from itemView...

    override fun bindViews(any: Any) {

    }
}
