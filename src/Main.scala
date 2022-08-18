package eu.shooktea.sudoku

import strategy.Strategy

import scala.io.Source
import scala.util.Using

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Sudoku Solver\nCheck README.md for usage information")
    } else if (args.head == "--test") {
      println("Running tests")
      runTests()
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

  def runTests(): Unit = Using(Source.fromFile("test.txt")){
    source => source.getLines()
      .map(_.trim)
      .filterNot(_.startsWith("#"))
      .filterNot(_.isEmpty)
      .grouped(3)
      .foreach(entry => runTest(entry.head, entry(1), entry(2)))
  }

  private def runTest(name: String, input: String, expectedOutput: String): Unit = {
    print(name + "... ")
    val grid = Grid(input)
    val solved = solve(grid, byStep = false, showGrid = false).convertToString

    if (solved == expectedOutput)
      println("OK")
    else println("failed")
  }

  def solve(grid: Grid, byStep: Boolean, showGrid: Boolean): Grid = {
    val stepLogger = StepLogger(byStep)

    if (showGrid) {
      println("Input grid:")
      GridDisplay(grid)
    }
    var change: Boolean = false
    var gridStep: Grid = grid
    do {
      val stepResult = Strategy.runStep(gridStep, stepLogger)
      change = gridStep != stepResult
      gridStep = stepResult

      if (byStep && change && showGrid) {
        GridDisplay(gridStep)
      }
    } while (change)

    if (showGrid) {
      println("Output grid:")
      GridDisplay(gridStep)
    }
    gridStep
  }
}
