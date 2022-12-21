package com.example.datebase

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Memos(
    @DocumentId val documentId: String? = null,
    @PropertyName("title") val title: String = "",
    @PropertyName("memo") val memo: String = "",
    @PropertyName("date") val date: String = ""
)

