# SudokuSolver

### Input arguments

Pass sudoku grid in format described below to run solution.

Add flags before sudoku input to change behaviour:
- add `--step` to show solution step by step
- add `--grid` to display grid

Run application with flag `--test` without sudoku input to run test puzzles.

### Input format

Base format contains 81 characters from 1 to 9 or `x` for empty. They describe given numbers in sudoku grid, read
row by row from left to right.