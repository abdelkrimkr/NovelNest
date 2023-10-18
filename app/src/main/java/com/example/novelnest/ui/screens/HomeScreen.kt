package com.example.novelnest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.novelnest.R
import com.example.novelnest.network.Item
import com.example.novelnest.network.VolumeInfo

@Composable
fun HomeScreen(
    novelUiState: NovelUiState,
    modifier: Modifier = Modifier){

    when(novelUiState){
        is NovelUiState.Success -> {NovelGridScreen(novelList = novelUiState.items)}
        is NovelUiState.Loading -> { LoadingScreen()}
        else -> { }
    }
}

@Composable
private fun NovelCard(bookItem: Item?, modifier: Modifier = Modifier){
    Card (
            modifier = modifier,
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp)
    ) {
        AsyncImage(
                model = if (bookItem?.volumeInfo?.imageLinks?.thumbnail != null) {
                    ImageRequest.Builder(context = LocalContext.current)
                        .data(bookItem.volumeInfo.imageLinks.thumbnail.replace("http:", "https:"))
                        .crossfade(true)
                        .build()
                } else {
                    // Provide a default image request or placeholder if the thumbnail is missing
                    ImageRequest.Builder(context = LocalContext.current)
                        .data(R.drawable.ic_broken_image)
                        .build()
                },
                contentDescription = bookItem?.volumeInfo?.title ,
                contentScale = ContentScale.Crop ,
                error = painterResource(R.drawable.ic_broken_image) ,
                placeholder = painterResource(R.drawable.loading_img) ,
                modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun NovelGridScreen(novelList: List<Item> , modifier: Modifier = Modifier){

    LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.fillMaxWidth() ,
            contentPadding = PaddingValues(4.dp)
    )
    {
        items(novelList){  item ->
            NovelCard(
                    bookItem = item ,
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
            modifier = modifier ,
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
                painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed) , modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
    )
}
