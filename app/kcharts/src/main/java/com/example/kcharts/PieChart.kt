package com.example.kcharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

data class ChartItem(
    val value: Double,
    val name: String,
    val color: Color = Color.None,
) {
    fun normalizedValue(total: Double = 1.0): Double = value / total
}
val Color.Companion.None: Color
    get() = Color(0, 0, 0, 0)

class ChartData(
    private val slices: List<ChartItem>,
) : List<ChartItem> by slices {
    val total = slices.sumOf { it.value }
}

class ChartBuilderScope {
    private val items = mutableListOf<ChartItem>()

    fun item(value: Double, color: Color, name: String): ChartBuilderScope {
        items.add(ChartItem(value, name, color))
        return this
    }

    fun add(item: ChartItem): ChartBuilderScope {
        items.add(item)
        return this
    }

    fun build(): List<ChartItem> = items
}

object KChart {
    @Composable
    fun TrianglePathCanvas(startAngle: Float, sweepAngle: Float, length: Float, fill: Color) {
        val enlarged = remember { mutableStateOf(false) }
        Canvas(
            modifier =
            Modifier.size((length * (if (enlarged.value) 1.2f else 1f)).dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffet ->
                            enlarged.value = !enlarged.value
                        },
                    )
                },
        ) {
            // First line end point
            // val firstEndPoint = Offset(
            //    center.x + length * cos(Math.toRadians(startAngle.toDouble())).toFloat(),
            //    center.y + length * sin(Math.toRadians(startAngle.toDouble())).toFloat(),
            // )
            // Second line end point (for arc)
            // val secondEndPoint = Offset(
            //  center.x + length * cos(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat(),
            // center.y + length * sin(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat(),
            // )

            drawArc(
                fill,
                startAngle,
                sweepAngle,
                true,
                style = Fill,
            )
        }
    }

    @Composable
    fun PieChart(
        modifier: Modifier = Modifier,
        xAxis: @Composable () -> Unit = {},
        content: ChartBuilderScope.() -> Unit,
    ) {
        val builder = ChartBuilderScope().apply(content)
        val items = builder.build()
        var total = 0.0
        for (item in items) {
            total += item.value
        }
        // val chart = ChartData(items, total)
        // val normalized = chart.normalized
        var lastAngle = 0f
        Column {
            Box {
                for (item in items) {
                    val angle = (item.value / total).toFloat() * 360f
                    TrianglePathCanvas(lastAngle, angle, 100f, item.color)
                    lastAngle += angle
                }
            }
            xAxis()
        }
    }

    val trianglePath = Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, 100f)
        lineTo(100f, 100f)
        close()
    }
    fun makeTrianglePath(startingAngle: Float, sweepAngle: Float): Path {
        return Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, 100f)
            lineTo(100f, 100f)
            close()
        }
    }

    @Composable
    fun makePieChart(items: List<ChartItem>) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp),
        ) {
            for (item in items) {
                drawPath(
                    path = trianglePath,
                    color = item.color,
                    style = Stroke(width = 10.dp.toPx()),
                )
            }
        }
    }

    @Composable
    fun Sliver(
        value: Double,
        color: Color,
    ) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp),
        ) {
            drawPath(
                path = trianglePath,
                color = color,
                style = Stroke(width = 10.dp.toPx()),
            )
        }
    }
}
