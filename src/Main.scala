package eu.shooktea.sudoku

import strategy.Strategy

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Sudoku Solver\nCheck README.md for usage information")
    } else if (args.head == "--test") {
      println("Running tests")
    } else {
      val input = Grid(args.last.trim)
      val byStep = args contains "--step"
      val showGrid = args contains "--grid"

      val result = solve(input, byStep, showGrid)
      val resultMessage = if (result.isSolved) "solved" else "not solved"
      println(s"Result: ($resultMessage)")
      println(result.convertToString)
    }
  }

  def solve(grid: Grid, byStep: Boolean, showGrid: Boolean): Grid = {
    if (showGrid && byStep) {
      println("Input grid:")
      GridDisplay(grid)
    }
    var message: Option[String] = None
    var gridStep: Grid = grid
    do {
      val stepResult = Strategy runStep gridStep
      message = stepResult._2
      gridStep = stepResult._1

      if (byStep && message.nonEmpty) {
        message foreach println
        if (showGrid) GridDisplay(gridStep)
      }
    } while (message.nonEmpty)

    grid
  }
}
