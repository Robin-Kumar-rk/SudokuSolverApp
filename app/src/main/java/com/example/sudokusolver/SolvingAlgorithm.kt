package com.example.sudokusolver


import androidx.compose.runtime.MutableState

fun resetSudoku(gridState: Array<Array<MutableState<Int>>>, message: MutableState<String>) {
    for (i in 0..8) {
        for (j in 0..8) {
            gridState[i][j].value = 0
        }
    }
    message.value = ""
}

fun solveSudoku(gridState: Array<Array<MutableState<Int>>>): String {
    val temp = Array(9) { Array(9) {0} };
    for (i in 0..8) {
        for (j in 0..8) {
            temp[i][j] = gridState[i][j].value
        }
    }
    if (!isValidInitialBoard(temp)) {
        return "Invalid"
    }
    if (solver(temp, 0, 0)) {
        for (i in 0..8) {
            for (j in 0..8) {
                gridState[i][j].value = temp[i][j]
            }
        }
        return "Solved"
    } else {
        return "Solution doesn't exist"
    }
}

fun solver(grid: Array<Array<Int>>, row: Int, col: Int): Boolean {
    if (row == 9) return true
    var nextRow = row
    var nextCol = col + 1
    if (nextCol == 9) {
        nextCol = 0
        nextRow++
    }
    if (grid[row][col] == 0) {
        for (i in 1..9) {
            if (checkValid(grid, row, col, i)) {
                grid[row][col] = i
                if (solver(grid, nextRow, nextCol)) {
                    return true
                }
                grid[row][col] = 0
            }
        }
        return false
    } else {
        return solver(grid, nextRow, nextCol)
    }
}

fun checkValid(grid: Array<Array<Int>>, row: Int, col: Int, num: Int): Boolean {
    for (i in 0..8) {
        if (grid[row][i] == num) {
            return false
        }
    }
    for (i in 0..8) {
        if (grid[i][col] == num) {
            return false
        }
    }
    val startRow = row - row % 3
    val startCol = col - col % 3

    for (i in 0..2) {
        for (j in 0..2) {
            if (grid[i + startRow][j + startCol] == num) {
                return false
            }
        }
    }
    return true
}
fun isValidInitialBoard(grid: Array<Array<Int>>): Boolean {
    val copyGrid = Array(9) { grid[it].clone() }
    for (i in 0..8) {
        for (j in 0..8) {
            val num = copyGrid[i][j]
            if (num != 0) {
                copyGrid[i][j] = 0
                if (!checkValid(copyGrid, i, j, num)) {
                    return false
                }
                copyGrid[i][j] = num
            }
        }
    }
    return true
}