package eu.shooktea.sudoku

object GridDisplay {
  private val firstLine =  "┏━━━┯━━━┯━━━┳━━━┯━━━┯━━━┳━━━┯━━━┯━━━┓"
  private val rowLine =    "┠───┼───┼───╂───┼───┼───╂───┼───┼───┨"
  private val blockLine =  "┣━━━┿━━━┿━━━╋━━━┿━━━┿━━━╋━━━┿━━━┿━━━┫"
  private val bottomLine = "┗━━━┷━━━┷━━━┻━━━┷━━━┷━━━┻━━━┷━━━┷━━━┛"

  def apply(grid: Grid): Unit = {
    println(firstLine)
    printRow(grid row 1)
    println(rowLine)
    printRow(grid row 2)
    println(rowLine)
    printRow(grid row 3)
    println(blockLine)
    printRow(grid row 4)
    println(rowLine)
    printRow(grid row 5)
    println(rowLine)
    printRow(grid row 6)
    println(blockLine)
    printRow(grid row 7)
    println(rowLine)
    printRow(grid row 8)
    println(rowLine)
    printRow(grid row 9)
    println(bottomLine)
  }

  private def printRow(row: Seq[Cell]): Unit = {
    printCandidates(row, Seq(1, 2, 3), solutionOnCenter = false)
    printCandidates(row, Seq(4, 5, 6), solutionOnCenter = true)
    printCandidates(row, Seq(7, 8, 9), solutionOnCenter = false)
  }

  private def printCandidates(row: Seq[Cell], candidates: Seq[Int], solutionOnCenter: Boolean): Unit = {
    print("┃")
    printCandidates(row(0), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(1), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(2), candidates, solutionOnCenter)
    print("┃")
    printCandidates(row(3), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(4), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(5), candidates, solutionOnCenter)
    print("┃")
    printCandidates(row(6), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(7), candidates, solutionOnCenter)
    print("│")
    printCandidates(row(8), candidates, solutionOnCenter)
    println("┃")
  }

  private def printCandidates(cell: Cell, candidates: Seq[Int], solutionOnCenter: Boolean): Unit = {
    if (cell.possibleValues.length == 1) {
      if (solutionOnCenter) print(" " + cell.possibleValues.head + " ")
      else print("   ")
    } else for (c <- candidates) {
      if (cell.possibleValues contains c) print(c)
      else print(" ")
    }
  }
}
