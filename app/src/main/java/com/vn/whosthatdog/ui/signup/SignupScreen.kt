package com.vn.whosthatdog.ui.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.vn.whosthatdog.ui.components.CustomTextField
import com.vn.whosthatdog.ui.components.HeaderText
import com.vn.whosthatdog.ui.components.LoginAndSignButton

@Composable
fun SignupScreen(onLoginClick: () -> Unit, onSignupClick: ()-> Unit) {
    val (firstName, setFirstName) = rememberSaveable { mutableStateOf("") }
    val (lastName, setLastName) = rememberSaveable { mutableStateOf("") }
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = rememberSaveable { mutableStateOf("") }
    val (agree, onAgreeChanged) = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = "Sign UP",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = firstName,
            modifier = Modifier,
            onValueChange = setFirstName,
            labelText = "First Name",
            leadingIcon = Icons.Filled.Person,
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            value = lastName,
            modifier = Modifier,
            onValueChange = setLastName,
            labelText = "Last Name",
            leadingIcon = Icons.Filled.Lock,
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            value = email,
            modifier = Modifier,
            onValueChange = setEmail,
            labelText = "Email",
            leadingIcon = Icons.Filled.Lock,
            keyBoardType = KeyboardType.Email
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            value = password,
            modifier = Modifier,
            onValueChange = setPassword,
            labelText = "Password",
            leadingIcon = Icons.Filled.Lock,
            keyBoardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            value = confirmPassword,
            modifier = Modifier,
            onValueChange = setConfirmPassword,
            labelText = "Confirm Password",
            leadingIcon = Icons.Filled.Lock,
            keyBoardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            val privacyText = "Privacy"
            val policyText = "Policy"
            val annotatedString = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                    append("I Agree with")
                }
                append(" ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                    pushStringAnnotation(tag = privacyText, privacyText)
                    append(privacyText)
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                    append(" And ")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                    pushStringAnnotation(tag = policyText, policyText)
                    append(policyText)
                }

            }
            Checkbox(agree, onAgreeChanged)
            ClickableText(annotatedString) {offset ->
                annotatedString.getStringAnnotations(offset,offset).forEach{
                    when(it.tag){
                        privacyText -> {
                            Toast.makeText(context, privacyText, Toast.LENGTH_SHORT).show()
                        }
                        policyText -> {
                            Toast.makeText(context, policyText, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        LoginAndSignButton(text = "Sign Up", onClick = onSignupClick, modifier = Modifier)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have and Account?")
            TextButton(onClick = onLoginClick) {
                Text("Log In")
            }
        }
    }
}