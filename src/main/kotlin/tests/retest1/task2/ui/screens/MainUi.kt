package tests.retest1.task2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import tests.retest1.task2.ViewModel
import tests.retest1.task2.ui.UiState

@Composable
fun MainUi(viewModel: ViewModel) {
    val uiState by remember { viewModel.uiState }

    when (uiState) {
        UiState.Best -> BestUi(viewModel)
        UiState.Latest -> LatestUi(viewModel)
        UiState.Random -> RandomUi(viewModel)
    }
}
