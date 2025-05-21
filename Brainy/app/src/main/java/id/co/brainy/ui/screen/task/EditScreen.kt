//package id.co.brainy.ui.screen.task
//
//import android.widget.Toast
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import id.co.brainy.R
//import id.co.brainy.ui.ViewModelFactory
//import id.co.brainy.ui.common.UiState
//import id.co.brainy.ui.components.ButtonCategory
//import id.co.brainy.ui.components.headerTask
//import id.co.brainy.ui.theme.BrainyTheme
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditTaskScreen(
//    navController: NavController,
//    taskId: String // ID task yang akan diedit
//) {
//    val context = LocalContext.current
//    val factory = remember { ViewModelFactory(context) }
//    val viewModel: TaskViewModel = viewModel(factory = factory)
//
//    val taskDetail by viewModel.taskDetail.observeAsState() // hasil getTaskById
//    val updateTaskState by viewModel.updateTask.collectAsState()
//
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var selectCategory by remember { mutableStateOf<String?>(null) }
//
//    val categories = listOf("Work", "Academy")
//
//    val date by viewModel.date.observeAsState("")
//    val time by viewModel.time.observeAsState("")
//    val dateTime by viewModel.dateTime.observeAsState("")
//
//    // Load task details saat pertama kali tampil
//    LaunchedEffect(taskId) {
//        viewModel.getTaskById(taskId)
//    }
//
//    // Update state ketika data task berhasil dimuat
//    LaunchedEffect(taskDetail) {
//        taskDetail?.let { task ->
//            title = task.title
//            description = task.description
//            selectCategory = task.category
//            viewModel.setDateTimeFromString(task.dueDate)
//        }
//    }
//
//    // Handler untuk update task
//    LaunchedEffect(updateTaskState) {
//        when (val state = updateTaskState) {
//            is UiState.Success -> {
//                Toast.makeText(context, "Task updated!", Toast.LENGTH_SHORT).show()
//                navController.popBackStack()
//            }
//
//            is UiState.Error -> {
//                Toast.makeText(context, "Failed: ${state.errorMessage}", Toast.LENGTH_SHORT).show()
//            }
//
//            else -> Unit
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(horizontal = 24.dp, vertical = 16.dp),
//    ) {
//        headerTask(
//            titleHeader = "Edit Task",
//            navController = navController
//        )
//        Spacer(modifier = Modifier.height(31.dp))
//        TitleTextField("Title")
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            placeholder = { Text("Title", color = Color.LightGray) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 22.dp, top = 4.dp)
//                .border(2.dp, Color.LightGray, RoundedCornerShape(14.dp)),
//            shape = RoundedCornerShape(14.dp),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//        )
//        TitleTextField("Deadline")
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            OutlinedTextField(
//                value = time,
//                onValueChange = {},
//                placeholder = { Text("Time") },
//                readOnly = true,
//                trailingIcon = {
//                    IconButton(onClick = {
//                        viewModel.selectTime(context)
//                    }) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_clock),
//                            contentDescription = "Select Time",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                },
//                modifier = Modifier
//                    .weight(0.4f)
//                    .border(2.dp, Color.LightGray, RoundedCornerShape(14.dp)),
//                shape = RoundedCornerShape(14.dp),
//            )
//            OutlinedTextField(
//                value = date,
//                onValueChange = {},
//                placeholder = { Text("Date") },
//                readOnly = true,
//                trailingIcon = {
//                    IconButton(onClick = {
//                        viewModel.selectDate(context)
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.DateRange,
//                            contentDescription = "Select date"
//                        )
//                    }
//                },
//                modifier = Modifier
//                    .weight(0.6f)
//                    .border(2.dp, Color.LightGray, RoundedCornerShape(14.dp)),
//                shape = RoundedCornerShape(14.dp),
//            )
//        }
//
//        TitleTextField("Category")
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            categories.forEach { category ->
//                ButtonCategory(
//                    btnTitle = category,
//                    onCategoryClick = { selected -> selectCategory = selected },
//                    isSelected = selectCategory == category
//                )
//            }
//        }
//
//        TitleTextField("Description")
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            placeholder = { Text("Enter description", color = Color.LightGray) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(bottom = 32.dp)
//                .border(2.dp, Color.LightGray, RoundedCornerShape(14.dp)),
//            shape = RoundedCornerShape(14.dp),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//            singleLine = false,
//        )
//
//        Button(
//            onClick = {
//                viewModel.updateTask(
//                    taskId = taskId,
//                    title = title,
//                    desc = description,
//                    category = selectCategory ?: "",
//                    dueDate = dateTime
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary
//            )
//        ) {
//            Text(
//                text = "UPDATE",
//                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
//                color = Color.White,
//                modifier = Modifier.padding(6.dp)
//            )
//        }
//    }
//}
