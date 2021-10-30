package com.vishnu.testapplication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishnu.testapplication.R
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.databinding.LoginFragmentBinding
import com.vishnu.testapplication.domain.EventObserver
import com.vishnu.testapplication.ui.ActivityNavigationController.navigateToHome
import com.vishnu.testapplication.ui.DialogsController.showErrorDialog
import com.vishnu.testapplication.ui.FragmentNavigationController.hideProgress
import com.vishnu.testapplication.ui.FragmentNavigationController.showProgress
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
            when (it) {
                is Result.Loading -> {
                    showProgress()
                }
                is Result.Error -> {
                    hideProgress()
                    showErrorDialog(requireActivity(), it.exception.localizedMessage.orEmpty(), getString(R.string.ok))
                }
                is Result.Success -> {
                    hideProgress()
                    requireActivity().navigateToHome(isRoot = true)
                }
            }
        })
    }

}