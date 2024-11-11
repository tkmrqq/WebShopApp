package com.tkmrqq.pmsapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tkmrqq.pmsapp.data.model.CartLibrary
import com.tkmrqq.pmsapp.ui.screen.HomeFragment
import com.tkmrqq.pmsapp.ui.screen.AccountFragment
import com.tkmrqq.pmsapp.ui.screen.CartFragment
import com.tkmrqq.pmsapp.ui.screen.LoginFragment
import com.tkmrqq.pmsapp.ui.screen.SignupFragment
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isLoggedIn: Boolean = false;

    val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user_prefs", 0)
        isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Связь BottomNavigationBar с ViewPager2
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> viewPager.currentItem = 0
                R.id.nav_cart -> viewPager.currentItem = 1
//                R.id.nav_account -> {
//                    if(isLoggedIn){
//                        viewPager.currentItem = 2
//                    }
//                    else { openLoginFragment() }
//                }
                R.id.nav_account -> viewPager.currentItem = 2
            }
            true
        }

        // Связь ViewPager2 с BottomNavigationBar
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.nav_home
                    1 -> bottomNavigationView.selectedItemId = R.id.nav_cart
                    2 -> bottomNavigationView.selectedItemId = R.id.nav_account
                }
            }
        })
    }
//    private fun openLoginFragment() {
//        val loginFragment = LoginFragment(
//            onLoginResult = { result ->
//                if (result) {
//                    isLoggedIn = true
//                    viewPager.currentItem = 2
//                    closeLoginFragment()
//                }
//            },
//            onSignUpClick = { openSignupFragment() }
//        )
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, loginFragment)
//            .addToBackStack(null)
//            .commit()
//        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
//        viewPager.visibility = View.GONE
//    }
//
//    private fun closeLoginFragment() {
//        supportFragmentManager.popBackStack()
//        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.GONE
//        viewPager.visibility = View.VISIBLE
//    }
//
//    private fun openSignupFragment() {
//        val signupFragment = SignupFragment()
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, signupFragment)
//            .addToBackStack(null)
//            .commit()
//
//        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
//        viewPager.visibility = View.GONE
//    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> CartFragment()
            2 -> AccountFragment()
            else -> HomeFragment()
        }
    }
}