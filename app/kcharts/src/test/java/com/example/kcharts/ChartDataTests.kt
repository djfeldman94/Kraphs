package com.example.kcharts

import androidx.compose.ui.graphics.Color
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Tests for ChartData
 * - ChartData should have a list of ChartItems and be indexable
 * - ChartData should have a total value
 */
class ChartDataTests {
    val chartItem1 = ChartItem(1.0, "Test ChartItem 1", Color.Red)
    val chartItem2 = ChartItem(2.0, "Test ChartItem 2", Color.Blue)
    val chartItem3 = ChartItem(3.0, "Test ChartItem 3", Color.Green)
    val chartItem4 = ChartItem(4.0, "Test ChartItem 4", Color.Yellow)
    val chartItem5 = ChartItem(5.0, "Test ChartItem 5", Color.Magenta)
    val chartItem6 = ChartItem(6.0, "Test ChartItem 6", Color.Cyan)
    val chartItem7 = ChartItem(7.0, "Test ChartItem 7", Color.Gray)

    val chartData = ChartData(
        listOf(
            chartItem1,
            chartItem2,
            chartItem3,
            chartItem4,
            chartItem5,
            chartItem6,
            chartItem7,
        ),
    )
    @Test
    fun chartDataShouldHaveAListOfChartItemsAndBeIndexable() {
        assertEquals(chartItem1, chartData[0])
        assertEquals(chartItem2, chartData[1])
        assertEquals(chartItem3, chartData[2])
        assertEquals(chartItem4, chartData[3])
        assertEquals(chartItem5, chartData[4])
        assertEquals(chartItem6, chartData[5])
        assertEquals(chartItem7, chartData[6])
    }
    @Test
    fun chartDataShouldHaveATotalValue() {
        assertEquals(28.0, chartData.total)
    }


}