package id.co.brainy.ui.screen.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.brainy.data.repository.TaskRepository
import id.co.brainy.ui.components.CardMyTask
import id.co.brainy.ui.components.HomeTabs
import id.co.brainy.ui.components.headerTask
import id.co.brainy.ui.theme.BrainyTheme

@Composable
fun MyTaskScreen(
    navController: NavController,
    userToken: String,
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(TaskRepository()))
) {
    var selectedCategory by remember { mutableStateOf("All Task") }
    val allTasks by viewModel.allTasks.collectAsState()
    val tasksByCategory by viewModel.tasksByCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Fetch tasks when screen loads or category changes
    LaunchedEffect(selectedCategory) {
        if (selectedCategory == "All Task") {
            viewModel.getAllTasks(userToken)
        } else {
            viewModel.getTasksByCategory(userToken, selectedCategory)
        }
    }

    // Handle task creation result
    LaunchedEffect(viewModel.taskCreated) {
        viewModel.taskCreated.collect { success ->
            if (success == true) {
                // Refresh tasks after creation
                if (selectedCategory == "All Task") {
                    viewModel.getAllTasks(userToken)
                } else {
                    viewModel.getTasksByCategory(userToken, selectedCategory)
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        headerTask(
            titleHeader = "My Task",
            navController = navController
        )

        Spacer(modifier = Modifier.height(31.dp))

        HomeTabs(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            val tasksToShow = if (selectedCategory == "All Task") allTasks else tasksByCategory

            LazyColumn {
                items(tasksToShow) { task ->
                    CardMyTask(
                        title = task.title,
                        category = task.category,
                        desc = task.description,
                        time = task.deadline,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("DetailTask/${task.id}")
                            }
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTaskScreenPreview() {
    BrainyTheme {
        val navController = rememberNavController()
        MyTaskScreen(
            navController = navController,
            userToken = "mock_token"
        )
    }
}