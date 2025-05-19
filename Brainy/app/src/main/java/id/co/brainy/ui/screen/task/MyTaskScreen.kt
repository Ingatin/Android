package id.co.brainy.ui.screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.brainy.ui.components.HomeTabs
import id.co.brainy.ui.components.headerTask
import id.co.brainy.ui.theme.BrainyTheme

@Composable
fun MyTaskScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All Task") }

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

//        CardMyTask(
//           tasks = ,
//            modifier = Modifier
//                .clickable {
//                    navController.navigate("DetailTask")
//                }
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyTaskScreenPreview() {
    BrainyTheme {
        val navController = rememberNavController()
        MyTaskScreen(navController = navController)
    }
}
