package uvg.edu.gt.contador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uvg.edu.gt.contador.ui.theme.ContadorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterApp()
        }
    }
}

@Composable
fun CounterApp() {
    var count by remember { mutableIntStateOf(0) }
    var totalIncrements by remember { mutableIntStateOf(0) }
    var totalDecrements by remember { mutableIntStateOf(0) }
    var maxValue by remember { mutableIntStateOf(0) }
    var minValue by remember { mutableIntStateOf(0) }
    var totalChanges by remember { mutableIntStateOf(0) }
    val history = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pablo Méndez",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                count--
                totalDecrements++
                totalChanges++
                if (count < minValue) minValue = count
                history.add(count)
            }) {
                Text("-", fontSize = 32.sp)
            }
            Text(text = "$count", fontSize = 32.sp, modifier = Modifier.padding(horizontal = 32.dp))
            Button(onClick = {
                count++
                totalIncrements++
                totalChanges++
                if (count > maxValue) maxValue = count
                history.add(count)
            }) {
                Text("+", fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text("Total incrementos: $totalIncrements")
            Text("Total decrementos: $totalDecrements")
            Text("Valor máximo: $maxValue")
            Text("Valor mínimo: $minValue")
            Text("Total cambios: $totalChanges")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Historial:")
        HistoryGrid(history = history)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            count = 0
            totalIncrements = 0
            totalDecrements = 0
            maxValue = 0
            minValue = 0
            totalChanges = 0
            history.clear()
        }) {
            Text("Reiniciar")
        }
    }
}

@Composable
fun HistoryGrid(history: List<Int>) {
    val rows = history.chunked(5)
    Column {
        rows.forEach { row ->
            LazyRow {
                items(row) { value ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(40.dp)
                            .background(
                                color = if (value >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "$value", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterAppView(){
    ContadorTheme {
        CounterApp()
    }
}