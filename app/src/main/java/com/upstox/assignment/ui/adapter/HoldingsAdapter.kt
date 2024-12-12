package com.upstox.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upstox.assignment.R
import com.upstox.assignment.data.model.Holding
import com.upstox.assignment.data.model.HoldingsResponse
import com.upstox.assignment.databinding.ItemHoldingBinding
import com.upstox.assignment.utils.roundOffDecimal
import com.upstox.assignment.utils.toDecimal2
import kotlin.math.absoluteValue

class HoldingsAdapter(private val holdings: HoldingsResponse) : RecyclerView.Adapter<HoldingsAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val binding = ItemHoldingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HoldingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        holder.bind(holdings.data.userHolding[position])
    }

    override fun getItemCount(): Int = holdings.data.userHolding.size

    class HoldingViewHolder(private val binding: ItemHoldingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holding: Holding) {
            val pnl = (holding.quantity * ( holding.ltp - holding.avgPrice))
            binding.symbol.text = holding.symbol
            binding.quantity.text = holding.quantity.toString()
            binding.ltp.text = "${binding.root.context.getString(R.string.rupee_symbol_c3)} ${holding.ltp.toDecimal2()}"
            if(pnl > 0.00){
                binding.pnl.text = "${binding.root.context.getString(R.string.rupee_symbol_c3)} ${pnl.toDecimal2()}"
                binding.pnl.setTextColor(binding.root.context.resources.getColor(R.color.green))
            } else {
                binding.pnl.text = "- ${binding.root.context.getString(R.string.rupee_symbol_c3)} ${pnl.absoluteValue.toDecimal2()}"
                binding.pnl.setTextColor(binding.root.context.resources.getColor(R.color.red))
            }
        }
    }
}