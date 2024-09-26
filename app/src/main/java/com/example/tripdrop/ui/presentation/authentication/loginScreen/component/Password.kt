package com.example.tripdrop.ui.presentation.authentication.loginScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Password(icon: ImageVector , plText: String , prefixText : String): String {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }


    val containerColor = MaterialTheme.colorScheme.secondary
    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
                icon, contentDescription = "icon",
                tint = Color.DarkGray)
        },
        onValueChange = { text = it },
        shape = RoundedCornerShape(10.dp) ,
        prefix = {
            Text(
                text = prefixText ,
                color = Color.Black ,
                fontSize = 14.sp
            )
        },


        placeholder = { Text(text = plText, color = Color.DarkGray,fontSize = 14.sp) },

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ) ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black ,
            unfocusedTextColor = Color.Black ,
            focusedContainerColor = Color.White ,
            unfocusedContainerColor = Color.White ,
            focusedBorderColor =Color.Black ,
            unfocusedBorderColor = Color.DarkGray ,
            cursorColor = Color.Black
        ) ,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 14.dp),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                //



            }
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() ,
        trailingIcon = {
            val visibilityIcon =
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
            Icon(
                imageVector = visibilityIcon,
                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                tint = Color(0xFFA7A7A7),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { isPasswordVisible = !isPasswordVisible }
            )
        }
    )
    return text
}
