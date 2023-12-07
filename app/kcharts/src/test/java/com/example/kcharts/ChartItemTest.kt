package com.example.kcharts

import androidx.compose.ui.graphics.Color
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Tests for ChartItem
 * - ChartItem should have a value
 * - ChartItem should have a name
 * - ChartItem may have a color; if it doesn't, it should be noColor
 * - ChartItem.normalizedValue(total) should be value / total. If no total is provided, it should be 1.0
 *
 */
class ChartItemTest {
    private val chartItemWithColor = ChartItem(1.0, "Test ChartItem", Color.Red)
    private val chartItemWithoutColor = ChartItem(1.0, "Test ChartItem")

    @Test
    fun chartItemShouldHaveAValue() {
        assertEquals(1.0, chartItemWithColor.value)
    }

    @Test
    fun chartItemShouldHaveAName() {
        assertEquals("Test ChartItem", chartItemWithColor.name)
    }

    @Test
    fun chartItemMayHaveAColor() {
        assertEquals(Color.Red, chartItemWithColor.color)
        assertEquals(chartItemWithoutColor.color, Color.None)
    }

    @Test
    fun chartItemNormalizedValueShouldBeValueOverTotal() {
        assertEquals(1.0, chartItemWithColor.normalizedValue(1.0))
        assertEquals(0.5, chartItemWithColor.normalizedValue(2.0))
        assertEquals(0.25, chartItemWithColor.normalizedValue(4.0))
        assertEquals(1.0, chartItemWithColor.normalizedValue())
    }
}
