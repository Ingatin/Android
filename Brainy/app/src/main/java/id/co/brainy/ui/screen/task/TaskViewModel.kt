package id.co.brainy.ui.screen.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.brainy.data.network.response.TasksItem
import id.co.brainy.data.repository.TaskRepository
import id.co.brainy.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _createTask = MutableStateFlow<UiState<TasksItem>>(UiState.Empty)
    val createTask: MutableStateFlow<UiState<TasksItem>> = _createTask

    private val _taskAll = MutableStateFlow<UiState<List<TasksItem>?>>(UiState.Empty)
    val taskAll: StateFlow<UiState<List<TasksItem>?>> = _taskAll

    private val _taskCategory = MutableStateFlow<UiState<List<TasksItem>?>>(UiState.Empty)
    val taskCategory: StateFlow<UiState<List<TasksItem>?>> = _taskCategory

    private val _date = MutableLiveData("")
    var date: LiveData<String> = _date

    private val _time = MutableLiveData("")
    var time: LiveData<String> = _time

    private val _dateTime = MutableLiveData("")
    var dateTime: LiveData<String> = _dateTime

    fun selectDate(context: Context) {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            context, { _, year, month, day ->
                val formatted = String.format(
                    "%02d-%02d-%d",
                    day,
                    month + 1,
                    year
                )
                _date.value = formatted
                updateDateTime()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun selectTime(context: Context) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hour, minute ->
                val formatted = String.format("%02d:%02d", hour, minute)
                _time.value = formatted
                updateDateTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

private fun updateDateTime() {
    val currentDate = _date.value ?: ""
    val currentTime = _time.value ?: ""

    if (currentDate.isNotEmpty() && currentTime.isNotEmpty()) {
        _dateTime.value = "$currentDate $currentTime"
    }
}


//    fun selectDateTime(context: Context) {
//        val currentDateTime = Calendar.getInstance()
//        val startYear = currentDateTime.get(Calendar.YEAR)
//        val startMonth = currentDateTime.get(Calendar.MONTH)
//        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
//        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
//        val startMinute = currentDateTime.get(Calendar.MINUTE)
//
//        DatePickerDialog(context, { _, year, month, day ->
//            TimePickerDialog(context, { _, hour, minute ->
//                val pickedDateTime = Calendar.getInstance()
//                pickedDateTime.set(year, month, day, hour, minute)
//
//                val formattedDateTime = String.format(
//                    "%02d-%02d-%d %02d:%02d",
//                    day,
//                    month + 1, // Month is 0-based
//                    year,
//                    hour,
//                    minute
//                )
//
//                updateDateTime(formattedDateTime)
//            }, startHour, startMinute, false).show()
//        }, startYear, startMonth, startDay).show()
//    }


fun createTask(
    category: String,
    dueDate: String,
    title: String,
    desc: String
) {
    _createTask.value = UiState.Loading
    viewModelScope.launch {
        val response = repository.createTask(category, dueDate, title, desc)
        response.onSuccess {
            _createTask.value = UiState.Success(it)
        }.onFailure {
            _createTask.value = UiState.Error(it.message ?: "Unknown Error")
        }
    }

}


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

fun getTasksByCategory(category: String) {
    viewModelScope.launch {
        _taskCategory.value = UiState.Loading
        val result = repository.getTaskByCategory(category)
        result.onSuccess {
            _taskCategory.value = UiState.Success(it ?: emptyList())
        }.onFailure {
            _taskCategory.value = UiState.Error(it.message ?: "Unknown Error")
        }
    }
}


}