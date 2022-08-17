package eu.shooktea.sudoku
package strategy

trait Strategy {
  def applyStrategy(grid: Grid): (Grid,Option[String])
}

object Strategy {
  val strategies: Seq[Strategy] = Seq()

  def runStep(grid: Grid): (Grid,Option[String]) = {
    for (s <- strategies) {
      val strategyResult = s applyStrategy grid
      if (strategyResult._2.nonEmpty) {
        return strategyResult
      }
    }
    (grid, None)
  }
}
