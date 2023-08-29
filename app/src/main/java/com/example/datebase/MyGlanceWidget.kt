package com.example.datebase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import com.google.firebase.firestore.ktx.toObject

class MyGlanceWidget : GlanceAppWidget(){
    @Composable
    override fun Content() {
        var memoList by remember { mutableStateOf(listOf<Memos>()) }

        LaunchedEffect(Unit) {
            val docRef = db.collection("memos")
            docRef.get()
                .addOnSuccessListener {documents ->
                    val fetchedMemoList = mutableListOf<Memos>()
                    for (document in documents) {
                        fetchedMemoList.add(document.toObject<Memos>())
                    }
                    memoList = fetchedMemoList
                }
                .addOnFailureListener { e -> "error"}
        }

        Box (
            modifier = GlanceModifier
                .clickable(actionStartActivity<MainActivity>())
                .fillMaxSize()
                .background(Color(0, 0, 100)),
            contentAlignment = androidx.glance.layout.Alignment.Center
        ){
            Text(text = "なんでやねん")
            LazyColumn(
                horizontalAlignment = androidx.glance.layout.Alignment.CenterHorizontally,
                modifier = GlanceModifier.fillMaxWidth()
            ){
                items(memoList) {memos ->
                    Column {
                        Text(
                            text = memos.date,
                            color = Color(255, 255, 255)
                        )
                        Text(
                            text = memos.title,
                            color = Color(255, 255, 255)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}