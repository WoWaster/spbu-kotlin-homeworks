package tests.retest1.task2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import tests.retest1.task2.ViewModel
import tests.retest1.task2.ui.components.PostColumnWithWaiting
import tests.retest1.task2.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BestUi(viewModel: ViewModel) {
    val model by remember { viewModel.model }
    LaunchedEffect(Unit) {
        launch {
            viewModel.getBest()
        }
    }

    Scaffold(topBar = {
        TopBar(viewModel)
    }) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            PostColumnWithWaiting(model.posts.value)
        }
    }
}
