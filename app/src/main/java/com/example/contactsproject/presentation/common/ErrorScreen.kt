package com.example.contactsproject.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.contactsproject.R

@Composable
fun ErrorScreen(
    message: String? = null,
    error: Throwable? = null,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 64.dp,
    textSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    buttonSize: Dp = 48.dp
) {
    val displayMessage = message ?: parseErrorMessage(error)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sad_face),
            contentDescription = "Error Icon",
            tint = Color.Gray,
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = displayMessage,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = textSize),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.height(buttonSize)
        ) {
            Text(text = "Retry", color = Color.White)
        }
    }
}
fun parseErrorMessage(error: Throwable?): String {
    return when (error) {
        is java.net.UnknownHostException -> "No internet connection. Please check your network."
        is java.net.SocketTimeoutException -> "Request timed out. Please try again later."
        is IllegalArgumentException -> "Invalid input. Please check your data."
        else -> error?.localizedMessage ?: "An unexpected error occurred."
    }
}