package com.example.sudokusolver


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.setValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudokusolver.ui.theme.SudokuSolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuSolverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SudokuSolverApp(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize())
                }
            }
        }
    }
}



@Composable
fun SudokuSolverApp(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var choice by remember { mutableStateOf(1) }
        val choiceRow = arrayOf(1,2,3,4,5,6,7,8,9)
        val gridState = remember { Array(9) { Array(9) { mutableStateOf(0) } } }
        var message = remember { mutableStateOf("") }

        SudokuTitle()
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 0..8) {
                val cellColor = if (choiceRow[i] != choice) Color(0xFFEEB787) else Color(0xFFB90622)
                SudokuCell(
                    choiceRow[i],
                    { choice = choiceRow[i] },
                    cellColor,
                    Modifier
                        .weight(1f)
                        .aspectRatio(1f).border(
                            1.dp,
                            if (isSystemInDarkTheme()) Color.White else Color.Black,
                            RoundedCornerShape(16.dp)
                        ).clip(RoundedCornerShape(16.dp))
                )  // Adjusted weight to 1f
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        SudokuCell(
            number = -1,
            onCellClick = { choice = 0 },
            cellColor = if (choice == 0) Color(0xFFB90622) else Color(0xFFEEB787),
            modifier1 = Modifier.height(40.dp).border(
                1.dp,
                if (isSystemInDarkTheme()) Color.White else Color.Black,
                RoundedCornerShape(16.dp)
            ).clip(RoundedCornerShape(16.dp)),
            modifier2 = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Rows for Sudoku grid (9x9)
        for (i in 0..8) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in 0..8) {

                    SudokuCell(
                        gridState[i][j].value,
                        {
                            gridState[i][j].value = if (gridState[i][j].value == choice) 0 else choice
                            message.value = ""
                        },
                        getCellColor(i, j),
                        Modifier
                            .weight(1f)
                            .aspectRatio(1f).border(
                                1.dp,
                                if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                    )  // Adjusted weight to 1f
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Optional padding around the row
            horizontalArrangement = Arrangement.SpaceEvenly // Space buttons evenly
        ) {
            Button(
                onClick = {
                    if (message.value != "Solved") {
                        message.value = solveSudoku(gridState)
                    } else {
                        back(gridState)
                        message.value = ""
                    }
                },
                modifier = Modifier.weight(1f) // Equal weight for both buttons
            ) {
                Text(text = if (message.value == "Solved") "Back" else "Solve")
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            Button(
                onClick = { resetSudoku(gridState, message) },
                modifier = Modifier.weight(1f) // Equal weight for both buttons
            ) {
                Text("Reset")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        MessageDialog(message.value)
    }

}




@Composable
fun SudokuCell(
    number: Int,
    onCellClick: () -> Unit,
    cellColor: Color ,
    modifier1: Modifier = Modifier,
    modifier2: Modifier = Modifier.padding(2.dp)
) {
    Box(
        modifier = modifier1
            // Set a fixed size or use dynamic size logic
            .background(cellColor,)
             // Background color
            .clickable(onClick = onCellClick), // Make it clickable
        contentAlignment = Alignment.Center // Center content inside the Box
    ) {
        Text(
            text = if (number == 0) "" else if (number == -1) "Make a cell empty" else number.toString(),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,// Adjust font size as needed
            modifier = modifier2
        )
    }
}
fun getCellColor(row: Int, col: Int): Color {
    return when {
        (row / 3 + col / 3) % 2 == 0 -> Color(0xFFAEF397) // Light green for one set of 3x3 boxes
        else -> Color(0xFFBF9AEA) // Amber for the other set
    }
}

@Composable
fun SudokuTitle() {
    Text(
        text = "Sudoku Solver",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4CAF50), // Change to a color of your choice
        textAlign = TextAlign.Center,
        modifier = Modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)) // Add shadow for depth
            .background(
                Color.White,
                shape = RoundedCornerShape(8.dp)
            ) // Background color for contrast
            .padding(16.dp) // Padding inside the text box
    )
    // Space below the title
}
@Composable
fun MessageDialog(message: String) {
    // Determine the background color based on the message content
    val backgroundColor = when {
        message.contains("Solved", ignoreCase = true) -> Color(0xFFB2FF59) // Light green for success
        message.contains("Invalid", ignoreCase = true) -> Color(0xFFFF5252) // Red for errors
        else -> if (isSystemInDarkTheme()) Color.Black else Color(0xFFEFEFEF) // Default light background color
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor // Set the background color based on the message
    ) {
        Box(
            modifier = Modifier.padding(16.dp), // Padding around the Box
            contentAlignment = Alignment.Center // Center the content inside the Box
        ) {
            Text(
                text = message,
                color = Color(0xFF2758EC),
                fontSize = 24.sp
            )
        }
    }
}


