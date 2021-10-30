package com.vishnu.testapplication.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vishnu.testapplication.databinding.ViewpagerWelcomeFragmentBinding
import org.koin.android.ext.android.inject

class PlaceholderFragment : Fragment() {

    private val placeHolderViewModel: PlaceHolderViewModel by inject()

    private lateinit var binding: ViewpagerWelcomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeHolderViewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewpagerWelcomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeHolderViewModel.drawableResId.observe(this, Observer<Int> {
            binding.imageView.setImageResource(it)
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}