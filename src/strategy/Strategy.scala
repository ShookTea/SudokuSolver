package eu.shooktea.sudoku
package strategy

trait Strategy {
  def applyStrategy(grid: Grid, stepLogger: StepLogger): Grid
}

object Strategy {
  val strategies: Seq[Strategy] = Seq(
    NakedSingleStrategy,
    HiddenSingleStrategy,
    NakedPairStrategy,
  )

  def runStep(grid: Grid, stepLogger: StepLogger): Grid = {
    if (stepLogger.logEnabled)
      runStepByStep(grid, stepLogger)
    else
      strategies.foldLeft(grid)((g, strategy) => strategy.applyStrategy(g, stepLogger))
  }

  private def runStepByStep(grid: Grid, stepLogger: StepLogger): Grid = {
    for (strategy <- strategies) {
      val applied = strategy.applyStrategy(grid, stepLogger)
      if (applied != grid) return applied
    }
    grid
  }
}
