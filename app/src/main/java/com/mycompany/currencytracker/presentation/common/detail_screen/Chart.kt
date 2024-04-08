package com.mycompany.currencytracker.presentation.common.detail_screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Chart(currencies: List<Point>){
    val steps = 7
    val maxValue = currencies.maxBy { it.y }
    val minValue = currencies.minBy { it.y }
    val difference = maxValue.y - minValue.y

    val startValueX = currencies[0].x.toLong()
    val endValueX = currencies[currencies.size - 1].x.toLong()

    val differenceX = endValueX - startValueX

    val formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm").withZone(ZoneId.systemDefault())

    Log.d("Point", "${currencies}")

    val xAxisData = AxisData.Builder()
        .axisStepSize(1.dp)
        .backgroundColor(Color.Transparent)
        .steps(currencies.size - 1)
        //.labelData { i -> Instant.ofEpochSecond(i.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter) }
        .labelData { i ->
            val xScale = differenceX / 10
            (startValueX + (i * xScale)).toString()
        }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = difference / steps
            (minValue.y + (i * yScale)).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = currencies,
                    LineStyle(
                        color = Color.Blue
                    ),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            Log.d("Point ${x}", "${y}")
                            val date = Instant.ofEpochSecond(x.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
                            "${date} - ${y}"
                        }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.Transparent
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}