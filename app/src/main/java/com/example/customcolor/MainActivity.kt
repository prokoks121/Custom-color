package com.example.customcolor

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customcolor.ui.theme.CustomColorTheme
import com.google.accompanist.flowlayout.FlowColumn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val list = mutableStateListOf(
            mutableStateListOf(getRandomColor()),
        )

        setContent {
            CustomColorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ActionButtons(list){
                        Log.d("Provera", list.size.toString())
                        Log.d("Provera",list[0].size.toString())
                    }
                }
            }
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val list = mutableStateListOf(
        mutableStateListOf(Color.Green,Color.Red,Color.Blue),
        mutableStateListOf(Color.Green,Color.Red,Color.Blue),
        mutableStateListOf(Color.Blue)
    )
    CustomColorTheme {
        ActionButtons(list){

        }
    }
}

@Composable
fun ActionButtons(list: SnapshotStateList<SnapshotStateList<Color>>,provera:()->Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .background(Color(0xFFB565A7), shape = RoundedCornerShape(5.dp))
                    .clickable {
                        list.add(mutableStateListOf(getRandomColor()))
                        provera()
                    }, contentAlignment = Alignment.Center) {
                    Text(fontWeight = FontWeight.Bold,
                        color = Color.White,
                        text = "Dodaj")
                }
                Box(modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .background(Color(0xFFB565A7), shape = RoundedCornerShape(5.dp))
                    .clickable {
                        if (!list.isEmpty())
                            list.removeLast() }
                    , contentAlignment = Alignment.Center) {
                    Text(fontWeight = FontWeight.Bold,
                        color = Color.White,
                        text = "Ocisti")
                }
            }
        }
  item {
      Row(modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center) {
          Box(modifier = Modifier
              .padding(10.dp)
              .width(100.dp)
              .height(50.dp)
              .background(Color(0xFFB565A7), shape = RoundedCornerShape(5.dp))
              .clickable { list.shuffle() }, contentAlignment = Alignment.Center) {
              Text(fontWeight = FontWeight.Bold,
                  color = Color.White,
                  text = "Promesaj")
          }
          Box(modifier = Modifier
              .padding(10.dp)
              .width(100.dp)
              .height(50.dp)
              .background(Color(0xFFB565A7), shape = RoundedCornerShape(5.dp))
              .clickable {
                  for (colors in list)
                      for (i in 0 until colors.size) {
                          colors[i] = getRandomColor(colors)
                      }
              }, contentAlignment = Alignment.Center) {
              Text(fontWeight = FontWeight.Bold,
                  color = Color.White,
                  text = "Izmeni boje")
          }
      }
  }
        items(list){colors->
            ColorHolder(colors)
        }
    }
}

@Composable
fun ColorHolder(colors: SnapshotStateList<Color>) {
Row() {
    ColorHOlderButtons(
        dodaj = {
        colors.add(getRandomColor(colors))
    },
    izbaciPoslednji = {
        colors.removeLast()
    },
    promesaj = {
        colors.shuffle()
    },
    izmeniBoje = {
        val size = colors.size
        colors.clear()
        for (i in 0 until size){
            colors.add(getRandomColor(colors))
        }
    },
    brojac = {
        colors.size
    })
        Colors(colors)

}
}

@Composable
fun ColorHOlderButtons(dodaj:()->Unit,
                       izbaciPoslednji:()->Unit,
                       promesaj:()->Unit,
                       izmeniBoje:()->Unit,
                        brojac:()->Int){
    Column() {
        Row() {
            Box(modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(30.dp)
                .background(color = Color(0xFF5a005a), shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable {
                    if (brojac() < 10)
                        dodaj()
                },
            contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_add_svgrepo_com),
                    contentDescription = null,
                tint = Color.White)
            }

            Box(modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(30.dp)
                .background(color = Color(0xFF5a005a), shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable {
                    if (brojac() > 0)
                        izbaciPoslednji()
                },
                contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_delete_svgrepo_com),
                    contentDescription = null,
                    tint = Color.White)
            } 
        }
        Row() {
            Box(modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(30.dp)
                .background(color = Color(0xFF5a005a), shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable { izmeniBoje() },
                contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_color_fill_svgrepo_com),
                    contentDescription = null,
                    tint = Color.White)
            }

            Box(modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(30.dp)
                .background(color = Color(0xFF5a005a), shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable { promesaj() },
                contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_shuffle_svgrepo_com),
                    contentDescription = null,
                    tint = Color.White)
            }
        }
    }
}

@Composable
fun Colors(colors: SnapshotStateList<Color>) {
Box(modifier = Modifier
    .fillMaxWidth()
    .height(69.dp)) {
    FlowColumn() {
        for (color in colors) {
            SingleColor(color)

        }
    }
}

}


@Composable
fun SingleColor(color:Color){
    Box(modifier = Modifier
        .padding(2.dp)
        .height(30.dp)
        .width(30.dp)
        .background(color = color, shape = RoundedCornerShape(5.dp))
        .padding(5.dp)
    )
}

private fun getRandomColor(list: SnapshotStateList<Color> = mutableStateListOf()):Color{
    val colors = arrayListOf<Color>(
        Color(0xFF34568B),
        Color(0xFFFF6F61),
        Color(0xFF6B5B95),
        Color(0xFF88B04B),
        Color(0xFFF7CAC9),
        Color(0xFF92A8D1),
        Color(0xFF9B2335),
        Color(0xFF55B4B0),
        Color(0xFFEFC050),
        Color(0xFFB565A7),
        Color(0xFF009B77)
        )
    var color = colors.random()
    while (true){
        if (list.contains(color)){
            colors.remove(color)
            color = colors.random()
            continue
        }else{
            break
        }
    }
    return color
}
