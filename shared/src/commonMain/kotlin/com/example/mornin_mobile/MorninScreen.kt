package com.example.mornin_mobile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mornin_mobile.features.digest.MorninViewModel
import com.example.mornin_mobile.features.topics.TopicsRow
import com.example.mornin_mobile.features.topics.TopicsViewModel

@Composable
fun MorninScreen(
    topicsViewModel: TopicsViewModel,
    morninViewModel: MorninViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopicsRow(viewModel = topicsViewModel)
        HorizontalDivider(thickness = 1.dp, color = MorninWarm.Rule)
        when (val state = morninViewModel.state.collectAsStateWithLifecycle().value) {
            is MorninState.Loading -> LoadingScreen(state)
            is MorninState.Loaded -> LoadedScreen(state)
            is MorninState.Error -> ErrorScreen(state)
            is MorninState.NoDigestFound -> NoDigestFoundScreen()
        }
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
fun NoDigestFoundScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No digest found for today.",
            fontSize = 14.sp,
            color = MorninWarm.Accent
        )
    }
}

@Composable
fun ErrorScreen(state: MorninState.Error) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = state.message, fontSize = 14.sp, color = MorninWarm.Accent)
    }
}
