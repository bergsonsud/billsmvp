package com.example.billsmvp.scenes.login.interfaces

interface LoginActivityInterface {
    interface View {
        fun goHome()
        fun auth()
    }

    interface Presenter {
        fun init()
        fun checkLogged()
        fun goHome()

    }
}