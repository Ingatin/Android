package id.co.brainy

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.co.brainy.ui.screen.auth.LoginScreen
import id.co.brainy.ui.screen.auth.RegisterScreen
import id.co.brainy.ui.screen.home.HomeScreen
import id.co.brainy.ui.screen.notif.NotifScreen
import id.co.brainy.ui.screen.task.DetailTaskScreen
import id.co.brainy.ui.screen.task.MyTaskScreen
import id.co.brainy.ui.screen.task.TaskScreen

@Composable
fun BrainyApp(){

    val navController = rememberNavController()

    NavHost(navController, startDestination = "login"){
        composable("login"){
            LoginScreen(navController)
        }
        composable("register"){
            RegisterScreen(navController)
        }
        composable("home"){
            HomeScreen(navController)
        }
        composable("task"){
            TaskScreen(navController)
        }
        composable("detailTask") {
            DetailTaskScreen(navController)
        }
        composable("notif") {
            NotifScreen(navController)
        }
        composable("myTask") {
            MyTaskScreen(navController)
        }

    }

}