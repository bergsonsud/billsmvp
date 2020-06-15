package com.example.billsmvp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.billsmvp.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .circleCrop()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun Date.getString(format : String) : String {
    return SimpleDateFormat(format).format(this)
}

fun Date.set(field: Int, value :  Int) : Date {

    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(field, value )

    return calendar.time

}


fun String.formValidateDescricao() : Boolean {
     if (this == "") {
         return false
     }
    return true
}

fun Float.formValidateValor() : Boolean {
    if (this >0) {
        return true
    }
    return false
}

fun Float.formataParaReal() : String {
    val moeda = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
    return moeda.format(this.toBigDecimal()).replace(moeda.currency.currencyCode, moeda.currency.currencyCode + " ")
}


