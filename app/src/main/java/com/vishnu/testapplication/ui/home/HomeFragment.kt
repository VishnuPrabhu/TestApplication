package com.vishnu.testapplication.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vishnu.testapplication.R
import com.vishnu.testapplication.databinding.HomeFragmentBinding
import com.vishnu.testapplication.di.sessionHelper
import com.vishnu.testapplication.ui.DialogsController.showAlertDialog
import com.vishnu.testapplication.ui.util.adaptToSwipeLayout
import com.vishnu.testapplication.ui.util.setupRefreshLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setUpAppBar()
        setUpRefreshListener()
        setUpAccountDetails()
        setUpTransactionsList()
        setUpButton()
    }

    private fun setUpTransactionsList() {
        binding.transactionsListView.adapter = TransactionsListAdapter()
        binding.transactionsListView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.noItemsView.isVisible = it.isEmpty()
            binding.transactionsListView.isVisible = it.isNotEmpty()
            if (it.isNotEmpty()) {
                (binding.transactionsListView.adapter as? TransactionsListAdapter)?.submitList(it)
            }
        })
        viewModel.loadingTransactions.observe(viewLifecycleOwner, Observer { loading ->
            // Shimmer Aniamtion for RecyclerView.
        })
    }

    private fun setUpAccountDetails() {
        viewModel.account.observe(viewLifecycleOwner, Observer {
            binding.accountCard.apply {
                this.accountName = it.accountName
                this.accountNumber = it.accountNumber
                this.accountBalance = it.balance
                this.amountTitle = if (it.accountNumber.isNotEmpty()) getString(R.string.you_have) else ""
                this.amountNote = if (it.accountNumber.isNotEmpty()) getString(R.string.in_your_account) else ""
            }
        })
        viewModel.loadingBalance.observe(viewLifecycleOwner, Observer {
            binding.accountCard.isLoading = it
        })
    }

    private fun setUpRefreshListener() {
        setHasOptionsMenu(true)
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        binding.appBar.adaptToSwipeLayout(binding.swipeRefreshLayout)
        setupRefreshLayout(binding.swipeRefreshLayout, binding.transactionsListView)

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.swipeRefreshLayout.isRefreshing = loading
            binding.swipeRefreshLayout.isEnabled = !loading
            binding.progressIndicator.isVisible = loading
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionHelper.registerCallback {
                    viewModel.refresh()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.refresh()
                return true
            }
            R.id.action_logout -> {
                showLogoutDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLogoutDialog() {
        showAlertDialog(requireActivity(), getString(R.string.logout_title), getString(R.string.logout_message),
            getString(R.string.yes_logout), { requireActivity().finish() },
            getString(R.string.cancel), {})
    }

    private fun setUpAppBar() {
        binding.collapsingToolbar.title = ""
        binding.toolBar.title = ""
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
    }

    private fun setUpButton() {
        binding.makeTransfer.setOnClickListener {
            findNavController().navigate(R.id.transferFragment)
        }
    }
}