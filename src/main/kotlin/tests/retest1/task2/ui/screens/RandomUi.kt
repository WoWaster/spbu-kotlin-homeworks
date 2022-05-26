package tests.retest1.task2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tests.retest1.task2.ViewModel
import tests.retest1.task2.ui.components.PostColumnWithWaiting
import tests.retest1.task2.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomUi(viewModel: ViewModel) {
    val model by remember { viewModel.model }

    LaunchedEffect(Unit) {
        launch {
            viewModel.getRandom()
        }
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopBar(viewModel)
    }, bottomBar = {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { coroutineScope.launch { viewModel.getRandom() } }) {
                Text("Еще!")
            }
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            PostColumnWithWaiting(model.posts.value)
        }
    }
}
