package eu.shooktea.sudoku
package strategy

import scala.annotation.tailrec

class NakedTripleStrategy(stepLogger: StepLogger) {
  private def mapSection(section: Seq[Cell]): Seq[Cell] = {
    val nonSolvedCells = section filter (_.notSolved)
    if (nonSolvedCells.length <= 3) section // we need at least 4 non-solved cells in section to use naked triple strategy
    else runCombinations(nonSolvedCells.combinations(3).toList, section)
  }

  @tailrec
  private def runCombinations(
     remainingCombinations: List[Seq[Cell]],
     section: Seq[Cell],
   ): Seq[Cell] = remainingCombinations match {
    case Nil => section
    case combination :: tail if checkCombination(combination) =>
      runCombinations(tail, runCombination(combination, section))
    case _ :: tail => runCombinations(tail, section)
  }

  private def checkCombination(combination: Seq[Cell]): Boolean =
    combination.flatMap(_.possibleValues).toSet.size == 3

  private def runCombination(
    combination: Seq[Cell],
    section: Seq[Cell],
  ): Seq[Cell] = runCombinationImpl(combination, section.toList) match {
    case (result, Nil) => result
    case (result, positions) =>
      val tripleValues = combination.flatMap(_.possibleValues).toSet.mkString("-")
      val triplePositions = combination.map(_.position).mkString("-")
      val changedPositions = positions.mkString(", ")
      stepLogger(s"Naked triple $tripleValues in positions $triplePositions removed those values in $changedPositions")
      result
  }

  @tailrec
  private def runCombinationImpl(
    combination: Seq[Cell],
    remainingCells: List[Cell],
    doneCells: List[Cell] = List(),
    updatedPositions: List[String] = List()
  ): (Seq[Cell], List[String]) = remainingCells match {
    case Nil => (doneCells.reverse, updatedPositions)
    case cell :: tail if cell.isSolved => runCombinationImpl(combination, tail, cell :: doneCells, updatedPositions)
    case cell :: tail if combination map (_.index) contains cell.index => runCombinationImpl(combination, tail, cell :: doneCells, updatedPositions)
    case cell :: tail  => checkCell(combination.flatMap(_.possibleValues), cell, updatedPositions) match {
      case (newCell, newPositions) => runCombinationImpl(combination, tail, newCell :: doneCells, newPositions)
    }
  }

  private def checkCell(values: Seq[Int], cell: Cell, updatedPositions: List[String]): (Cell, List[String]) =
    if (cell containsAnyOf values)
      (cell withoutAnyOf values, cell.position :: updatedPositions)
    else (cell, updatedPositions)
}

object NakedTripleStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid =
    grid.mapSections(new NakedTripleStrategy(stepLogger).mapSection)

}
