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
import com.upstox.assignment.R
import com.upstox.assignment.databinding.FragmentHoldingsBinding
import com.upstox.assignment.ui.adapter.HoldingsAdapter
import com.upstox.assignment.ui.viewmodel.HoldingsViewModel
import com.upstox.assignment.ui.viewmodel.HoldingsViewModelFactory
import com.upstox.assignment.utils.isOnline
import com.upstox.assignment.utils.toDecimal2
import kotlin.math.absoluteValue

class HoldingsFragment : Fragment() {

    private var _binding: FragmentHoldingsBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: HoldingsViewModel
    lateinit var viewModelFactory: HoldingsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoldingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, HoldingsViewModelFactory()).get(HoldingsViewModel::class.java)

        if (isOnline(requireActivity())) {
            binding.progressBar.visibility = View.VISIBLE

            viewModel.holdings.observe(viewLifecycleOwner) { holdingsResponse ->
                Log.d("HoldingsFragment", "Holdings: $holdingsResponse")
                if (holdingsResponse != null) {
                    binding.progressBar.visibility = View.GONE
                    val adapter = HoldingsAdapter(holdingsResponse)
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView.adapter = adapter

                    viewModel.calculatePnl(holdingsResponse) { pnlData ->
                        // Set total PNL to TextView
                        val totalPnl = pnlData["totalPnl"]!!.toDouble()
                        if (totalPnl > 0.0) {
                            binding.cardStrip.cardPnlValue.text =
                                "${getString(R.string.rupee_symbol_c3)} ${totalPnl.toDecimal2()}"
                            binding.cardStrip.cardPnlValue.setTextColor(resources.getColor(R.color.green))
                        } else {
                            binding.cardStrip.cardPnlValue.text =
                                "-${getString(R.string.rupee_symbol_c3)} ${totalPnl.absoluteValue.toDecimal2()}"
                            binding.cardStrip.cardPnlValue.setTextColor(resources.getColor(R.color.red))
                        }

                        var isCardOpen = false
                        binding.cardStrip.root.setOnClickListener {
                            if (!isCardOpen) {
                                binding.cardStrip.layoutTotalValues.visibility = View.VISIBLE
                                binding.cardStrip.cardIcon.setImageDrawable(
                                    context?.resources?.getDrawable(
                                        R.drawable.ic_arrow_down
                                    )
                                )
                                binding.cardStrip.totalInvestment.text = buildString {
                                    append(getString(R.string.rupee_symbol_c3))
                                    append(" ")
                                    append(pnlData["totalInvestment"])
                                }
                                binding.cardStrip.currentValue.text = buildString {
                                    append(getString(R.string.rupee_symbol_c3))
                                    append(" ")
                                    append(pnlData["currentValue"])
                                }
                                val daysPnl = pnlData["daysPnl"]!!.toDouble()
                                if (daysPnl > 0.0) {
                                    binding.cardStrip.daysPnL.text = buildString {
                                        append(getString(R.string.rupee_symbol_c3))
                                        append(" ")
                                        append(daysPnl.toDecimal2())
                                    }
                                    binding.cardStrip.daysPnL.setTextColor(resources.getColor(R.color.green))
                                } else {
                                    binding.cardStrip.daysPnL.text =
                                        buildString {
                                            append("- ")
                                            append(getString(R.string.rupee_symbol_c3))
                                            append(" ")
                                            append(daysPnl.absoluteValue.toDecimal2())
                                        }
                                    binding.cardStrip.daysPnL.setTextColor(resources.getColor(R.color.red))
                                }
                                isCardOpen = true
                            } else {
                                binding.cardStrip.layoutTotalValues.visibility = View.GONE
                                binding.cardStrip.cardIcon.setImageDrawable(
                                    context?.resources?.getDrawable(
                                        R.drawable.ic_arrow_up
                                    )
                                )
                                isCardOpen = false
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Something went wrong. Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                requireActivity(),
                "Please check your internet connection.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}