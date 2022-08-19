package eu.shooktea.sudoku
package strategy

class NakedSingleStrategy(stepLogger: StepLogger) {
  def mapGrid(grid: Grid): Grid = grid.cells
    .filter(_.isSolved)
    .foldLeft(grid)(clearNakedSingle)

  def clearNakedSingle(grid: Grid, nakedSingle: Cell): Grid = {
    val nakedValue = nakedSingle.possibleValues.head

    val fixedCells = getFixedCellsForNakedSingle(grid, nakedSingle, nakedValue)
    if (fixedCells.isEmpty) grid
    else {
      val updatedPositions = fixedCells.map(_.position).mkString(", ")
      stepLogger(s"Naked single $nakedValue in position ${nakedSingle.position} removes $nakedValue in $updatedPositions")
      grid setCells fixedCells
    }
  }

  def getFixedCellsForNakedSingle(grid: Grid, nakedSingle: Cell, nakedValue: Int): Seq[Cell] = grid.cells
    .filter(_.notSolved)
    .filter(_ sees nakedSingle)
    .filter(_.possibleValues contains nakedValue)
    .map(_.withoutAnyOf(Seq(nakedValue)))
}

object NakedSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid =
    new NakedSingleStrategy(stepLogger).mapGrid(grid)
}
