package com.vishnu.testapplication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.databinding.LoginFragmentBinding
import com.vishnu.testapplication.domain.EventObserver
import com.vishnu.testapplication.ui.ActivityNavigation.navigateToHome
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    val viewmodel: LoginViewModel by viewModel()

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewmodel
        viewmodel.login.observe(viewLifecycleOwner, EventObserver {
            val showLoader = it is Result.Loading
            when (it) {
                is Result.Error -> {
                    // show error
                }
                is Result.Success -> {
                    requireActivity().navigateToHome(isRoot = true)
                }
            }
        })
    }

}