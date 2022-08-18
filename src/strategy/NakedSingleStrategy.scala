package eu.shooktea.sudoku
package strategy

object NakedSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid = {
    grid.mapRows(mapGroup(stepLogger))
  }

  private def mapGroup(stepLogger: StepLogger)(group: Seq[Cell]): Seq[Cell] = {
    val nakedSingleCells = group.filter(_.isSolved)
    nakedSingleCells.foldLeft(group)((group, nakedSingle) => clearNakedSingleFromCells(nakedSingle, group, stepLogger))
  }

  private def clearNakedSingleFromCells(nakedSingle: Cell, cells: Seq[Cell], stepLogger: StepLogger): Seq[Cell] = {
    val nakedSingleValue = nakedSingle.possibleValues.head
    cells.map {
      case c if !c.isSolved && c.possibleValues.contains(nakedSingleValue) =>
        stepLogger(s"Naked single $nakedSingleValue in ${nakedSingle.position} removes $nakedSingleValue in ${c.position}")
        Cell(c.possibleValues.filterNot(_ == nakedSingleValue), c.index)
      case c => c
    }
  }
}
