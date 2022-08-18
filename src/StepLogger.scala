package eu.shooktea.sudoku

class StepLogger(logEnabled: Boolean) {

}

object StepLogger {
  def apply(logEnabled: Boolean): StepLogger = new StepLogger(logEnabled)
}