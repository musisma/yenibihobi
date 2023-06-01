package com.example.yenibihobi.fragments.shopping

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yenibihobi.R
import com.example.yenibihobi.activities.ShoppingActivity
import com.example.yenibihobi.adapters.ColorsAdapter
import com.example.yenibihobi.adapters.HomeViewpagerAdapter
import com.example.yenibihobi.adapters.SizesAdapter
import com.example.yenibihobi.adapters.ViewPager2Images
import com.example.yenibihobi.data.CartProduct
import com.example.yenibihobi.databinding.FragmentProductDetailsBinding
import com.example.yenibihobi.util.Resource
import com.example.yenibihobi.util.hideBottomNavigationView
import com.example.yenibihobi.viewmodel.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
@AndroidEntryPoint
class ProductDetailsFragment: Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var  binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor : Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupColorsRv()
        setupViewPager()

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizesAdapter.onItemClick = {
            selectedSize = it
        }
        colorsAdapter.onItemClick = {
            selectedColor = it
        }
        binding.buttonAddToCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product, 1,selectedColor,selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        binding.buttonAddToCart.startAnimation()
                    }

                    is Resource.Success ->{
                        binding.buttonAddToCart.revertAnimation()
                        Toast.makeText(requireContext(),"Ürün Sepete Eklendi",Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error ->{
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "₺ ${product.price}"
            tvProductDescription.text = product.description

            if (product.colors.isNullOrEmpty())
                tvProductColors.visibility = View.INVISIBLE
            if (product.sizes.isNullOrEmpty())
                tvProductSizes.visibility = View.INVISIBLE
        }

        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }

    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setupColorsRv() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }
}