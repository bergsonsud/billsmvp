package com.example.billsmvp.scenes.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.billsmvp.R
import com.example.billsmvp.scenes.login.interfaces.LoginActivityInterface
import com.example.billsmvp.scenes.main.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class LoginActivity : AppCompatActivity(), LoginActivityInterface.View{
    private val presenter = LoginPresenter()
    var RC_SIGN_IN = 1232


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.view = this
        presenter.init()

    }

    override fun goHome() {
        startActivity(Intent(this,
            MainActivity::class.java))
    }

    override fun auth() {

        AuthUI.getInstance()

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(), RC_SIGN_IN
        );
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                goHome()
            } else {
                auth()
            }
        }
    }

}