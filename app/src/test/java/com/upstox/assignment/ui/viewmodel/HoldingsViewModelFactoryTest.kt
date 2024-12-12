package com.upstox.assignment.ui.viewmodel

import org.junit.Assert.assertTrue
import org.junit.Test

class HoldingsViewModelFactoryTest {

    @Test
    fun `test create HoldingsViewModel`() {
        val factory = HoldingsViewModelFactory()
        val viewModel = factory.create(HoldingsViewModel::class.java)
        assertTrue(viewModel is HoldingsViewModel)
    }
}