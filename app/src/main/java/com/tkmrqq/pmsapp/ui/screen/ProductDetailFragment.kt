package com.tkmrqq.pmsapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tkmrqq.pmsapp.R
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView

class ProductDetailFragment : Fragment() {

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(name: String, price: Double, description: String, imageUrl: String): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putDouble("price", price)
            bundle.putString("description", description)
            bundle.putString("imageUrl", imageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_screen, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val price = arguments?.getDouble("price")
        val description = arguments?.getString("description")
        val imageUrl = arguments?.getString("imageUrl")

        view.findViewById<TextView>(R.id.productNameDetail).text = name
        view.findViewById<TextView>(R.id.productPriceDetail).text = "$price $"
        view.findViewById<TextView>(R.id.productDescriptionDetail).text = description
//        Glide.with(this).load(imageUrl).into(view.findViewById(R.id.productImageDetail))

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Скрываем фрагмент и возвращаем видимость recyclerView
                activity?.findViewById<FrameLayout>(R.id.fragment_container)?.visibility = View.GONE
                activity?.findViewById<RecyclerView>(R.id.recyclerView)?.visibility = View.VISIBLE
                // Возвращаемся на предыдущий экран
                parentFragmentManager.popBackStack()
            }
        })
//        val cartViewModel = (activity as MainActivity).cartViewModel
//        view.findViewById<Button>(R.id.button4).setOnClickListener{
//            val product = Product(id, name, price, description, imageUrl)
//            cartViewModel.addItem(product)
//        }

    }
}

