package com.example.yenibihobi.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.yenibihobi.R
import com.example.yenibihobi.util.Resource
import com.example.yenibihobi.viewmodel.CategoryViewModel
import com.example.yenibihobi.data.Category
import com.example.yenibihobi.viewmodel.factory.BaseCategoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
@AndroidEntryPoint
class ChairFragment: BaseCategoryFragment() {

    @Inject
    lateinit var firestore: FirebaseFirestore

    val viewModel by viewModels<CategoryViewModel>{
        BaseCategoryViewModelFactory(firestore, Category.Sayi)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest {
                when (it){
                    is Resource.Loading ->{
                        showOfferLoading()

                    }
                    is Resource.Success ->{
                        offerAdapter.differ.submitList((it.data))
                        hideOfferLoading()

                    }
                    is Resource.Error ->{
                        Snackbar.make(requireView(),it.message.toString(),Snackbar.LENGTH_LONG).show()
                        hideOfferLoading()

                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when (it){
                    is Resource.Loading ->{
                        showBestProductsLoading()

                    }
                    is Resource.Success ->{
                        bestProductsAdapter.differ.submitList((it.data))
                        hideBestProductsLoading()

                    }
                    is Resource.Error ->{
                        Snackbar.make(requireView(),it.message.toString(),Snackbar.LENGTH_LONG).show()
                        hideBestProductsLoading()

                    }
                    else -> Unit
                }
            }
        }
    }
}