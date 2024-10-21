package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tkmrqq.pmsapp.R

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Загружаем layout для экрана корзины
        return inflater.inflate(R.layout.cart_screen, container, false)
    }
}