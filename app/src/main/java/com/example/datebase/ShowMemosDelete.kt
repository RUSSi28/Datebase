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
fun ShowMemosDelete(onClickButton: ()-> Unit = {}){
    var memoListDelete by remember{ mutableStateOf(listOf<Memos>()) }
    val showDialog = remember{ mutableStateOf(false) }
    //val checkedState = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val docRefDelete = db.collection("memos")
        docRefDelete.get()
            .addOnSuccessListener { documents ->
                val fetchedMemoListDelete = mutableListOf<Memos>()
                for (document in documents){
                    fetchedMemoListDelete.add(document.toObject<Memos>())
                }
                memoListDelete = fetchedMemoListDelete
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
        for (memoDate in memoListDelete){
            Column(
                modifier = Modifier.clickable {

                }
            ) {
//                if (showDialog.value == true){
//                    //SimpleAlertDialog(memoDate, showDialog.value)
//                    AlertDialog(
//                        onDismissRequest = { /*TODO*/ },
//                        confirmButton = {
//                            TextButton(
//                                onClick = {
//                                    db.collection("memos").document("${memoDate.documentId}").delete()
//                                    showDialog.value = false
//                                }
//                            ){
//                                Text(text = "削除する")
//                            }
//                        },
//                        dismissButton = {
//                            TextButton(
//                                onClick = { showDialog.value = false }
//                            ) {
//                                Text(text = "キャンセル")
//                            }
//                        },
//                        title = {
//                            Text(text = "メモを削除")
//                        },
//                        text = {
//                            Text(text = "$memoDate")
//                        }
//                    )
//                }
                TextButton(
                    onClick = {
                        db.collection("memos").document("${memoDate.documentId}").delete()
                    }
                ){
                    Text(text = "削除する")
                }

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

@Composable
fun SimpleAlertDialog(datas: Memos, bool: Boolean){

    fun valueChange(bool: Boolean){
        bool != bool
    }

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = { db.collection("memos").document("$datas").delete()}
            ){
                Text(text = "削除")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { valueChange(bool) }
            ) {
                Text(text = "キャンセル")
            }
        },
        title = {
            Text(text = "メモを削除")
        },
        text = {
            Text(text = "$bool")
        }
    )
}