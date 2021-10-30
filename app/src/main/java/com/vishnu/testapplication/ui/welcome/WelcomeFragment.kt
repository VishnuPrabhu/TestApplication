package com.vishnu.testapplication.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vishnu.testapplication.R
import com.vishnu.testapplication.databinding.LaunchWelcomeBinding
import com.vishnu.testapplication.domain.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModel()

    private lateinit var binding: LaunchWelcomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.loginEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_login)
        })

        val adapter = PlaceHolderPagerAdapter(requireActivity())
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            // not needed now.
        }.attach()
    }
}