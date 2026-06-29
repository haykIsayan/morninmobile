package com.example.mornin_mobile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mornin_mobile.features.digest.MorninViewModel

@Composable
fun MorninScreen(viewModel: MorninViewModel) {
    when (val state = viewModel.state.collectAsStateWithLifecycle().value) {
        is MorninState.Loading -> LoadingScreen(state)
        is MorninState.Loaded -> LoadedScreen(state)
        is MorninState.Error -> ErrorScreen(state)
    }
}

@Composable
fun LoadingScreen(state: MorninState.Loading) {

}

@Composable
fun LoadedScreen(state: MorninState.Loaded) {
    LazyColumn {
        items(state.articles.size) { index ->
            ArticleItem(index = index, article = state.articles[index])
        }
    }
}

@Composable
fun ErrorScreen(state: MorninState.Error) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = state.message, fontSize = 14.sp, color = MorninWarm.Accent)
    }
}
