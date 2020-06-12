package com.example.billsmvp.scenes.main.interfaces

import androidx.fragment.app.Fragment

interface MainActivityContract {
    interface View {
        fun openFragment(fragment: Fragment)
        fun openHome()
        fun setupNavigation()
    }
    interface Presenter {
    }
}