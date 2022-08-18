package eu.shooktea.sudoku
package strategy

object HiddenSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid = grid
    .mapRows(mapGroup(stepLogger, c => s"row ${c.row}"))
    .mapColumns(mapGroup(stepLogger, c => s"column ${c.column}"))
    .mapBoxes(mapGroup(stepLogger, c => s"box ${c.box}"))

  private def mapGroup(stepLogger: StepLogger, labelGenerator: Cell => String)(group: Seq[Cell]): Seq[Cell] =
    for (cell <- group) yield checkCell(stepLogger, labelGenerator, cell, group)

  private def checkCell(stepLogger: StepLogger, labelGenerator: Cell => String, cell: Cell, group: Seq[Cell]): Cell =
    if (cell.isSolved) cell
    else {
      cell.possibleValues.find(valueNotPresentInOtherCells(group, cell.index)) match {
        case Some(i) =>
          stepLogger(s"Value $i on ${labelGenerator(cell)} only appears in cell ${cell.position}")
          Cell(Seq(i), cell.index)
        case None => cell
      }
    }

  private def valueNotPresentInOtherCells(group: Seq[Cell], ignoredIndex: Int)(value: Int): Boolean =
    !group.filterNot(_.index == ignoredIndex).flatMap(_.possibleValues).contains(value)
}
