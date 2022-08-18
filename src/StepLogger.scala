package eu.shooktea.sudoku

class StepLogger(val logEnabled: Boolean) {
  def apply(message: String): Unit = if (logEnabled) println(message)
}

object StepLogger {
  def apply(logEnabled: Boolean): StepLogger = new StepLogger(logEnabled)
}