package com.mycompany.currencytracker.presentation.setting_screen.currencySelectScreen.crypto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.setting_screen.currencySelectScreen.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoSelectList(
    viewModel: CryptoSelectListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val searchResult = viewModel.searchResult.value

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            SearchBar(searchText = searchText) {
                searchText = it
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(searchResult) { crypto ->
                CryptoSelectListItem(crypto = crypto)
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }


    LaunchedEffect(searchText) {
        viewModel.search(searchText)
    }
}