package com.example.login.navmanager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.view.LoginScreen
import com.example.login.view.SignUpScreen
import com.example.login.view.WelcomeView


@Composable
fun NavManager(){

    val navContoller= rememberNavController()

    NavHost(navContoller, startDestination = "Login"){

        composable("Login") {
            LoginScreen(navContoller)
        }

        composable("signup") {
            SignUpScreen(navContoller)
        }

        composable("HomeScreen") {
            WelcomeView()
        }
    }


}