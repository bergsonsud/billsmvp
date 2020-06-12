package com.example.billsmvp.scenes.account

interface AccountActivityContract {
    interface Presenter{
    }
    interface View{
        fun logout()
    }

    interface Route{
        fun logout()
    }
}