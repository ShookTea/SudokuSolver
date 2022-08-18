package eu.shooktea.sudoku
package strategy

import scala.annotation.tailrec

object NakedSingleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid = {
    grid.mapRows(mapGroup(stepLogger)).mapColumns(mapGroup(stepLogger))
  }

  private def mapGroup(stepLogger: StepLogger)(group: Seq[Cell]): Seq[Cell] = {
    val nakedSingleCells = group.filter(_.isSolved)
    nakedSingleCells.foldLeft(group)((group, nakedSingle) => clearNakedSingleFromCells(nakedSingle, group, stepLogger))
  }

  private def clearNakedSingleFromCells(nakedSingle: Cell, cells: Seq[Cell], stepLogger: StepLogger): Seq[Cell] = {
    val nakedSingleValue = nakedSingle.possibleValues.head
    clearNakedSingleFromCellsImpl(nakedSingleValue, cells.toList, List(), List()) match {
      case (newCells, updatedPositions) if updatedPositions.isEmpty => newCells
      case (newCells, updatedPositions) =>
        val updatedPositionsString = updatedPositions.mkString(", ")
        stepLogger(s"Naked single $nakedSingleValue in ${nakedSingle.position} removes $nakedSingleValue in $updatedPositionsString")
        newCells
    }
  }

  @tailrec
  private def clearNakedSingleFromCellsImpl(
   nakedSingle: Int,
   remainingCells: List[Cell],
   doneCells: List[Cell],
   updatedPositions: List[String]
 ): (Seq[Cell], List[String]) = remainingCells match {
    case Nil => (doneCells.reverse, updatedPositions)
    case cell :: tail if !cell.isSolved && cell.possibleValues.contains(nakedSingle) =>
      clearNakedSingleFromCellsImpl(
        nakedSingle,
        tail,
        Cell(cell.possibleValues.filterNot(_ == nakedSingle), cell.index) :: doneCells,
        cell.position :: updatedPositions,
      )
    case cell :: tail => clearNakedSingleFromCellsImpl(
      nakedSingle,
      tail,
      cell :: doneCells,
      updatedPositions,
    )
  }
}
