package id.co.brainy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.co.brainy.data.utils.UserPreferences
import id.co.brainy.data.utils.dataStore
import id.co.brainy.ui.screen.auth.LoginScreen
import id.co.brainy.ui.screen.auth.RegisterScreen
import id.co.brainy.ui.screen.home.HomeScreen
import id.co.brainy.ui.screen.notif.NotifScreen
import id.co.brainy.ui.screen.task.DetailTaskScreen
import id.co.brainy.ui.screen.task.MyTaskScreen
import id.co.brainy.ui.screen.task.TaskScreen
import kotlinx.coroutines.flow.first

@Composable
fun BrainyApp(startDestination: String) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences.getInstance(context.dataStore) }
    var userToken by remember { mutableStateOf("") }

    // Get token when app starts
    LaunchedEffect(Unit) {
        userToken = userPreferences.getToken().first() ?: ""
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth screens (unchanged)
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }

        // Main screens (unchanged)
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("notif") {
            NotifScreen(navController = navController)
        }

        // Task screens (with token parameter)
        composable("task") {
            TaskScreen(
                navController = navController,
                userToken = userToken
            )
        }
        composable("myTask") {
            MyTaskScreen(
                navController = navController,
                userToken = userToken
            )
        }
        composable("detailTask/{taskId}") { backStackEntry ->
            DetailTaskScreen(
                navController = navController,
                taskId = backStackEntry.arguments?.getString("taskId") ?: "",
                userToken = userToken
            )
        }
    }
}