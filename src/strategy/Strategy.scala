package eu.shooktea.sudoku
package strategy

trait Strategy {
  def applyStrategy(grid: Grid): Grid
}

object Strategy {
  val strategies: Seq[Strategy] = Seq(
    NakedSingleStrategy,
  )

  def runStep(grid: Grid): Grid = strategies.foldLeft(grid)((g, strategy) => strategy.applyStrategy(g))
}
