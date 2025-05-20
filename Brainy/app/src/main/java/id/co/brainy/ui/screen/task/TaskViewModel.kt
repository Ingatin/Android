package id.co.brainy.ui.screen.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.brainy.data.network.response.TaskResponse
import id.co.brainy.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    // For task creation
    private val _taskCreated = MutableStateFlow<Boolean?>(null)
    val taskCreated: StateFlow<Boolean?> = _taskCreated.asStateFlow()

    // For all tasks
    private val _allTasks = MutableStateFlow<List<TaskResponse>>(emptyList())
    val allTasks: StateFlow<List<TaskResponse>> = _allTasks.asStateFlow()

    // For tasks by category
    private val _tasksByCategory = MutableStateFlow<List<TaskResponse>>(emptyList())
    val tasksByCategory: StateFlow<List<TaskResponse>> = _tasksByCategory.asStateFlow()

    // For single task
    private val _taskById = MutableStateFlow<TaskResponse?>(null)
    val taskById: StateFlow<TaskResponse?> = _taskById.asStateFlow()

    // For loading states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // For errors
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun createTask(
        token: String,
        title: String,
        description: String,
        category: String,
        deadline: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.createTask(token, title, description, category, deadline)
                _taskCreated.value = true
                _error.value = null
            } catch (e: Exception) {
                _taskCreated.value = false
                _error.value = e.message ?: "Failed to create task"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Existing createTask function remains the same

    fun getAllTasks(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllTasks(token).collect { tasks ->
                _allTasks.value = tasks
                _isLoading.value = false
            }
        }
    }

    fun getTasksByCategory(token: String, category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTasksByCategory(token, category).collect { tasks ->
                _tasksByCategory.value = tasks
                _isLoading.value = false
            }
        }
    }

    fun getTaskById(token: String, id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTaskById(token, id).collect { task ->
                _taskById.value = task
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
    // Add these to your existing TaskViewModel
    private val _taskUpdated = MutableStateFlow<Boolean?>(null)
    val taskUpdated: StateFlow<Boolean?> = _taskUpdated.asStateFlow()

    private val _taskDeleted = MutableStateFlow<Boolean?>(null)
    val taskDeleted: StateFlow<Boolean?> = _taskDeleted.asStateFlow()

    fun updateTask(
        token: String,
        id: String,
        title: String,
        description: String,
        category: String,
        deadline: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateTask(token, id, title, description, category, deadline)
                _taskUpdated.value = true
                _error.value = null
                // Refresh the task list after update
                if (_tasksByCategory.value.isNotEmpty()) {
                    getTasksByCategory(token, _tasksByCategory.value.first().category)
                } else {
                    getAllTasks(token)
                }
            } catch (e: Exception) {
                _taskUpdated.value = false
                _error.value = e.message ?: "Failed to update task"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(token: String, id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.deleteTask(token, id)
                _taskDeleted.value = success
                _error.value = if (!success) "Failed to delete task" else null
                // Refresh the task list after deletion
                if (_tasksByCategory.value.isNotEmpty()) {
                    getTasksByCategory(token, _tasksByCategory.value.first().category)
                } else {
                    getAllTasks(token)
                }
            } catch (e: Exception) {
                _taskDeleted.value = false
                _error.value = e.message ?: "Failed to delete task"
            } finally {
                _isLoading.value = false
            }
        }
    }
}