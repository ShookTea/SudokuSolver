package eu.shooktea.sudoku
package strategy

object NakedSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid): Grid = {
    grid.mapRows(mapGroup)
  }

  private def mapGroup(group: Seq[Cell]): Seq[Cell] = {
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
