package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.dao.OrderDao
import com.tkmrqq.pmsapp.ui.adapter.OrderAdapter
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel


class AccountFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var orderDao: OrderDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        val ordersRecyclerView = view.findViewById<RecyclerView>(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Загружаем заказы с элементами
        cartViewModel.loadOrdersWithItems(orderDao)

        // Наблюдаем за изменениями в списке заказов с элементами
        cartViewModel.ordersWithItems.observe(viewLifecycleOwner) { ordersWithItems ->
            ordersRecyclerView.adapter = OrderAdapter(ordersWithItems)
        }
    }
}


