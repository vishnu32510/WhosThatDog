package com.vn.whosthatdog.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.vn.whosthatdog.ui.components.CustomTextField
import com.vn.whosthatdog.ui.components.HeaderText
import com.vn.whosthatdog.ui.components.LoginAndSignButton

@Composable
fun LoginScreen(onLoginClick: ()->Unit, onSignUpClick: () -> Unit) {
    val (userName, setUserName) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (checked, onChecked) = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = "Login",
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = userName,
            modifier = Modifier,
            onValueChange = setUserName,
            labelText = "User Name",
            leadingIcon = Icons.Filled.Person,
        )
        Spacer(Modifier.height(8.dp))
        CustomTextField(
            value = password,
            modifier = Modifier,
            onValueChange = setPassword,
            labelText = "Password",
            leadingIcon = Icons.Filled.Lock,
            keyBoardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = onChecked,
                )
                Text(
                    text = "Remember Me"
                )
            }
            TextButton(onClick = {}) {
                Text("Forgot password?")
            }
        }
        Spacer(Modifier.height(8.dp))
        LoginAndSignButton(text = "Log In", onClick = onLoginClick, modifier = Modifier)
        AlternativeLoginOptions(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter),
            onIconClick = {
                index ->
                when(index){
                    0 -> {
                        Toast.makeText(context, "Instagram Login Click",Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(context, "Github Login Click",Toast.LENGTH_SHORT).show()

                    }
                    2 -> {
                        Toast.makeText(context, "Google Login Click",Toast.LENGTH_SHORT).show()

                    }
                }
            },
            onSignUpClick = onSignUpClick)
    }
}



@Composable
fun AlternativeLoginOptions(
    modifier: Modifier,
    onIconClick: (index: Int) -> Unit,
    onSignUpClick: () -> Unit,
) {
    val iconList =
        listOf(Icons.Filled.MailOutline, Icons.Filled.AccountCircle, Icons.Filled.AccountBox)
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("or Sign in With")
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
        ) {
            iconList.forEachIndexed() { index, item ->
                Icon(
                    imageVector = item,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = { onIconClick(index) })
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an Account?")
            TextButton(onClick = onSignUpClick) {
                Text("Sign Up")
            }
        }

    }
}
