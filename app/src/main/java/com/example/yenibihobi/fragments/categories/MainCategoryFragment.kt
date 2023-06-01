package com.example.yenibihobi.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yenibihobi.R
import com.example.yenibihobi.adapters.BestDealsAdapter
import com.example.yenibihobi.adapters.BestProductsAdapter
import com.example.yenibihobi.adapters.SpecialProductsAdapter
import com.example.yenibihobi.databinding.FragmentMainCategoryBinding
import com.example.yenibihobi.databinding.SpecialRvItemBinding
import com.example.yenibihobi.util.Resource
import com.example.yenibihobi.util.showBottomNavigationView
import com.example.yenibihobi.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val TAG = "MainCategoryFragment"
@AndroidEntryPoint


class MainCategoryFragment: Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpecialProductsRV()
        setupBestDealsRV()
        setupBestProductsRv()


        specialProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        bestDealsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        bestProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        lifecycleScope.launchWhenStarted {
           viewModel.specialProducts.collectLatest {
               when (it){
                   is Resource.Loading ->{
                       showLoading()
                   }
                   is Resource.Success ->{
                       specialProductsAdapter.differ.submitList(it.data)
                       hideLoading()
                   }
                   is Resource.Error ->{
                       hideLoading()
                       Log.e(TAG,it.message.toString())
                       Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                   }
                   else -> Unit
           }
        }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when (it){
                    is Resource.Loading ->{
                        showLoading()
                    }
                    is Resource.Success ->{
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error ->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when (it){
                    is Resource.Loading ->{
                        showLoading()
                    }
                    is Resource.Success ->{
                        bestProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error ->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }


    private fun setupBestProductsRv() {
        bestProductsAdapter = BestProductsAdapter()
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = bestProductsAdapter
        }
    }

    private fun setupBestDealsRV() {
        bestDealsAdapter = BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = bestDealsAdapter
        }
    }

    private fun hideLoading() {
        binding.mainCategoryProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.mainCategoryProgressBar.visibility = View.VISIBLE
    }

    private fun setupSpecialProductsRV() {
        specialProductsAdapter = SpecialProductsAdapter()
        binding.rvSpecialProduct.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = specialProductsAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }
}