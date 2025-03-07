package com.example.login.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun SignUpScreen(navController: NavController){

    var passwordError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        AlertDialog(
            title = { Text("Success")},
            text = { Text("User saved correctly")},
            confirmButton = {
                Button(onClick = {showDialog=false}) {
                    Text("Ok")
                }
            },
            onDismissRequest = {showDialog=false}
        )
    }
    val loginViewModel:LoginViewModel = viewModel()
    val authState by loginViewModel.authState.collectAsState()

    when(val state=authState){
        is LoginViewModel.AuthState.Loading->{
            CircularProgressIndicator()
        }
        is LoginViewModel.AuthState.Success->{
            LaunchedEffect(Unit) {
                     showDialog=true
            }
        }

        is LoginViewModel.AuthState.Error->{
            Log.d("error", state.message)
        }

        else ->{}
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sign up", color = Color.White)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =Color.Gray
                ),

                navigationIcon = {
                   IconButton(onClick = {navController.popBackStack()}) {
                       Icon(Icons.Default.ArrowBack, contentDescription = "",
                           tint = Color.White
                           )
                   }
                }
            )
        }


    ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black, Color.White)

                    )
                )
        ) {

            OutlinedCard(

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Email")

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("example@gmail.com") }

                    )

                    Spacer(Modifier.height(20.dp))

                    Text("Password")
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it
                            passwordError=it.length<6
                                        },
                        label = { Text("*****") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        isError = passwordError,
                        supportingText = {
                            if(passwordError){
                                Text("Password must have at least 6 caracters")
                            }
                        }
                    )
                    Spacer(Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = {
                            loginViewModel.SignUp(email, password)

                        },
                        modifier = Modifier.fillMaxWidth(0.90f)
                    ) {
                        Text("Create")
                    }





                }


            }


        }
    }

}