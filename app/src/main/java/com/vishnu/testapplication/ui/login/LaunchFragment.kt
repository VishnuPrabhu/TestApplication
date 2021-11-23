package com.vishnu.testapplication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vishnu.testapplication.R
import com.vishnu.testapplication.compose.launch.LaunchView
import com.vishnu.testapplication.domain.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchFragment : Fragment() {

    private val viewModel: LaunchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LaunchView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.launchApplication.observe(viewLifecycleOwner, EventObserver {
            viewModel.isCustomerOnboarded.observe(viewLifecycleOwner, Observer { isOnboarded ->
                if (isOnboarded) {
                    findNavController().navigate(R.id.action_login)
                } else {
                    findNavController().navigate(R.id.action_welcome)
                }
            })
        })
        viewModel.launch()
    }
}

