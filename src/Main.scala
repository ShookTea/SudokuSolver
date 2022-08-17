package eu.shooktea.sudoku

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println(
        """Pass sudoku grid in format described in README.md to run solving.
          |You can use flag --step before sudoku grid to display solution step by step with descriptions.
          |
          |You can use flag --test without passing sudoku grid to run all tests.
          |""".stripMargin)
    } else if (args.head == "--test") {
      println("Running tests")
    } else if (args.head == "--step" && args.length >= 2) {
      println(s"Running for input ${args(1)} with steps")
    } else {
      println(s"Running for input ${args.head}")
    }
  }
}
