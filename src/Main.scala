package eu.shooktea.sudoku

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Sudoku Solver\nCheck README.md for usage information")
    } else if (args.head == "--test") {
      println("Running tests")
    } else {
      val input = args.last
      val byStep = args contains "--step"
      val showGrid = args contains "--grid"
    }
  }
}
