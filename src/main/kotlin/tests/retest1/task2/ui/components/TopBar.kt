package tests.retest1.task2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tests.retest1.task2.ViewModel

@Composable
fun TopBar(viewModel: ViewModel) {
    Surface(tonalElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { viewModel.onBestClicked() }) {
                Text("Лучшие")
            }
            Button(onClick = { viewModel.onLatestClicked() }) {
                Text("Последние")
            }
            Button(onClick = { viewModel.onRandomClicked() }) {
                Text("Случайная")
            }
        }
    }
}
