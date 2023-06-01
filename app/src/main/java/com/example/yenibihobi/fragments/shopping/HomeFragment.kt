package com.example.yenibihobi.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yenibihobi.R
import com.example.yenibihobi.adapters.HomeViewpagerAdapter
import com.example.yenibihobi.databinding.FragmentHomeBinding
import com.example.yenibihobi.fragments.categories.AccessoryFragment
import com.example.yenibihobi.fragments.categories.ChairFragment
import com.example.yenibihobi.fragments.categories.CupboardFragment
import com.example.yenibihobi.fragments.categories.FurnitureFragment
import com.example.yenibihobi.fragments.categories.MainCategoryFragment
import com.example.yenibihobi.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment(R.layout.fragment_home){
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

        binding.viewPagerHome.isUserInputEnabled = false

        val viewPager2Adapter = HomeViewpagerAdapter(categoriesFragment,childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPagerHome){ tab, position ->
            when(position){
                0 -> tab.text = "Sana Özel"
                1 -> tab.text = "Sayılarla Boyama"
                2 -> tab.text = "Takı Boyama"
                3 -> tab.text = "Maket Boyama"
                4 -> tab.text = "3D Boyama"
                5 -> tab.text = "Sarf Malzeme"
            }

        }.attach()
    }
}