package com.example.datebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ShowMemos(onClickButton: ()-> Unit = {}){
    var memoList by remember{ mutableStateOf(listOf<Memos>()) }
    //val checkedState = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val docRef = db.collection("memos")
        docRef.get()
            .addOnSuccessListener { documents ->
                val fetchedMemoList = mutableListOf<Memos>()
                for (document in documents){
                    fetchedMemoList.add(document.toObject<Memos>())
                }
                //ここの代入で型が合わなかったので
                memoList = fetchedMemoList
            }
            .addOnFailureListener { e -> "error" }
    }
    Column(modifier = Modifier
        .padding(10.dp)
        .animateContentSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    )
    {
        for (memoDate in memoList){
            Column(
                //modifier = Modifier.clickable {  }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray),
                    contentAlignment = Alignment.TopStart
                )
                {
                    Text(
                        text = memoDate.date.dropLast(3),
                        modifier = Modifier.background(Color.White)
                        )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray),
                    contentAlignment = Alignment.TopStart
                )
                {
                    Text(text = "タイトル       \n" + memoDate.title + "\n")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray),
                    contentAlignment = Alignment.TopStart
                )
                {
                    Text(text = "メモ内容       \n" + memoDate.memo)
                }
                Divider()
            }
        }
    }

}