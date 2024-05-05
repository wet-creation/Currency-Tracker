package com.mycompany.currencytracker.presentation.notification_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.calculator.states.ActionInput
import com.mycompany.currencytracker.presentation.calculator.ui.elements.ConvertButton
import com.mycompany.currencytracker.presentation.common.AutoResizedText
import com.mycompany.currencytracker.presentation.navigation.BottomBarScreen
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.notification_screen.list.main.YellowButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    val viewModel = hiltViewModel<NotificationScreenViewMode>()
    val state = viewModel.screenState
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(23.dp)
                    .height(23.dp),
                model = state.value.crypto.image,
                contentDescription = "image"
            )
            Text(
                text = state.value.crypto.symbol.uppercase(), style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
                ), modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = state.value.crypto.name, style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        if (state.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                top = 71.dp, end = 101.dp, start = 101.dp, bottom = 48.dp
            )
        ) {
            AutoResizedText(
                text = stringResource(id = R.string.when_price_reach_target),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
                ),

                )
            BoxWithConstraints {
                Text(
                    text = state.value.writtenPrice,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            val width = it.size.width.dp
                            viewModel.changeWidth(width)
                        },
                    softWrap = false,
                    fontSize = calculateShrunkFontSize(
                        state.value.writtenPrice, TextStyle(
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(600)
                        )
                    ),
                    style = TextStyle(
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    text = state.value.symbolForFiat,
                    isSelected = state.value.fiatSelected,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.selectBase(true)
                }
                CustomButton(
                    text = state.value.symbolForCrypto.uppercase(),
                    isSelected = !state.value.fiatSelected,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.selectBase(false)
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.current_price), style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Text(
                text = if (!state.value.fiatSelected) " ${state.value.symbolForCrypto.uppercase()} ${state.value.currentPriceForCrypto}"
                else " ${state.value.symbolForFiat} ${state.value.currentPriceForFiat}",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
        YellowButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.set_a_price_allert)
        ) {
            viewModel.saveNotification()
        }

        LaunchedEffect(key1 = viewModel.navigate.value) {

            val route = if (navController.backQueue.map { it.destination.route }
                    .contains(Screen.NotificationScreenList.route)) Screen.NotificationScreenList.route else navController.backQueue[navController.backQueue.size - 2].destination.route
            if (viewModel.navigate.value)
                navController.popBackStack(
                    route?: BottomBarScreen.Home.route,
                    false
                )
        }

        Column {
            Row(modifier = Modifier.weight(1f)) {
                ConvertButton(text = "1", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(1))
                }
                ConvertButton(text = "2", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(2))
                }
                ConvertButton(text = "3", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(3))
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                ConvertButton(text = "4", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(4))
                }
                ConvertButton(text = "5", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(5))
                }
                ConvertButton(text = "6", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(6))
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                ConvertButton(text = "7", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(7))
                }
                ConvertButton(text = "8", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(8))
                }
                ConvertButton(text = "9", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(9))
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                ConvertButton(text = ".", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Decimal)
                }
                ConvertButton(text = "0", modifier = Modifier.weight(1f)) {
                    viewModel.readInput(ActionInput.Number(0))
                }
                ConvertButton(
                    icon = R.drawable.eraser_icon, modifier = Modifier.weight(1f)
                ) {
                    viewModel.readInput(ActionInput.Erase)
                }
            }
        }

    }
}

@Composable
private fun BoxWithConstraintsScope.calculateShrunkFontSize(
    text: String,
    style: TextStyle
): TextUnit {
    var shrunkFontSize = 48.sp
    val calculateIntrinsics: @Composable () -> ParagraphIntrinsics = {
        ParagraphIntrinsics(
            text = text,
            style = style.copy(fontSize = shrunkFontSize),
            density = LocalDensity.current,
            fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
        )
    }

    var intrinsics = calculateIntrinsics()
    with(LocalDensity.current) {
        while (intrinsics.maxIntrinsicWidth > maxWidth.toPx()) {
            shrunkFontSize *= 0.9f
            intrinsics = calculateIntrinsics()
        }
    }

    return shrunkFontSize
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, isSelected: Boolean = false, text: String, onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .width(88.dp)
            .height(38.dp)
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(size = 21.dp)
            )) {
        Text(
            text = text, style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(600),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            )
        )
    }
}

