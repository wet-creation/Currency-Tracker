package com.mycompany.currencytracker.presentation.common.detail_screen


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
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
import com.mycompany.currencytracker.presentation.ui.theme.borderColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Chart(currencies: Map<Point, Long>){
    val steps = 6

    val dataPoints : List<Point> = currencies.keys.toList()

    val maxValue = dataPoints.maxBy { it.y }
    val minValue = dataPoints.minBy { it.y }
    val difference = maxValue.y - minValue.y


    val formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm").withZone(ZoneId.systemDefault())

    Log.d("Point", "${currencies}")

    val xAxisData = AxisData.Builder()
        .axisStepSize(22.dp)
        .backgroundColor(Color.Transparent)
        .steps(currencies.size - 1)
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(Color.Transparent)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = difference / steps
            (minValue.y + (i * yScale)).toString()
        }
        .axisLineColor(Color.Transparent)
        .axisLabelColor(borderColor)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = dataPoints,
                    LineStyle(
                        color = selectTextColor
                    ),
                    IntersectionPoint(
                        color = selectTextColor,
                        radius = 4.dp
                    ),
                    SelectionHighlightPoint(
                        color = secondTextColor,
                        radius = 4.dp
                    ),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            val currentPoint = Point(x, y)

                            val timestamp = currencies[currentPoint] ?: 1

                            val date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
                            "${date}\n${y}"
                        }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            enableVerticalLines = false,
            color = borderColor
        ),
        backgroundColor = Color.Transparent
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun ChangeChartTimeButtons(
    selectedTime: Int,
    onItemClick: (Int)->Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ){
        listOf(24, 7, 30).forEach { time ->
            FilterChip(
                onClick = { onItemClick(time) },
                label = {
                    if (time == 24){
                        Text("${time}h")
                    }
                    else{
                        Text("${time}d")
                    }
                },
                selected = time == selectedTime
            )
        }
    }
}