package com.upstox.assignment.ui.portfolio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.upstox.assignment.databinding.FragmentHoldingsBinding
import com.upstox.assignment.ui.adapter.HoldingsAdapter
import com.upstox.assignment.ui.viewmodel.HoldingsViewModel
import com.upstox.assignment.ui.viewmodel.HoldingsViewModelFactory
import com.upstox.assignment.utils.isOnline

class HoldingsFragment : Fragment() {

    private var _binding: FragmentHoldingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HoldingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoldingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, HoldingsViewModelFactory()).get(HoldingsViewModel::class.java)

        if(isOnline(requireActivity())){
            binding.progressBar.visibility = View.VISIBLE

            viewModel.holdings.observe(viewLifecycleOwner) { holdingsResponse ->
                Log.d("HoldingsFragment", "Holdings: $holdingsResponse")
                binding.progressBar.visibility = View.GONE
                val adapter = HoldingsAdapter(holdingsResponse.data.userHolding)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = adapter
            }
        }
        else {
            Toast.makeText(requireActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}