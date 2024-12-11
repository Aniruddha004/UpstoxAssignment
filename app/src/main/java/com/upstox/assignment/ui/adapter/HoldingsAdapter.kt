package com.upstox.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upstox.assignment.data.model.Holding
import com.upstox.assignment.databinding.ItemHoldingBinding
import com.upstox.assignment.utils.roundOffDecimal

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
            val pnl = holding.quantity * (holding.avgPrice - holding.ltp)
            binding.symbol.text = holding.symbol
            binding.quantity.text = holding.quantity.toString()
            binding.ltp.text = holding.ltp.toString()
            binding.pnl.text = pnl.roundOffDecimal().toString()
        }
    }
}