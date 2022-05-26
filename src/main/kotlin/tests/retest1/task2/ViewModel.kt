package tests.retest1.task2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import tests.retest1.task2.models.Model
import tests.retest1.task2.ui.UiState

class ViewModel {
    private val _model = mutableStateOf(Model())
    val model: State<Model> = _model

    private val _uiState = mutableStateOf(UiState.Best)
    val uiState: State<UiState> = _uiState

    fun onBestClicked() {
        _uiState.value = UiState.Best
    }

    suspend fun getBest() {
        _model.value.getBest()
    }

    fun onLatestClicked() {
        _uiState.value = UiState.Latest
    }

    suspend fun getLatest() {
        _model.value.getLatest()
    }

    fun onRandomClicked() {
        _uiState.value = UiState.Random
    }

    suspend fun getRandom() {
        _model.value.getRandom()
    }
}
