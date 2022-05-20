package tests.retest1.task2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import tests.retest1.task2.ui.screens.MainUi

fun main() {
    val viewModel = ViewModel()
    application {
        Window(onCloseRequest = ::exitApplication, title = "Bash.org reader") {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainUi(viewModel)
                }
            }
        }
    }
}
