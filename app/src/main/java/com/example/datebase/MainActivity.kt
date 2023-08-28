package com.example.datebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datebase.ui.theme.DatebaseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            StatusBarColor()
            Scaffold(
                topBar = { TopBar() },
                bottomBar = { MultipleItemsBottomNavigation(navController = navController)}
            )
            {paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)){
                    NavHost(navController = navController, startDestination = "0"){
                        composable("0"){ ShowMemos() }
                        composable("1"){ ShowMemosDelete() }
                        composable("2"){ TextField() }
                    }
                }
            }
        }

    }
}

val db = Firebase.firestore

@Composable
private fun TextField(onClickButton :() -> Unit = {}) {
    var title = rememberSaveable { mutableStateOf("") }
    var memo = rememberSaveable { mutableStateOf("") }//ここで状態の保持をしている。

    //メモを保存する機能
    fun adder(){
        //日付を参照？
        val cal = LocalDateTime.now()
        val sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        val result = sdf.format(cal)
        val memory = hashMapOf("title" to "${title.value}","memo" to "${memo.value}","date" to "${result.removePrefix("{")}")//なぜこれだけで消せるんだ`{
        if (memo.value != ""&&title.value != "") {
            //データ保存はsetメソッドでも行けるらしいな
            db.collection("memos").document("${result.replace("[^\\d]".toRegex(),"")}").set(memory)

            memo.value = ""
            title.value = ""
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
        ) {
        TitleField(title = title.value, onTitleChange = { title.value = it })
        TextField(memo = memo.value, onNameChange = { memo.value = it })
        Button(
            onClick = { adder() },
            colors = ButtonDefaults.buttonColors(Color(61, 164, 233, 255))
        )
        {
            //ボタンに装飾を付ける
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)

            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "登録", )
        }
    }
}


@Composable
fun TitleField(title: String, onTitleChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        //ここがテキストフィールドです。valueにはTextFieldの引数としてのnameが代入されます。
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") }
        )
    }
}


@Composable
fun TextField(memo: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        //ここがテキストフィールドです。valueにはTextFieldの引数としてのnameが代入されます。
        OutlinedTextField(
            value = memo,
            onValueChange = onNameChange,
            label = { Text("Memo") }
        )
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Memo App") },
        backgroundColor = Color(61, 164, 233, 255),

        )
}


sealed class Item(var dist: String, var icon: ImageVector) {//ベクター画像
object Home : Item("Memos", Icons.Filled.Home)//iconをimportしてベクター画像をオブジェクトに？
    //object Email : Item("Email", Icons.Filled.Email)
    object Stars : Item("Delete", Icons.Filled.Delete)
    object Lists : Item("Add", Icons.Filled.Add)
}

@Composable
fun MultipleItemsBottomNavigation(navController: NavHostController) {
    var selectedItem = remember { mutableStateOf(0) }//選ばれた画像の番号配列で？
    val items = listOf(Item.Home, Item.Stars, Item.Lists)//itemとselecteditem

    BottomAppBar(backgroundColor = Color(61, 164, 233, 255)) {
        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    modifier = Modifier.background(Color(61, 164, 233, 255)),
                    icon = { Icon(item.icon, contentDescription = item.dist) },
                    label = { Text(item.dist) },
                    alwaysShowLabel = false, // 4つ以上のItemのとき
                    selected = selectedItem.value == index,//アイコン下の文字列の表示trueのやつのみ表示？
                    onClick = {
                        selectedItem.value = index
                        navController.navigate("$index")
                    }
                )

            }
        }
    }

}

@Composable
fun StatusBarColor(){
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color(61,164,233,255))
    }
}



