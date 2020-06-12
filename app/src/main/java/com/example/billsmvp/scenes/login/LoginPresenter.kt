package com.example.billsmvp.scenes.login

import com.example.billsmvp.builders.FirebaseBuilder
import com.example.billsmvp.scenes.login.interfaces.LoginActivityInterface

class LoginPresenter : LoginActivityInterface.Presenter {
    val auth = FirebaseBuilder.auth
    lateinit var view : LoginActivityInterface.View

    override fun init() {
        checkLogged()
    }

    override fun checkLogged() {
        if (auth.currentUser == null) {
            view.auth()
        }
        else{
            view.goHome()
        }
    }

    override fun goHome() {
    }
}