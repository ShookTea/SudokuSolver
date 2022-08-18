package eu.shooktea.sudoku
package strategy

object NakedSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid = {
    grid.mapRows(mapGroup(stepLogger))
  }

  private def mapGroup(stepLogger: StepLogger)(group: Seq[Cell]): Seq[Cell] = {
    val nakedSingles = group.filter(_.isSolved).map(_.possibleValues.head)
    for (cell <- group) yield {
      val withoutNakedSingles = cell.possibleValues.filterNot(nakedSingles contains _)

      if (cell.isSolved)
        cell
      else
        Cell(withoutNakedSingles, cell.index)
    }
  }
}
