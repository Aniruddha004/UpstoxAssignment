package com.upstox.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upstox.assignment.R
import com.upstox.assignment.data.model.Holding
import com.upstox.assignment.databinding.ItemHoldingBinding
import com.upstox.assignment.utils.roundOffDecimal
import kotlin.math.absoluteValue

class HoldingsAdapter(private val holdings: List<Holding>) : RecyclerView.Adapter<HoldingsAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val binding = ItemHoldingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HoldingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        holder.bind(holdings[position])
    }

    override fun getItemCount(): Int = holdings.size

    class HoldingViewHolder(private val binding: ItemHoldingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holding: Holding) {
            val pnl = holding.quantity * ( holding.ltp - holding.avgPrice)
            binding.symbol.text = holding.symbol
            binding.quantity.text = holding.quantity.toString()
            binding.ltp.text = "${binding.root.context.getString(R.string.rupee_symbol_c3)} ${holding.ltp.toString()}"
            if(pnl>0.0){
                binding.pnl.text = "${binding.root.context.getString(R.string.rupee_symbol_c3)} ${pnl.roundOffDecimal()}"
                binding.pnl.setTextColor(binding.root.context.resources.getColor(R.color.green))
            } else {
                binding.pnl.text = "- ${binding.root.context.getString(R.string.rupee_symbol_c3)} ${pnl.roundOffDecimal()}"
                binding.pnl.setTextColor(binding.root.context.resources.getColor(R.color.red))
            }
        }
    }
}