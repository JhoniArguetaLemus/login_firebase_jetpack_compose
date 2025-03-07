package com.example.login.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(): ViewModel() {

    private val _authState= MutableStateFlow<AuthState>(AuthState.Idle)

    var authState:MutableStateFlow<AuthState> =_authState

    fun Login(email:String, password:String){
         val db=FirebaseAuth.getInstance()
        _authState.value=AuthState.Loading
        db.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                _authState.value=if(task.isSuccessful){
                    AuthState.Success
                }else{
                   AuthState.Error(task.exception?.message ?:"Error")
                }


            }



    }


    @SuppressLint("SuspiciousIndentation")
    fun SignUp(email: String, password: String){
        _authState.value=AuthState.Loading
        val db=FirebaseAuth.getInstance()
             db.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener{task->
                     _authState.value=if(task.isSuccessful){
                         AuthState.Success
                     }else{
                         AuthState.Error(task.exception?.message ?:"Error")
                     }

                 }

    }



    sealed class  AuthState{
        object  Idle:AuthState()
        object Success:AuthState()
        object Loading:AuthState()
        data class  Error(val message:String):AuthState()
    }



}