package eu.shooktea.sudoku

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Sudoku Solver\nCheck README.md for usage information")
    } else if (args.head == "--test") {
      println("Running tests")
    } else {
      val input = args.last.trim
      val byStep = args contains "--step"
      val showGrid = args contains "--grid"

      val result = solve(input, byStep, showGrid)
      val resultMessage = if (result.isSolved) "solved" else "not solved"
      println(s"Result: ($resultMessage)")
      println(result.convertToString)
    }
  }

  def solve(input: String, byStep: Boolean, showGrid: Boolean): Grid = {
    val grid = Grid(input)
    grid
  }
}
