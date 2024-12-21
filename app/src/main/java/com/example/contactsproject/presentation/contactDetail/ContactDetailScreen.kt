package com.example.contactsproject.presentation.contactDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.contactsproject.R
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.ResultResponse
import com.example.contactsproject.presentation.util.formatDate
import com.example.contactsproject.presentation.util.getAvatarColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ContactDetailsScreen(contactId: String, viewModel: ContactDetailViewModel) {
    val contactItemState by viewModel.contactItem.observeAsState()
    val contactDetailState by viewModel.contactDetail.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(contactId) {
        viewModel.setContactID(contactId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                ContactHeader(contactItemState)
                ContactDetails(
                    contactDetailState,
                    snackbarHostState,
                    scope,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ContactHeader(contactItem: ContactListItem?) {
    if (contactItem != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(contactItem.name.getAvatarColor()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contactItem.name.firstOrNull()?.uppercaseChar()?.toString() ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = contactItem.name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ContactDetails(
    contactDetailState: ResultResponse<ContactEntity?>?,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {
    when (contactDetailState) {
        is ResultResponse.Success -> {
            val contactEntity = contactDetailState.data
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            if (contactEntity?.photoUri.isNullOrBlank()) {
                                Text(
                                    text = contactEntity?.phoneNumber?.firstOrNull()
                                        ?.uppercaseChar()
                                        ?.toString() ?: "",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White
                                )
                            } else {
                                AsyncImage(
                                    model = contactEntity?.photoUri,
                                    contentDescription = "Contact Photo",
                                    modifier = Modifier.clip(CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            contactEntity?.phoneNumber?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Last updated: ${
                                    contactEntity?.lastUpdated?.formatDate() ?: "-"
                                }",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    DetailRow(
                        label = "Contact ID",
                        value = contactEntity?.contactID
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DetailRow(
                        label = "Phone Number",
                        value = contactEntity?.phoneNumber
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionIconButton(
                            icon = Icons.Default.Call,
                            label = stringResource(R.string.call)
                        ) {}

                        ActionIconButton(
                            icon = Icons.AutoMirrored.Filled.Send,
                            label = stringResource(R.string.message)
                        ) {}
                        ActionIconButton(icon = Icons.Default.Share, label = "Share") {
                        }
                    }
                }
            }
        }

        is ResultResponse.Failure -> {
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Failed to load contact details.",
                        actionLabel = "Retry"
                    )
                }
            }
        }

        else -> {
            CircularProgressIndicator()
        }
    }
}



@Composable

fun DetailRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value ?: "not saved",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable

fun ActionIconButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
