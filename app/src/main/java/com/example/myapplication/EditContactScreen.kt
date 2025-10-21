package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(navController: NavController, contactId: Int?) {
    val isEditing = contactId != null
    val existingContact = if (isEditing) {
        ContactRep.contactList.find { it.id == contactId!! }
    } else {
        null
    }

    var name by remember { mutableStateOf(existingContact?.name ?: "") }
    var address by remember { mutableStateOf(existingContact?.address ?: "") }
    var phone by remember { mutableStateOf(existingContact?.phone ?: "") }
    var email by remember { mutableStateOf(existingContact?.email ?: "") }

    val title = if (isEditing) "Edit Contact" else "Add a New Contact"
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val addressWords = address.split(" ").filter { it.isNotBlank() }.size
                        if (name.isBlank() || address.isBlank() || phone.isBlank() || email.isBlank()) {
                            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                        } else if (addressWords < 5) {
                            Toast.makeText(context, "Address at least 5 words", Toast.LENGTH_SHORT).show()
                        } else {
                            if (isEditing) {
                                val index = ContactRep.contactList.indexOf(existingContact)
                                if (index != -1) {
                                    ContactRep.contactList[index] = existingContact!!.copy(
                                        name = name,
                                        address = address,
                                        phone = phone,
                                        email = email
                                    )
                                }
                            } else {
                                val newContact = Contact(
                                    id = (ContactRep.contactList.maxOfOrNull { it.id } ?: 0) + 1,
                                    name = name,
                                    address = address,
                                    phone = phone,
                                    email = email
                                )
                                ContactRep.contactList.add(newContact)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save Contact",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF212121),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF212121)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ContactTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactTextField(
                value = address,
                onValueChange = { address = it },
                label = "Address",
                singleLine = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone"
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email"
            )
        }
    }
}

@Composable
fun ContactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF333333),
            unfocusedContainerColor = Color(0xFF333333),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray
        )
    )
}