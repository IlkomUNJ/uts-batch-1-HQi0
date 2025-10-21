package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf

data class Contact(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val email: String
)

class ContactRep {
    companion object {
        val contactList = mutableStateListOf<Contact>()
    }
}