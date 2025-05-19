package id.co.brainy.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.brainy.data.network.response.TasksItem
import id.co.brainy.data.repository.TaskRepository
import id.co.brainy.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _taskAll = MutableStateFlow<UiState<List<TasksItem>?>>(UiState.Empty)
    val taskAll: StateFlow<UiState<List<TasksItem>?>> = _taskAll

    fun getAllTasks() {
        viewModelScope.launch {
            _taskAll.value = UiState.Loading
            val response = repository.getAllTasks()
            response.onSuccess {
                _taskAll.value = UiState.Success(it ?: emptyList())
            }.onFailure {
                _taskAll.value = UiState.Error(it.message ?: "Unknown Error")
            }
        }
    }
}