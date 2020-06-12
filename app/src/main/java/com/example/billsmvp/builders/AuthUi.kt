package com.example.billsmvp.builders

import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.BuildConfig
import com.firebase.ui.auth.viewmodel.RequestCodes.GOOGLE_PROVIDER
import java.util.*

object AuthUi {
    private var RC_SIGN_IN = 1232
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build())


}