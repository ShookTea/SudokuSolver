package eu.shooktea.sudoku
package strategy

import scala.annotation.tailrec

object NakedPairStrategy extends Strategy {
  type CellPair = (Cell, Cell)
  type ValuePair = (Int, Int)
  type NakedPair = (CellPair, ValuePair)

  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid =
    grid.mapSections(mapGroup(stepLogger))

  private def mapGroup(stepLogger: StepLogger)(group: Seq[Cell]): Seq[Cell] = {
    val pairs: List[NakedPair] = group
      .filter(_.possibleValues.length == 2)
      .map(cell => (cell, cell.possibleValues))
      .groupBy(_._2)
      .view
      .mapValues(_.map(_._1))
      .filter(_._2.length == 2)
      .toList
      .map{ case (values, cells) => ((cells.head, cells.tail.head), (values.head, values.tail.head))}

    clearNakedPairs(pairs, group, stepLogger)
  }

  @tailrec
  private def clearNakedPairs(
    remainingPairs: List[NakedPair],
    group: Seq[Cell],
    stepLogger: StepLogger,
  ): Seq[Cell] = remainingPairs match {
    case Nil => group
    case (cellPair, valuePair) :: tail => clearNakedPairs(tail, clearNakedPair(valuePair, cellPair, group, stepLogger), stepLogger)
  }

  private def clearNakedPair(valuePair: ValuePair, cellPair: CellPair, group: Seq[Cell], stepLogger: StepLogger): Seq[Cell] =
    clearNakedPairImpl(valuePair, cellPair, group.toList) match {
      case (cells, Nil) => cells
      case (cells, positions) =>
        val updatedPositionsString = positions.mkString(", ")
        stepLogger(s"Naked pair ${valuePair._1}-${valuePair._2} in positions ${cellPair._1.position}-${cellPair._2.position} removes those values in $updatedPositionsString")
        cells
    }


  @tailrec
  private def clearNakedPairImpl(
    valuePair: ValuePair,
    cellPair: CellPair,
    remainingCells: List[Cell],
    updatedPositions: List[String] = List(),
    doneCells: List[Cell] = List()
  ): (Seq[Cell], List[String]) = remainingCells match {
    case Nil => (doneCells.reverse, updatedPositions)
    case cell :: tail if cell.isSolved || !cell.containsAnyOf(valuePair) || cell.index == cellPair._1.index || cell.index == cellPair._2.index =>
      clearNakedPairImpl(valuePair, cellPair, tail, updatedPositions, cell :: doneCells)
    case cell :: tail => clearNakedPairImpl(valuePair, cellPair, tail, cell.position :: updatedPositions, cell.withoutAnyOf(valuePair) :: doneCells)
  }
}
