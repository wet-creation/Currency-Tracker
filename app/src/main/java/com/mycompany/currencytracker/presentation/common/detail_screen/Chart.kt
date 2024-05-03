package com.mycompany.currencytracker.presentation.common.detail_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Chart(currencies: Map<Point, Long>) {
    if (currencies.isEmpty())
        return
    val steps = 6
    val dataPoints: List<Point> = currencies.keys.toList()

    var maxValue = dataPoints.maxBy { it.y }.y
    var minValue = dataPoints.minBy { it.y }.y
    var difference = maxValue - minValue

    if (difference == 0f){
        maxValue += (maxValue * 0.15f)
        minValue -= (minValue * 0.15f)
        difference = maxValue - minValue
    }

    val formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm").withZone(ZoneId.systemDefault())

    val xAxisData = AxisData.Builder()
        .axisStepSize(22.dp)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .axisLineColor(MaterialTheme.colorScheme.background)
        .steps(currencies.size - 1)
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .axisLineColor(MaterialTheme.colorScheme.background)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = difference / steps
            (minValue + (i * yScale)).toString()
        }
        .axisLabelColor(borderColor)
        .build()

    val lineChartData = LineChartData(
        paddingRight = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = dataPoints,
                    LineStyle(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.secondary,
                        radius = 4.dp
                    ),
                    SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.secondary,
                        radius = 4.dp
                    ),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            val currentPoint = Point(x, y)

                            val timestamp = currencies[currentPoint] ?: 1

                            val date =
                                Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault())
                                    .toLocalDateTime().format(formatter)
                            "$date - $y"
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
        )
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
    selectedTime: String,
    onItemClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        listOf("24h", "7d", "30d").forEach { time ->
            FilterChip(
                onClick = { onItemClick(time) },
                label = {
                    Text(time)
                },
                selected = time == selectedTime
            )
        }
    }
}