
# Sudoku Solver App

## Overview

The Sudoku Solver App is a Kotlin-based Android application designed to solve Sudoku puzzles. This app allows users to input a Sudoku grid and then find the solution using a backtracking algorithm. Additionally, it can reset the grid for new puzzles and ensure the initial board is valid before attempting to solve.

## Features

- Input Sudoku puzzles through a user-friendly interface.
- Solve Sudoku puzzles using a backtracking algorithm.
- Reset the grid to start a new puzzle easily.
- Validate the initial board configuration before solving.

## Requirements

- Android Studio
- Kotlin programming language

## Getting Started

### 1. Clone the Repository

To get started with the Sudoku Solver App, clone this repository to your local machine:

```bash
git clone https://github.com/Robin-Kumar-rk/SudokuSolverApp.git
cd SudokuSolver
```

2. Open the Project
Open the project in Android Studio. Make sure you have the latest version of Android Studio and the necessary SDK components installed.

3. Run the App
Connect your Android device or start an emulator.
Build and run the project from Android Studio.
Input your Sudoku puzzle in the grid format.
Tap the "Solve" button to find the solution.
Use the "Reset" button to clear the grid and start over.

- Code Explanation
- Key Components
resetSudoku(): Resets the Sudoku grid to its initial state, allowing users to enter a new puzzle.

solveSudoku(): Initiates the solving process, checking the validity of the initial board and using a backtracking algorithm to find the solution.

solver(): The recursive function that implements the backtracking algorithm to fill the Sudoku grid.

checkValid(): Checks if a number can be placed in a specific cell without violating Sudoku rules.

isValidInitialBoard(): Validates the initial board configuration to ensure that the puzzle can potentially be solved.

- Usage
Launch the app on your Android device.
Fill in the Sudoku grid with known values (0 for empty cells).
Click the "Solve" button to find the solution.
Click the "Reset" button to clear the grid and input a new puzzle.


