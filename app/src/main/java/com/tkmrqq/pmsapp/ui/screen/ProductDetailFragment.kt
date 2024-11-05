package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tkmrqq.pmsapp.R
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel

class ProductDetailFragment : Fragment() {

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_PRODUCT, product)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val price = arguments?.getDouble("price")
        val description = arguments?.getString("description")
        val imageResId = arguments?.getInt("imageResId")

        val product = arguments?.getParcelable(ARG_PRODUCT, Product::class.java)

        product?.let {
            view.findViewById<TextView>(R.id.productNameDetail).text = it.name
            view.findViewById<TextView>(R.id.productPriceDetail).text = "${it.price} BYN"
            view.findViewById<TextView>(R.id.productDescriptionDetail).text = it.description
            Glide.with(this).load(it.imageResId).into(view.findViewById(R.id.productImageDetail))
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.findViewById<FrameLayout>(R.id.fragment_container)?.visibility = View.GONE
                activity?.findViewById<RecyclerView>(R.id.recyclerView)?.visibility = View.VISIBLE
                // Возвращаемся на предыдущий экран
                parentFragmentManager.popBackStack()
            }
        })

        val cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        val button = view.findViewById<Button>(R.id.button4)
        button.setOnClickListener{
            product?.let {
            cartViewModel.addItem(it)
            Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_LONG).show()
            } ?: Toast.makeText(requireContext(), "Product not available", Toast.LENGTH_LONG).show()
        }
    }
}

