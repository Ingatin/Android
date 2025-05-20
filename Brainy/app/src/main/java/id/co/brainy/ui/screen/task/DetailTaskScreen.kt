package id.co.brainy.ui.screen.task

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.brainy.data.repository.TaskRepository
import id.co.brainy.ui.components.headerTask
import id.co.brainy.ui.theme.BrainyTheme

@Composable
fun DetailTaskScreen(
    navController: NavController,
    taskId: String,
    userToken: String,
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(TaskRepository()))
) {
    // Local state for task data
    var title by remember { mutableStateOf("Tugas RPLK") }
    var description by remember { mutableStateOf("desc") }
    var deadline by remember { mutableStateOf("Date Time") }
    var category by remember { mutableStateOf("Academy") }

    // ViewModel state
    val task by viewModel.taskById.collectAsState()
    val taskUpdated by viewModel.taskUpdated.collectAsState()
    val taskDeleted by viewModel.taskDeleted.collectAsState()

    // Load task data when screen opens
    LaunchedEffect(taskId) {
        viewModel.getTaskById(userToken, taskId)
    }

    // Update local state when task data is loaded
    LaunchedEffect(task) {
        task?.let {
            title = it.title
            description = it.description
            deadline = it.deadline
            category = it.category
        }
    }

    // Handle navigation after successful operations
    LaunchedEffect(taskUpdated, taskDeleted) {
        if (taskUpdated == true || taskDeleted == true) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        headerTask(titleHeader = category, navController = navController)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.tertiary
            )
        )
        Text(
            text = deadline,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    // Update task logic
                    viewModel.updateTask(
                        token = userToken,
                        id = taskId,
                        title = title,
                        description = description,
                        category = category,
                        deadline = deadline
                    )
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
            Button(
                onClick = {
                    // Delete task logic
                    viewModel.deleteTask(userToken, taskId)
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Delete",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailTaskScreenPreview() {
    BrainyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            DetailTaskScreen(
                navController = navController,
                taskId = "1",
                userToken = "mock_token"
            )
        }
    }
}