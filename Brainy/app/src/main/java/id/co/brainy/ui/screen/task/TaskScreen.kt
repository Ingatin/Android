package id.co.brainy.ui.screen.task

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.brainy.data.repository.TaskRepository
import id.co.brainy.ui.components.ButtonCategory
import id.co.brainy.ui.components.DateTime
import id.co.brainy.ui.components.convertMillisToDate
import id.co.brainy.ui.components.headerTask
import id.co.brainy.ui.theme.BrainyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navController: NavController,
    userToken: String,
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(TaskRepository()))
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Work", "Academy")
    var selectedDate by remember { mutableStateOf("") }
    val timePickerState = rememberTimePickerState(is24Hour = true)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Observe task creation state
    val taskCreated by viewModel.taskCreated.collectAsState()

    LaunchedEffect(taskCreated) {
        if (taskCreated==true) {
            navController.popBackStack() // Go back after successful creation
        } else if (taskCreated == false) {
            scope.launch {
                snackbarHostState.showSnackbar("Failed to create task")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            headerTask(
                titleHeader = "Create Task",
                navController = navController
            )
            Spacer(modifier = Modifier.height(31.dp))
            TitleTextField("Title")
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text(text = "Title", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp, top = 4.dp)
                    .border(
                        width = 2.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(14.dp)
                    ),
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            TitleTextField("Deadline")
            DateTime(
                selectedDate = selectedDate,
                onDateSelected = { millis ->
                    selectedDate = convertMillisToDate(millis)
                },
                timePickerState = timePickerState
            )
            TitleTextField("Category")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categories.forEach { category ->
                    ButtonCategory(
                        btnTitle = category,
                        onCategoryClick = { selected ->
                            selectCategory = selected
                        },
                        isSelected = selectCategory == category
                    )
                }
            }
            TitleTextField("Description")
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text(text = "Enter description", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 4.dp, bottom = 32.dp)
                    .border(
                        width = 2.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(14.dp)
                    ),
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = false,
            )
            Button(
                onClick = {
                    if (title.isNotEmpty() && selectCategory != null && selectedDate.isNotEmpty()) {
                        viewModel.createTask(
                            token = userToken,
                            title = title,
                            description = description,
                            category = selectCategory!!,
                            deadline = selectedDate
                        )
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = title.isNotEmpty() && selectCategory != null && selectedDate.isNotEmpty()
            ) {
                Text(
                    text = "SAVE",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(6.dp),
                )
            }
        }
    }
}

@Composable
fun TitleTextField(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    BrainyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            TaskScreen(
                navController = navController,
                userToken = "mock_token"
            )
        }
    }
}