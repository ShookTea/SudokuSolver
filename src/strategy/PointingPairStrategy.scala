package eu.shooktea.sudoku
package strategy

class PointingPairStrategy(stepLogger: StepLogger) {
  def runSection(grid: Grid, section: Seq[Cell]): Grid = grid setCells getUpdatedCellsForSection(grid, section)

  private def getUpdatedCellsForSection(grid: Grid, section: Seq[Cell]): List[Cell] =
    getPointingPairCandidates(section).flatMap(pair => clearGridForPointingPairCandidate(grid, pair))

  private def getPointingPairCandidates(section: Seq[Cell]): List[(Seq[Cell], Int)] =
    (for (i <- 1 to 9) yield {
      val cells = section.filter(_.possibleValues contains i)
      if (cells.length == 2) Some((cells, i))
      else None
    }).filter(_.nonEmpty).map(_.get).toList

  private def clearGridForPointingPairCandidate(
    grid: Grid,
    pointingPairCandidate: (Seq[Cell], Int),
  ): List[Cell] = getClearedCells(grid, pointingPairCandidate) match {
    case Nil => Nil
    case cells =>
      val pointingPositions = pointingPairCandidate._1.map(_.position).mkString("-")
      val changedPositions = cells.map(_.position).mkString(", ")
      stepLogger(s"Pointing pair $pointingPositions with value ${pointingPairCandidate._2} removes that value from $changedPositions")
      cells
  }

  private def getClearedCells(grid: Grid, pointingPairCandidate: (Seq[Cell], Int)): List[Cell] = grid.cells
    .filter(_.notSolved)
    .filter(_.possibleValues contains pointingPairCandidate._2)
    .filter(_ seesAllOf pointingPairCandidate._1)
    .map(_.without(pointingPairCandidate._2))
    .toList

}

object PointingPairStrategy extends Strategy {
  override def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid =
    grid.sections.foldLeft(grid)(new PointingPairStrategy(stepLogger).runSection)
}
