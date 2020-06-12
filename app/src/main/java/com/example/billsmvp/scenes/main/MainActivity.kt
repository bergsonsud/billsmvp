package com.example.billsmvp.scenes.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.billsmvp.R
import com.example.billsmvp.base.BaseActivity
import com.example.billsmvp.scenes.account.AccountFragment
import com.example.billsmvp.scenes.main.interfaces.MainActivityContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        openFragment(MainFragment())
    }


    override fun openFragment(fragment : Fragment) {
        var fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(container.id,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun openHome() {
        openFragment(MainFragment())
    }

    override fun setupNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
           when(it.itemId) {
               R.id.nav_home ->{
                   openHome()
                   true
               }
               else->{
                   //startActivity(Intent(this,AccountActivity::class.java))
                   openFragment(AccountFragment())
                   true
               }
           }
        }
    }
}
