package id.co.brainy.ui.screen.task

import android.util.Log
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.brainy.data.network.response.TasksItem
import id.co.brainy.ui.ViewModelFactory
import id.co.brainy.ui.common.UiState
import id.co.brainy.ui.components.headerTask
import id.co.brainy.ui.screen.home.HomeViewModel
import id.co.brainy.ui.theme.BrainyTheme


@Composable
fun DetailTaskScreen(
    navController: NavController,
    taskId: String
) {

    val context = LocalContext.current
    val factory = remember { ViewModelFactory(context) }
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val detailTask by viewModel.taskDetail.collectAsState()
    Log.d("DetailTaskScreen_composable", "Received taskId: $detailTask")

    LaunchedEffect(taskId) {
        Log.d("DetailTaskScreen_launScreen", "Received taskId: $taskId")
        viewModel.getDetailTask(taskId)
    }

    when (detailTask) {
        is UiState.Success -> {
            val tasks = (detailTask as UiState.Success<List<TasksItem>>).data
            val task = tasks.first()

            Log.d("DetailTaskScreen_success", "Received task: $task")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),

                ) {
                headerTask(
                    titleHeader = task.category
                    , navController = navController
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
                Text(
                    text = task.dueDate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
                )
                Text(
                    text = task.desc,
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
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                    Button(
                        onClick = {

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
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }

        }

        is UiState.Error -> {
            val errorMessage = (detailTask as UiState.Error).errorMessage
            Text(text = "Error: $errorMessage", color = Color.Red)
        }

        else -> {
            Text(text = "Tidak ada data")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailTaskScreenPreview() {
    BrainyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            DetailTaskScreen(taskId = "O6PHC9ZPBANA7R_",
                    navController = navController)
        }
    }
}